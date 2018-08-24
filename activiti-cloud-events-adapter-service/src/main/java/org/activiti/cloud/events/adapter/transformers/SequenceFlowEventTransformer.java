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

import org.activiti.runtime.api.event.CloudSequenceFlowEvent;
import org.activiti.runtime.api.model.SequenceFlow;
import org.alfresco.event.model.EventV1;
import org.alfresco.event.model.activiti.SequenceFlowResourceV1;
import org.springframework.stereotype.Component;

@Component
public class SequenceFlowEventTransformer extends AbstractEventTransformer<CloudSequenceFlowEvent, SequenceFlowResourceV1> {

    private static final String SUPPORTED_EVENT_TYPES = "SEQUENCE_FLOW_TAKEN";

    public SequenceFlowEventTransformer() {
        super(SUPPORTED_EVENT_TYPES);
    }

    @Override
    public EventV1<SequenceFlowResourceV1> transform(CloudSequenceFlowEvent event) {
        SequenceFlowResourceV1 resource = new SequenceFlowResourceV1(event.getId(), null);
        setCommonValues(event, resource);

        SequenceFlow entity = event.getEntity();
        resource.setProcessInstanceId(entity.getProcessInstanceId());
        resource.setProcessDefinitionId(entity.getProcessDefinitionId());
        resource.setSourceActivityElementId(entity.getSourceActivityElementId());
        resource.setSourceActivityName(entity.getSourceActivityName());
        resource.setSourceActivityType(entity.getSourceActivityType());
        resource.setTargetActivityElementId(entity.getTargetActivityElementId());
        resource.setTargetActivityName(entity.getTargetActivityName());
        resource.setTargetActivityType(entity.getTargetActivityType());

        return new EventV1<>(event.getEventType().name(), null, resource);
    }
}