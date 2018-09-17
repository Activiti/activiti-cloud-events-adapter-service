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
package org.activiti.cloud.events.adapter;

import org.activiti.cloud.events.adapter.util.EventReceiverUtil;
import org.activiti.cloud.events.adapter.util.TestUtil;
import org.activiti.cloud.services.events.adapter.streams.EventsAdapterConsumerChannel;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiCloudEventsAdapterTest {

    @ClassRule
    public static EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();

    private static ApplicationContext applicationContext;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EventsAdapterConsumerChannel channel;
    @Autowired
    private EventReceiverUtil receiverUtil;

    @Autowired
    private void setContext(ApplicationContext applicationContext) {
        ActivitiCloudEventsAdapterTest.applicationContext = applicationContext;
    }

    @AfterClass
    public static void afterClass() {
        ((ConfigurableApplicationContext) applicationContext).close();
    }

    @After
    public void tearDown()
    {
        receiverUtil.reset();
    }

    @Test
    public void testProcessStartedEvent() throws Exception {
        AbstractMessageChannel adapterConsumer = (AbstractMessageChannel) this.channel.eventsAdapterConsumer();

        final String payload = getEventResourceAsString("aggregated-processStarted.json");
        adapterConsumer.send(new GenericMessage<>(payload));
        // wait for a few seconds to get all the messages
        await().untilAsserted(() -> {
            List<String> result = receiverUtil.getEvents();
            assertEquals("11 events must have been sent.", 11, result.size());
            //Checked the received events
            checkExpectedJson(getEventResourceAsString("public-processCreated-01.json"), result.get(0));
            checkExpectedJson(getEventResourceAsString("public-variableCreated-02.json"), result.get(1));
            checkExpectedJson(getEventResourceAsString("public-variableCreated-03.json"), result.get(2));
            checkExpectedJson(getEventResourceAsString("public-variableCreated-04.json"), result.get(3));
            checkExpectedJson(getEventResourceAsString("public-processStarted-05.json"), result.get(4));
            checkExpectedJson(getEventResourceAsString("public-activityStarted-06.json"), result.get(5));
            checkExpectedJson(getEventResourceAsString("public-activityCompleted-07.json"), result.get(6));
            checkExpectedJson(getEventResourceAsString("public-sequenceFlowTaken-08.json"), result.get(7));
            checkExpectedJson(getEventResourceAsString("public-activityStarted-09.json"), result.get(8));
            checkExpectedJson(getEventResourceAsString("public-taskCandidateGroupAdded-10.json"), result.get(9));
            checkExpectedJson(getEventResourceAsString("public-taskCreated-11.json"), result.get(10));
        });
    }

    @Test
    public void testTaskAssignedEvent() throws Exception {
        AbstractMessageChannel adapterConsumer = (AbstractMessageChannel) this.channel.eventsAdapterConsumer();

        final String payload = getEventResourceAsString("aggregated-taskAssigned.json");
        adapterConsumer.send(new GenericMessage<>(payload));
        // wait for a few seconds to get all the messages
        await().untilAsserted(()->{
            List<String> result = receiverUtil.getEvents();
            assertEquals("1 event must have been sent.", 1, result.size());
            //Checked the received events
            checkExpectedJson(getEventResourceAsString("public-taskAssigned.json"), result.get(0));
        });
    }

    private void checkExpectedJson(String expectedJsonBody, String actualJsonBody) throws Exception
    {
        // Enable strict mode - as we require all of the elements requested to be returned.
        // We can do that as our arrays of elements are always returned in the same order.
        JSONAssert.assertEquals(expectedJsonBody, actualJsonBody, true);
    }

    private String getEventResourceAsString(String eventFileName) throws Exception
    {
        return TestUtil.getResourceFileAsString("events/" + eventFileName);
    }
}
