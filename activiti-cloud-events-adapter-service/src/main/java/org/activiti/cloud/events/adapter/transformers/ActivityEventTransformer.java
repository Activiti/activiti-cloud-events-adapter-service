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

import org.activiti.runtime.api.event.BPMNActivityEvent.ActivityEvents;
import org.activiti.runtime.api.event.CloudBPMNActivityCancelled;
import org.activiti.runtime.api.event.CloudBPMNActivityEvent;
import org.activiti.runtime.api.model.BPMNActivity;
import org.alfresco.event.model.activiti.ActivityResourceV1;
import org.alfresco.event.model.EventV1;
import org.springframework.stereotype.Component;

@Component
public class ActivityEventTransformer extends AbstractEventTransformer<CloudBPMNActivityEvent, ActivityResourceV1> {

    private static final String[] SUPPORTED_EVENT_TYPES = { "ACTIVITY_STARTED", "ACTIVITY_COMPLETED", "ACTIVITY_CANCELLED" };

    public ActivityEventTransformer() {
        super(SUPPORTED_EVENT_TYPES);
    }

    @Override
    public EventV1<ActivityResourceV1> transform(CloudBPMNActivityEvent event) {
        ActivityResourceV1 resource = new ActivityResourceV1(event.getId(), null);
        setCommonValues(event, resource);

        BPMNActivity entity = event.getEntity();
        resource.setActivityName(entity.getActivityName());
        resource.setActivityType(entity.getActivityType());
        resource.setElementId(entity.getElementId());
        resource.setProcessDefinitionId(entity.getProcessDefinitionId());
        resource.setProcessInstanceId(entity.getProcessInstanceId());

        if (event.getEventType() == ActivityEvents.ACTIVITY_CANCELLED) {
            resource.setCause(((CloudBPMNActivityCancelled) event).getCause());
        }

        return new EventV1<>(event.getEventType().name(), null, resource);
    }
}


