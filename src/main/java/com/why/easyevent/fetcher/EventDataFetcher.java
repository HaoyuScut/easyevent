package com.why.easyevent.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.why.easyevent.type.Event;
import com.why.easyevent.type.EventInput;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/10 16 23
 * @Version: v1.0
 */

@DgsComponent
public class EventDataFetcher {

    private List<Event> eventList = new ArrayList<>();


    @DgsQuery
    public List<Event> events() {
        return eventList;
    }


//    @DgsMutation
//    public String createEvent(@InputArgument String name) {
//
//        return name + "isCreated";
//    }

    @DgsMutation
    public Event createEvent(@InputArgument EventInput eventInput) {
        Event newevent = new Event();
        newevent.setId(UUID.randomUUID().toString());
        newevent.setTitle(eventInput.getTitle());
        newevent.setDescription(eventInput.getDescription());
        newevent.setPrice(eventInput.getPrice());
        newevent.setDate(eventInput.getDate());

        eventList.add(newevent);
        return newevent;
    }

}
