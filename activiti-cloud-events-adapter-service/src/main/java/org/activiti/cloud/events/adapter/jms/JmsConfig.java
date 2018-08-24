/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.cloud.events.adapter.jms;

import javax.jms.Destination;

import org.activiti.cloud.events.adapter.EventGateway;
import org.alfresco.event.databind.EventObjectMapperFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.StringUtils;

/**
 * JMS configuration.
 */
@Configuration
@IntegrationComponentScan
@EnableConfigurationProperties(JmsProperties.class)
public class JmsConfig {

    private final JmsProperties properties;

    @Autowired
    public JmsConfig(JmsProperties properties) {
        this.properties = properties;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(properties.getUrl());
        connectionFactory.setUserName(StringUtils.isEmpty(properties.getUsername()) ? null : properties.getUsername());
        connectionFactory.setPassword(StringUtils.isEmpty(properties.getPassword()) ? null : properties.getPassword());

        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean
    public IntegrationFlow jmsOutboundFlow() {
        return IntegrationFlows
                    .from(EventGateway.EVENTS_ADAPTER_PRODUCER_CHANNEL)
                    .handle(Jms.outboundAdapter(jmsTemplate()))
                    .get();
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT); //to send a message containing a String
        converter.setObjectMapper(EventObjectMapperFactory.createInstance());
        return converter;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate(cachingConnectionFactory());
        Destination destination = new ActiveMQTopic(properties.getTopicName());
        template.setDefaultDestination(destination);
        template.setMessageConverter(jacksonJmsMessageConverter());
        return template;
    }
}
