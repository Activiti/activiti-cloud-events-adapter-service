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
package org.activiti.cloud.services.events.adapter.transformers;

import org.activiti.cloud.api.model.shared.events.CloudVariableEvent;
import org.activiti.api.model.shared.model.VariableInstance;
import org.alfresco.event.model.EventV1;
import org.alfresco.event.model.activiti.VariableResourceV1;
import org.springframework.stereotype.Component;

@Component
public class VariableEventTransformer extends AbstractEventTransformer<CloudVariableEvent, VariableResourceV1> {

    private static final String[] SUPPORTED_EVENT_TYPES = { "VARIABLE_CREATED", "VARIABLE_UPDATED", "VARIABLE_DELETED" };

    public VariableEventTransformer() {
        super(SUPPORTED_EVENT_TYPES);
    }

    @Override
    public EventV1<VariableResourceV1> transform(CloudVariableEvent event) {
        VariableResourceV1<?> resource = new VariableResourceV1<>(event.getId(), null);
        setCommonValues(event, resource);

        VariableInstance entity = event.getEntity();
        resource.setName(entity.getName());
        resource.setType(entity.getType());
        resource.setProcessInstanceId(entity.getProcessInstanceId());
        resource.setTaskVariable(entity.isTaskVariable());
        resource.setValue(entity.getValue());

        return new EventV1<>(event.getEventType().name(), null, resource);
    }
}

