package com.alfresco.activitieventsconsolidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import javax.jms.JMSException;

@SpringBootApplication
public class ActivitiEventsConsolidatorApplication {

	public static void main(String[] args) throws Exception{
        SpringApplication.run(ActivitiEventsConsolidatorApplication.class, args);
//        consumer();
//        publisher();
	}

//	public static void publisher() throws JMSException {
//	    ActiveMqPublisher activeMqPublisher = new ActiveMqPublisher();
//	    activeMqPublisher.create("activiti", "activitiTopic");
//	    activeMqPublisher.sendName("cristina","sirbu");
//	    activeMqPublisher.closeConnection();
//    }

	public static void consumer() throws Exception{
//        com.alfresco.activitieventsconsolidator.RabbitMqReceiver consumer = new com.alfresco.activitieventsconsolidator.RabbitMqReceiver("engineEvents");
//        Thread consumerThread = new Thread(consumer);
//        consumerThread.start();
    }
}
