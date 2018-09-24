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

import org.activiti.cloud.api.task.model.events.CloudTaskCancelledEvent;
import org.activiti.cloud.api.task.model.events.CloudTaskRuntimeEvent;
import org.activiti.api.task.model.events.TaskRuntimeEvent.TaskEvents;
import org.activiti.api.task.model.Task;
import org.alfresco.event.model.EventV1;
import org.alfresco.event.model.activiti.TaskResourceV1;
import org.springframework.stereotype.Component;

@Component
public class TaskEventTransformer extends AbstractEventTransformer<CloudTaskRuntimeEvent, TaskResourceV1> {

    private static final String[] SUPPORTED_EVENT_TYPES = { "TASK_CREATED", "TASK_ASSIGNED", "TASK_COMPLETED",
                "TASK_ACTIVATED", "TASK_SUSPENDED", "TASK_CANCELLED" };

    public TaskEventTransformer() {
        super(SUPPORTED_EVENT_TYPES);
    }

    @Override
    public EventV1<TaskResourceV1> transform(CloudTaskRuntimeEvent event) {
        TaskResourceV1 resource = new TaskResourceV1(event.getId(), null);
        setCommonValues(event, resource);

        Task entity = event.getEntity();
        resource.setName(entity.getName());
        resource.setProcessDefinitionId(entity.getProcessDefinitionId());
        resource.setProcessInstanceId(entity.getProcessInstanceId());
        resource.setPriority(entity.getPriority());
        resource.setStatus(entity.getStatus().name());
        resource.setAssignee(entity.getAssignee());
        resource.setCreatedDate(entity.getCreatedDate());
        resource.setClaimedDate(entity.getClaimedDate());

        if (event.getEventType() == TaskEvents.TASK_CANCELLED) {
            resource.setCause(((CloudTaskCancelledEvent) event).getCause());
        }

        return new EventV1<>(event.getEventType().name(), null, resource);
    }
}
