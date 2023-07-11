package com.why.easyevent.fetcher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.why.easyevent.entity.EventEntity;
import com.why.easyevent.entity.UserEntity;
import com.why.easyevent.mapper.EventEntityMapper;
import com.why.easyevent.mapper.UserEntityMapper;
import com.why.easyevent.type.Event;
import com.why.easyevent.type.EventInput;
import com.why.easyevent.type.User;
import com.why.easyevent.util.DateUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/10 16 23
 * @Version: v1.0
 */

@DgsComponent
public class EventDataFetcher {

    private final EventEntityMapper eventEntityMapper;
    private final UserEntityMapper userEntityMapper;

    public EventDataFetcher(EventEntityMapper eventEntityMapper, UserEntityMapper userEntityMapper) {
        this.eventEntityMapper = eventEntityMapper;
        this.userEntityMapper = userEntityMapper;
    }

    @DgsQuery
    public List<Event> events() {
        List<EventEntity> eventEntities = eventEntityMapper.selectList(new QueryWrapper<EventEntity>());
        List<Event> eventList = eventEntities.stream()
                .map(eventEntity -> {
                    Event event = Event.fromEntity(eventEntity);
                    populateEventWithUser(event, eventEntity.getCreatorId());
                    return event;
                }).collect(Collectors.toList());
        return eventList;
    }

    @DgsMutation
    public Event createEvent(@InputArgument EventInput eventInput) {
        EventEntity eventEntity = EventEntity.fromEventInput(eventInput);

        eventEntityMapper.insert(eventEntity);

        Event event = Event.fromEntity(eventEntity);

        populateEventWithUser(event, eventEntity.getCreatorId());

        return event;
    }

    private void populateEventWithUser(Event event, Integer userId) {
        UserEntity userEntity = userEntityMapper.selectById(userId);
        User user = User.fromEntity(userEntity);
        event.setCreator(user);
    }





}
