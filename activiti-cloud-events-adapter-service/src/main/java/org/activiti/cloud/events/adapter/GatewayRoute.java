package org.activiti.cloud.events.adapter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alfresco.event.model.EventV1;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Event gateway route builder.
 *
 * @author Jamal Kaabi-Mofrad
 */
@Component
public class GatewayRoute extends RouteBuilder {


    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayRoute.class);

    private final String camelFromRoute;
    private final String camelToRoute;

    private ArrayList<LinkedHashMap> processResponses;

    @Autowired
    public GatewayRoute(String camelFromRoute, String camelToRoute) {
        this.camelFromRoute = camelFromRoute;
        this.camelToRoute = camelToRoute;
    }

    private LinkedHashMap searchForEvent(String eventName)
    {
        LinkedHashMap eventFound = null;
        for(LinkedHashMap event: processResponses) {
            List<String> keys = new ArrayList<>(event.keySet());
            for(String key:keys) {
                if (event.get(key).equals(eventName)) {
                    System.out.println("Event found: "+event.get(key));
                    eventFound = event;
                }
            }
        }
        return eventFound;
    }

    private void getListOfEvents(Exchange exchange) throws JsonParseException, JsonMappingException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();

        processResponses = mapper.readValue(exchange.getIn().getBody(String.class), new TypeReference<List<?>>() { });
        LinkedHashMap event = searchForEvent("TaskCreatedEvent");
        EventV1 newEvent = new EventV1();

    }

    @Override
    public void configure() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Setting camel Route -  From:{}, To:{}", camelFromRoute, camelToRoute);
        }


        from(camelFromRoute).log("Processing ${body}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        getListOfEvents(exchange);
                }
                })
                .to(camelToRoute).log("finished");

    }

}