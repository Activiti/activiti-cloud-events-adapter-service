package org.activiti.cloud.events.adapter.from;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RabbitMQ configuration properties.
 *
 * @author Jamal Kaabi-Mofrad
 */
@ConfigurationProperties(prefix = "messaging.from.rabbitmq")
public class RabbitMqProperties {

    private final CamelRouteProperties camelRoute = new CamelRouteProperties();
    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;

    public CamelRouteProperties getCamelRoute() {
        return camelRoute;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

}
