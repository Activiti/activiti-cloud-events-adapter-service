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

import org.activiti.runtime.api.event.CloudTaskCandidateGroupEvent;
import org.activiti.runtime.api.model.TaskCandidateGroup;
import org.alfresco.event.model.EventV1;
import org.alfresco.event.model.activiti.TaskCandidateResourceV1;
import org.springframework.stereotype.Component;

/**
 * @author Jamal Kaabi-Mofrad
 */
@Component
public class TaskCandidateGroupEventTransformer extends AbstractEventTransformer<CloudTaskCandidateGroupEvent, TaskCandidateResourceV1> {

    private static final String[] SUPPORTED_EVENT_TYPES = { "TASK_CANDIDATE_GROUP_ADDED", "TASK_CANDIDATE_GROUP_REMOVED" };

    public TaskCandidateGroupEventTransformer() {
        super(SUPPORTED_EVENT_TYPES);
    }

    @Override
    public EventV1<TaskCandidateResourceV1> transform(CloudTaskCandidateGroupEvent event) {
        TaskCandidateResourceV1 resource = new TaskCandidateResourceV1(event.getId(), null);
        setCommonValues(event, resource);

        TaskCandidateGroup entity = event.getEntity();
        resource.setTaskId(entity.getTaskId());
        resource.setGroupId(entity.getGroupId());

        return new EventV1<>(event.getEventType().name(), null, resource);
    }
}

