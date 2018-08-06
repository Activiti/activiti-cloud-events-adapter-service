package com.alfresco.activitieventsconsolidator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.alfresco.event.databind.EventObjectMapperFactory;
import org.alfresco.event.model.EventV1;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import org.activiti.services.events.AbstractProcessEngineEvent;
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

    @Autowired
    public GatewayRoute(String camelFromRoute, String camelToRoute) {
        this.camelFromRoute = camelFromRoute;
        this.camelToRoute = camelToRoute;
    }

  /*  public void processResponseJSON(Exchange exchange) throws JsonParseException, JsonMappingException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();

        List<ProcessStartedEvent> processResponses = mapper.readValue(exchange.getIn().getBody(String.class), new TypeReference<List<>>() { });
        for(ProcessStartedEvent e:processResponses) {
            System.out.println(e.applicationName);
        }
        //exchange.getIn().setBody(personSearchResponses);

    }*/

    public static final ObjectMapper RAW_OBJECT_MAPPER = new ObjectMapper();
    public static final ObjectMapper PUBLIC_OBJECT_MAPPER = EventObjectMapperFactory.createInstance();

    @Bean
    public DataFormat rawDataFormat()
    {
        return new JacksonDataFormat(RAW_OBJECT_MAPPER, Object.class);
    }

    @Bean
    public DataFormat publicDataFormat()
    {
        return new JacksonDataFormat(PUBLIC_OBJECT_MAPPER, EventV1.class);
    }

    @Override
    public void configure() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Setting camel Route -  From:{}, To:{}", camelFromRoute, camelToRoute);
        }


        from(camelFromRoute).log("Processing ${body}")
//                .process(new Processor() {
//                    @Override
//                    public void process(Exchange exchange) throws Exception {
//                        //processResponseJSON(exchange);
//                }
//                })

                .to(camelToRoute).log("finished");

    }

}