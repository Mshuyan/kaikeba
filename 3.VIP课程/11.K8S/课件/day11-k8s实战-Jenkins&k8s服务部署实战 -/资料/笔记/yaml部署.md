# 1、nginx

1）创建deployment

```yaml
#创建deployment
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-deploy
  namespace: default
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
      release: stabel
  template:
    metadata:
      labels:
        app: myapp
        release: stabel
        env: test
    spec:
      containers:
      - name: myapp
        image: nginx:v1
        imagePullPolicy: IfNotPresent
        ports:
        - name: http
          containerPort: 80
---        
#创建service服务
apiVersion: v1
kind: Service
metadata:
  name: myweb
  namespace: default
spec:
  type: ClusterIP
  selector:
    app: myapp
    release: stabel
  ports:
  - name: http
    port: 80
    targetPort: 80
```

# 2、eureka

1)  deployment

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: eureka
  namespace: default
spec:
  replicas: 1
  selector:
    matchLabels:
      app: myapp
      release: stable
  template:
    metadata:
      labels:
        app: myapp
        release: stable
        env: test
    spec:
      containers:
      - name: myapp
        image: hub.kaikeba.com/supergo/supergo-eureka:1.0-SNAPSHOT
        imagePullPolicy: IfNotPresent
        ports:
        - name: http
          containerPort: 10086
---
apiVersion: v1
kind: Service
metadata:
  name: eureka-svc
  namespace: default
spec:
  type: NodePort
  selector:
    app: myapp
    release: stable
  ports:
  - name: http
    port: 80
    targetPort: 10086
```

# 3、mysql

```yaml
apiVersion: apps/v1
kind: ReplicaSet
metadata:
  name: mysql-rs
  labels:
    name: mysql-rs
spec:
  replicas: 1
  selector: 
    matchLabels:
      name: mysql-pod
  template:
    metadata:
      labels:
        name: mysql-pod
    spec:
      containers:
      - name: mysql
        image: hub.kaikeba.com/library/mysql:v2
        imagePullPolicy: IfNotPresent
        ports:
        - containerPort: 3306
        env:
        - name: MYSQL_ROOT_PASSWORD
          value: "admin"
---
apiVersion: v1
kind: Service
metadata:
  name: mysql-svc
  labels:
    name: mysql-svc
spec:
  type: NodePort
  ports:
  - port: 3306
    protocol: TCP
    targetPort: 3306
    name: http
    nodePort: 32306
  selector:
    name: mysql-pod
```



# 4、user

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: user-deploy
  namespace: default
spec:
  replicas: 1
  selector:
    matchLabels:
      app: myapp
      release: stable
  template:
    metadata:
      labels:
        app: myapp
        release: stable
        env: test
    spec:
      containers:
      - name: myapp
        image: hub.kaikeba.com/supergo/supergo-user-web:1.0-SNAPSHOT
        imagePullPolicy: IfNotPresent
        ports:
        - name: http
          containerPort: 8882
---
apiVersion: v1
kind: Service
metadata:
  name: user-svc
  namespace: default
spec:
  type: ClusterIP
  selector:
    app: myapp
    release: stable
  ports:
  - name: http
    port: 80
    targetPort: 8882
```

