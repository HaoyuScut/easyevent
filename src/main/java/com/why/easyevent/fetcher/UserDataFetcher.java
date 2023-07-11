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
import com.why.easyevent.type.UserInput;
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

@DgsComponent
public class UserDataFetcher {

    private final UserEntityMapper userEntityMapper;

    private final PasswordEncoder passwordEncoder;

    public UserDataFetcher(UserEntityMapper userEntityMapper, PasswordEncoder passwordEncoder) {
        this.userEntityMapper = userEntityMapper;
        this.passwordEncoder = passwordEncoder;
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


    private void ensureUserNotExists(UserInput userInput) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserEntity::getEmail, userInput.getEmail());
        if(userEntityMapper.selectCount(queryWrapper) >= 1) {
            throw new RuntimeException("该email地址已经被使用");
        }
    }
}
