package com.why.easyevent.fetcher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.context.DgsContext;
import com.why.easyevent.custom.AuthContext;
import com.why.easyevent.entity.EventEntity;
import com.why.easyevent.entity.UserEntity;
import com.why.easyevent.mapper.EventEntityMapper;
import com.why.easyevent.mapper.UserEntityMapper;
import com.why.easyevent.type.Event;
import com.why.easyevent.type.EventInput;
import com.why.easyevent.type.User;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/10 16 23
 * @Version: v1.0
 */

@DgsComponent
@RequiredArgsConstructor
public class EventDataFetcher {

    private final EventEntityMapper eventEntityMapper;
    private final UserEntityMapper userEntityMapper;

    /**
     * 查询事件-event
     * @return
     */
    @DgsQuery
    public List<Event> events() {
        List<EventEntity> eventEntities = eventEntityMapper.selectList(new QueryWrapper<EventEntity>());
        List<Event> eventList = eventEntities.stream()
                .map(Event::fromEntity).collect(Collectors.toList());
        return eventList;
    }

    /**
     * 创建事件
     * @param eventInput
     * @param dfe
     * @return
     */
    @DgsMutation
    public Event createEvent(@InputArgument EventInput eventInput,DataFetchingEnvironment dfe) {
        AuthContext authContext = DgsContext.getCustomContext(dfe);

        authContext.ensureAuthenticated();

        EventEntity eventEntity = EventEntity.fromEventInput(eventInput);

        eventEntity.setCreatorId(authContext.getUserEntity().getId());

        eventEntityMapper.insert(eventEntity);

        Event event = Event.fromEntity(eventEntity);

        return event;
    }

//    /**
//     * 使用userId查询User并填充至event
//     * @param event
//     * @param userId
//     */
    /*private void populateEventWithUser(Event event, Integer userId) {
        UserEntity userEntity = userEntityMapper.selectById(userId);
        User user = User.fromEntity(userEntity);
        event.setCreator(user);
    }*/

    @DgsData(parentType = "Event", field = "creator")
    public User creator(DgsDataFetchingEnvironment dfe) {
        Event event = dfe.getSource();
        Integer creatorId = event.getCreatorId();
        UserEntity userEntity = userEntityMapper.selectById((int) event.getCreatorId());
        User user = User.fromEntity(userEntity);
        return user;
    }





}
