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

package org.activiti.cloud.events.adapter.util;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;

import java.util.ArrayList;
import java.util.List;

public class EventReceiverUtil {

    private List<String> events = new ArrayList<>();

    public void reset() {
        events.clear();
    }

    public List<String> getEvents() {
        return events;
    }

    @JmsListener(destination = "${activiti.cloud.events.adapter.topic.topicName}")
    public void receive(final Message message) {
        events.add((String) message.getPayload());
    }
}
