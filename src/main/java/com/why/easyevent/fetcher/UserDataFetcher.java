package com.why.easyevent.fetcher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.netflix.graphql.dgs.*;
import com.why.easyevent.entity.EventEntity;
import com.why.easyevent.entity.UserEntity;
import com.why.easyevent.mapper.EventEntityMapper;
import com.why.easyevent.mapper.UserEntityMapper;
import com.why.easyevent.type.*;
import com.why.easyevent.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/10 16 23
 * @Version: v1.0
 */

@Slf4j
@DgsComponent
public class UserDataFetcher {

    private final UserEntityMapper userEntityMapper;

    private final PasswordEncoder passwordEncoder;

    private final EventEntityMapper eventEntityMapper;

    public UserDataFetcher(UserEntityMapper userEntityMapper, PasswordEncoder passwordEncoder, EventEntityMapper eventEntityMapper) {
        this.userEntityMapper = userEntityMapper;
        this.passwordEncoder = passwordEncoder;
        this.eventEntityMapper = eventEntityMapper;
    }

    @DgsQuery
    public List<User> users() {
        List<UserEntity> userEntities = userEntityMapper.selectList(new QueryWrapper<>());
        List<User> userList = userEntities.stream()
                .map(User::fromEntity).collect(Collectors.toList());
        return userList;
    }

    @DgsQuery
    public AuthData login(@InputArgument LoginInput loginInput) {
        UserEntity userEntity = finduserByEmail(loginInput.getEmail());
        if(userEntity == null) {
            throw new RuntimeException("使用该email地址用户不存在");
        }
        boolean match = passwordEncoder.matches(loginInput.getPassword(), userEntity.getPassword());
        if(!match) {
            throw new RuntimeException("密码不正确");
        }

        Integer id = userEntity.getId();
        log.info("userId = {}", id);

        String token = TokenUtils.signToken(id, 1);

        AuthData authData = new AuthData();
        authData.setUserId(id);
        authData.setToken(token);
        authData.setTokenExpiration(1);
        return authData;
    }

    @DgsMutation
    public User createUser(@InputArgument UserInput userInput) {
        ensureUserNotExists(userInput);//判断用户不存在

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userInput.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userInput.getPassword()));

        userEntityMapper.insert(userEntity);

        return User.fromEntity(userEntity);
    }

    @DgsData(parentType = "User", field = "createdEvents")
    public List<Event> createdEvents(DgsDataFetchingEnvironment dgs) {
        User user = dgs.getSource();
        QueryWrapper<EventEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EventEntity::getCreatorId, user.getId());
        List<EventEntity> eventEntities = eventEntityMapper.selectList(queryWrapper);
        List<Event> eventList = eventEntities.stream()
                .map(Event::fromEntity).collect(Collectors.toList());
        return eventList;
    }


    private void ensureUserNotExists(UserInput userInput) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getEmail, userInput.getEmail());
        if(userEntityMapper.selectCount(queryWrapper) >= 1) {
            throw new RuntimeException("该email地址已经被使用");
        }
    }

    private UserEntity finduserByEmail(String email) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getEmail, email);
        return userEntityMapper.selectOne(queryWrapper);
    }
}
