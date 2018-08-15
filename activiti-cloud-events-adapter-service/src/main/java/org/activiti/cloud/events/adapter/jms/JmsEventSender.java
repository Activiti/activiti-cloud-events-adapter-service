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

import org.alfresco.event.model.EventV1;
import org.alfresco.event.model.ResourceV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Jamal Kaabi-Mofrad
 */
@Service
public class JmsEventSender {

    private static Logger LOGGER = LoggerFactory.getLogger(JmsEventSender.class);

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsEventSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(EventV1<ResourceV1> event) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Sending event: " + event);
        }
        jmsTemplate.convertAndSend(event);
    }
}
