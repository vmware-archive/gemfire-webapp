package io.pivotal.wealthfrontier.controllers;
import java.io.IOException;

import io.pivotal.wealthfrontier.business.EventHandler;

import io.pivotal.wealthfrontier.ui.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EventsRestController {

    @Autowired
    EventHandler eventHanlder;

    @RequestMapping(value = "/ingestevent", method = RequestMethod.POST,
            consumes = "application/json")
    @ResponseBody
    void commitTransaction(@RequestBody String json) throws
            JsonParseException, JsonMappingException, IOException {

        Event[] event;
        ObjectMapper mapper = new ObjectMapper();
        event = mapper.readValue(json, Event[].class);
        eventHanlder.handleEvent(event);
    }
}