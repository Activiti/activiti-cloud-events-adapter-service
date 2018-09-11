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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.activiti.runtime.api.event.CloudRuntimeEvent;
import org.alfresco.event.model.ResourceV1;
import org.springframework.stereotype.Component;

@Component
public class EventTransformerRegistry<T extends CloudRuntimeEvent, R extends ResourceV1> {

    private final ConcurrentMap<String, EventTransformer<T, R>> registry;

    public EventTransformerRegistry() {
        this.registry = new ConcurrentHashMap<>();
    }

    /**
     * Register an instance of {@code EventTransformer} with the
     * specified event type.
     *
     * @param eventType        the event type
     * @param eventTransformer the event transformer
     */
    public void addTransformer(String eventType, EventTransformer<T, R> eventTransformer) {
        registry.putIfAbsent(eventType, eventTransformer);
    }

    /**
     * Gets the vent transformer.
     *
     * @param eventType the event type to perform the lookup
     * @return the event transformer or null if none found
     */
    public EventTransformer<T, R> getTransformer(String eventType) {
        return registry.get(eventType);
    }
}
