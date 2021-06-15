# 安装

## 环境要求

+ CPU核数 >= 2
+ 内存 >= 2G
+ linux内核版本 >= 4
  + `uname -r`查看
  + 后面可以进行升级

## 环境准备

+ 安装依赖

  ```shell
  $ yum install -y conntrack ntpdate ntp ipvsadm ipset jq iptables curl sysstat libseccomp wget vim net-tools git iproute lrzsz bash-completion tree bridge-utils unzip bind-utils gcc
  ```

+ 关闭防火墙

  ```shell
  $ systemctl stop firewalld && systemctl disable firewalld
  ```

+ 安装`iptables`并置空规则，用于后续使用`iptables`实现`k8s`得负载均衡

  ```shell
  $ yum -y install iptables-services && systemctl start iptables && systemctl enable iptables && iptables -F && service iptables saveyum -y install iptables-services && systemctl start iptables && systemctl enable iptables && iptables -F && service iptables save
  ```

+ 关闭swap分区（虚拟内存）

  ```shell
  $ swapoff -a && sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab
  ```

+ 关闭`selinux`

  ```shell
  $ setenforce 0 && sed -i 's/^SELINUX=.*/SELINUX=disabled/' /etc/selinux/config
  ```

+ 升级linux内核

  ```shell
  $ rpm -Uvh https://www.elrepo.org/elrepo-release-7.el7.elrepo.noarch.rpm
  $ yum --enablerepo=elrepo-kernel install -y kernel-lt
  # 查看可选内核
  $ awk -F\' '$1=="menuentry " {print $2}' /etc/grub2.cfg
  # 选择1个内核作为默认启动内核（从0开始）
  $ grub2-set-default 0
  # 重启生效
  $ reboot
  # 查看内核版本
  $ uname -r
  ```

+ 配置时间

  + 设置时区

    ```shell
    $ timedatectl set-timezone Asia/Shanghai
    ```

  + 安装`ntpd`自动同步时间，utc时间写入RTC时钟

    ```shell
    $ ntpdate cn.pool.ntp.org
    $ timedatectl set-local-rtc
    ```

  + 重启服务器，使依赖时间得服务应用新的时间

+ 根据`k8s`需要，配置内核参数

  ```shell
  # 创建配置文件
  $ cat > /etc/sysctl.d/kubernetes.conf <<EOF
  net.bridge.bridge-nf-call-iptables=1
  net.bridge.bridge-nf-call-ip6tables=1
  net.ipv4.ip_forward=1
  net.ipv4.tcp_tw_recycle=0
  vm.swappiness=0
  vm.overcommit_memory=1
  vm.panic_on_oom=0
  fs.inotify.max_user_instances=8192
  fs.inotify.max_user_watches=1048576
  fs.file-max=52706963
  fs.nr_open=52706963
  net.ipv6.conf.all.disable_ipv6=1
  net.netfilter.nf_conntrack_max=2310720
  EOF
  # 立即生效
  $ sysctl -p /etc/sysctl.d/kubernetes.conf
  ```

+ 配置日志管理服务`journal`，用于管理`k8s`日志

  ```shell
  # 日志保存目录
  $ mkdir /var/log/journal
  # 配置文件存放目录
  $ mkdir /etc/systemd/journald.conf.d
  # 创建配置文件
  $ cat > /etc/systemd/journald.conf.d/99-prophet.conf <<EOF
  [Journal]
  Storage=persistent
  Compress=yes
  SyncIntervalSec=5m
  RateLimitInterval=30s
  RateLimitBurst=1000
  SystemMaxUse=10G
  SystemMaxFileSize=200M
  MaxRetentionSec=2week
  ForwardToSyslog=no
  EOF
  # 重启journald 加载配置
  $ systemctl restart systemd-journald
  ```

+ 为`kube-proxy`开启`ipvs`做准备

  > `kube-proxy`用于为`k8s`中得`service`构建路由规则；可以基于`iptables`或`ipvs`实现，`ipvs`比`iptables`能支持更多得路由规则

  ```shell
  # 向内核安装桥接模式防火墙模块`br_netfilter`
  $ modprobe br_netfilter
  # 向内核安装 ipvs 相关模块得脚本
  $ cat > /etc/sysconfig/modules/ipvs.modules <<EOF
  #!/bin/bash
  modprobe -- ip_vs
  modprobe -- ip_vs_rr
  modprobe -- ip_vs_wrr
  modprobe -- ip_vs_sh
  modprobe -- nf_conntrack_ipv4
  EOF
  # 修改权限并执行脚本，并查看这些文件是否被引导
  $ chmod 755 /etc/sysconfig/modules/ipvs.modules && bash /etc/sysconfig/modules/ipvs.modules && lsmod | grep -e ip_vs -e nf_conntrack_ipv4
  ```

## 配置docker

+ Cgroup Driver

  + Docker 在默认情况下使用的 Cgroup Driver 为 `cgroupfs`，可以通过`docker info`命令查看

  + 而 Kubernetes 其实推荐使用 `systemd` 来代替 `cgroupfs`，`Cgroup Driver`不一致会导致安装`k8s`时报错

    ```
    failed to create kubelet: misconfiguration: kubelet cgroup driver: "cgroupfs" is different from docker cgroup driver: "systemd"
    ```

  + Docker修改`Cgroup Driver`为`systemd`

    + 编辑 `/etc/docker/daemon.json` ，添加如下启动项参数

      ```json
      {
        "exec-opts": ["native.cgroupdriver=systemd"]
      }
      ```

    + 重启docker

## 安装K8S

> + k8s得3种安装方式：
>   + 二进制：负责
>   + kubeadm安装：简单，官方推荐
>   + 可视化界面（如rancher）
> + 我们这里使用kubeadm安装

```shell
# 创建`repo`文件，从阿里云安装kubeadm
$ cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=http://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=http://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg
		http://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
# 安装kubeadm、kubelet、kubectl
$ yum install -y kubeadm kubelet kubectl
# 启动 kubelet
$ systemctl enable kubelet && systemctl start kubelet
```

## 集群安装

+ k8s需要得docker镜像下载

  + 查看需要哪些镜像

    ```shell
    $ kubeadm config images list
    ```

  + 从国内镜像仓库下载，并重命名

    ```
    docker pull registry.aliyuncs.com/google_containers/kube-apiserver:v1.21.1
    docker tag registry.aliyuncs.com/google_containers/kube-apiserver:v1.21.1 k8s.gcr.io/kube-apiserver:v1.21.1
    docker rmi registry.aliyuncs.com/google_containers/kube-apiserver:v1.21.1
    
    docker pull registry.aliyuncs.com/google_containers/kube-controller-manager:v1.21.1
    docker tag registry.aliyuncs.com/google_containers/kube-controller-manager:v1.21.1 k8s.gcr.io/kube-controller-manager:v1.21.1
    docker rmi registry.aliyuncs.com/google_containers/kube-controller-manager:v1.21.1
    
    docker pull registry.aliyuncs.com/google_containers/kube-scheduler:v1.21.1
    docker tag registry.aliyuncs.com/google_containers/kube-scheduler:v1.21.1 k8s.gcr.io/kube-scheduler:v1.21.1
    docker rmi registry.aliyuncs.com/google_containers/kube-scheduler:v1.21.1
    
    docker pull registry.aliyuncs.com/google_containers/kube-proxy:v1.21.1
    docker tag registry.aliyuncs.com/google_containers/kube-proxy:v1.21.1 k8s.gcr.io/kube-proxy:v1.21.1
    docker rmi registry.aliyuncs.com/google_containers/kube-proxy:v1.21.1
    
    docker pull registry.aliyuncs.com/google_containers/pause:3.4.1
    docker tag registry.aliyuncs.com/google_containers/pause:3.4.1 k8s.gcr.io/pause:3.4.1
    docker rmi registry.aliyuncs.com/google_containers/pause:3.4.1
    
    docker pull registry.aliyuncs.com/google_containers/etcd:3.4.13-0
    docker tag registry.aliyuncs.com/google_containers/etcd:3.4.13-0 k8s.gcr.io/etcd:3.4.13-0
    docker rmi registry.aliyuncs.com/google_containers/etcd:3.4.13-0
    
    docker pull coredns/coredns:v1.8.0
    docker tag coredns/coredns:v1.8.0 k8s.gcr.io/coredns:v1.8.0
    docker rmi coredns/coredns:v1.8.0
    ```

+ 

  

## 节点配置

+ 配置host

  + 设置本机hostname

    ```shell
    # 设置本机hostname
    $ hostnamectl set-hostname k8s-master01
    $ hostnamectl set-hostname k8s-node01
    $ hostnamectl set-hostname k8s-node02
    # 查看hostname
    $ hostname
    ```

  + 配置`IP Host`映射

    ```shell
    vi /etc/hosts
    192.168.66.10 k8s-master01
    192.168.66.11 k8s-node01
    192.168.66.12 k8s-node02
    ```

    

