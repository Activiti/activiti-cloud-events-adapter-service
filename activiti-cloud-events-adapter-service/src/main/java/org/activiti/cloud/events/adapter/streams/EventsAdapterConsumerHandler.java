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
package org.activiti.cloud.events.adapter.streams;

import org.activiti.cloud.events.adapter.jms.JmsEventSender;
import org.activiti.cloud.events.adapter.transformers.EventTransformer;
import org.activiti.cloud.events.adapter.transformers.EventTransformerRegistry;
import org.activiti.cloud.events.adapter.transformers.GenericEventTransformer;
import org.activiti.runtime.api.event.CloudRuntimeEvent;
import org.alfresco.event.model.EventV1;
import org.alfresco.event.model.ResourceV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author Jamal Kaabi-Mofrad
 */
@Component
@EnableBinding(EventsAdapterConsumerChannel.class)
public class EventsAdapterConsumerHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(EventsAdapterConsumerHandler.class);

    private final EventTransformerRegistry<CloudRuntimeEvent, ResourceV1> eventTransformerRegistry;
    private final JmsEventSender eventSender;

    @Autowired
    public EventsAdapterConsumerHandler(EventTransformerRegistry<CloudRuntimeEvent, ResourceV1> eventTransformerRegistry,
                JmsEventSender eventSender) {
        this.eventTransformerRegistry = eventTransformerRegistry;
        this.eventSender = eventSender;
    }

    @StreamListener(EventsAdapterConsumerChannel.EVENTS_ADAPTER_CONSUMER)
    public void receiveCloudRuntimeEvent(CloudRuntimeEvent<?, ?>... events) {
        if (events != null) {
            for (CloudRuntimeEvent event : events) {
                EventTransformer<CloudRuntimeEvent, ResourceV1> transformer = eventTransformerRegistry.getTransformer(event.getEventType().name());

                if (transformer == null) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("No transformer is registered for the event type ["
                                    + event.getEventType().name() + "]. Using the generic transformer.");
                    }
                    transformer = eventTransformerRegistry.getTransformer(GenericEventTransformer.UNKNOWN_TYPE);
                }

                EventV1<ResourceV1> publicEvent = transformer.transform(event);
                eventSender.send(publicEvent);
            }
        }
    }
}
