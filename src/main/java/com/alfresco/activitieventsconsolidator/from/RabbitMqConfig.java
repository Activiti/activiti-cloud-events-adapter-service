package com.alfresco.activitieventsconsolidator.from;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration of the RabbitMQ connection for the source broker and the camel from-route.
 *
 * @author Jamal Kaabi-Mofrad
 */
@Configuration
@ConditionalOnProperty(prefix = "messaging.from.rabbitmq.camelRoute", name = "fromRoute")
@EnableConfigurationProperties(RabbitMqProperties.class)
public class RabbitMqConfig {

    private final RabbitMqProperties properties;

    @Autowired
    public RabbitMqConfig(RabbitMqProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ConnectionFactory rabbitmqConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(properties.getHost());
        factory.setPort(properties.getPort());
        factory.setUsername(properties.getUsername());
        factory.setPassword(properties.getPassword());
        factory.setVirtualHost(properties.getVirtualHost());
        return factory;
    }

    @Bean
    public String camelFromRoute() {
        return properties.getCamelRoute().getFromRoute();
    }
}


