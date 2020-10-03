package com.sugo.seckill.web.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * @ClassName WebServerConfig
 * @Description 改造web服务，设置keepalive相关配置
 * @Author hubin
 * @Date 2020/8/15 21:46
 * @Version V1.0
 **/
@Component
public class WebServerConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory configurableWebServerFactory) {
        //使用对应工厂类定制tomcat connector
        ((TomcatServletWebServerFactory) configurableWebServerFactory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {

                //通过连接器获取nio2

                //定制keepalive
                // 如果30s之内，这个链接处于空闲状态，释放这个链接
                Http11NioProtocol http11NioProtocol = (Http11NioProtocol) connector.getProtocolHandler();
                //设置keepalive连接超时时间
                http11NioProtocol.setKeepAliveTimeout(30000);
                //设置连接数量
                http11NioProtocol.setMaxKeepAliveRequests(10000);
            }
        });
    }
}

