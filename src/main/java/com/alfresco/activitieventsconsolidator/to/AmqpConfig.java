/*
 * Copyright 2005-2018 Alfresco Software, Ltd. All rights reserved.
 *
 * License rights for this program may be obtained from Alfresco Software, Ltd.
 * pursuant to a written agreement and any use of this program without such an
 * agreement is prohibited.
 */

package com.alfresco.activitieventsconsolidator.to;

import org.apache.camel.component.amqp.AMQPComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration of the AMQP connection for the target broker and the camel to-route.
 *
 * @author Jamal Kaabi-Mofrad
 */
@Configuration
@ConditionalOnProperty(prefix = "messaging.to.activemq.camelRoute", name = "toRoute")
@EnableConfigurationProperties(AmqpProperties.class)
public class AmqpConfig
{
    private final AmqpProperties properties;

    @Autowired
    public AmqpConfig(AmqpProperties properties)
    {
        this.properties = properties;
    }

    @Bean
    public AMQPComponent amqpConnection()
    {
        return AMQPComponentFactory.getAmqpComponent(properties.getUrl(), properties.getUsername(), properties.getPassword());
    }

    @Bean
    public String camelToRoute()
    {
        return properties.getCamelRoute().getToRoute();
    }
}
