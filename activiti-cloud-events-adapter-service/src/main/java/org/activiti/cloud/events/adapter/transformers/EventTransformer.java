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

import org.activiti.runtime.api.event.CloudRuntimeEvent;
import org.alfresco.event.model.EventV1;
import org.alfresco.event.model.ResourceV1;

/**
 * Represents a transformer which converts the Activiti cloud runtime event
 * {@link CloudRuntimeEvent} to <i>Public</i> event {@link EventV1}.
 */
public interface EventTransformer<T extends CloudRuntimeEvent, R extends ResourceV1> {

    /**
     * Transforms the Activiti cloud runtime event to <i>Public</i> event.
     *
     * @param event the Activiti cloud runtime event
     * @return the public event
     */
    EventV1<R> transform(T event);
}
