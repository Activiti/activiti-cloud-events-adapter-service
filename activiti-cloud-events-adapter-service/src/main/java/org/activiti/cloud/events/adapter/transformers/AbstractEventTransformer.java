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
package org.activiti.cloud.events.adapter.transformers;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.activiti.runtime.api.event.CloudRuntimeEvent;
import org.alfresco.event.model.activiti.ActivitiCloudRuntimeResourceV1;
import org.alfresco.event.model.ResourceV1;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEventTransformer<T extends CloudRuntimeEvent, R extends ResourceV1> implements EventTransformer<T, R> {

    @Autowired
    private EventTransformerRegistry<T, R> registry;

    private Set<String> supportedTypes;

    public AbstractEventTransformer(String... supportedEventTypes) {
        if (supportedEventTypes == null) {
            throw new RuntimeException("Property 'supportedEventTypes' is mandatory.");
        }
        this.supportedTypes = Arrays.stream(supportedEventTypes).collect(Collectors.toSet());
    }

    @PostConstruct
    public final void register() {
        for (String eventType : supportedTypes) {
            registry.addTransformer(eventType, this);
        }
    }

    protected void setCommonValues(CloudRuntimeEvent event, ActivitiCloudRuntimeResourceV1 resource) {
        resource.setEntityId(event.getEntityId());
        resource.setTimestamp(event.getTimestamp());
        resource.setAppName(event.getAppName());
        resource.setAppVersion(event.getAppVersion());
        resource.setServiceFullName(event.getServiceFullName());
        resource.setServiceName(event.getServiceName());
        resource.setServiceVersion(event.getServiceVersion());
        resource.setServiceType(event.getServiceType());
    }
}
