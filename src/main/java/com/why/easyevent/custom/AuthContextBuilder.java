package com.why.easyevent.custom;

import com.netflix.graphql.dgs.context.DgsCustomContextBuilderWithRequest;
import com.why.easyevent.entity.UserEntity;
import com.why.easyevent.mapper.UserEntityMapper;
import com.why.easyevent.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/11 17 08
 * @Version: v1.0
 */
@Slf4j
@Component
public class AuthContextBuilder implements DgsCustomContextBuilderWithRequest {


    private final UserEntityMapper userEntityMapper;

    public AuthContextBuilder(UserEntityMapper userEntityMapper) {
        this.userEntityMapper = userEntityMapper;
    }

    static final String AUTHORIZATION_HEADER = "Authorization";
    @Override
    public Object build(@Nullable Map map, @Nullable HttpHeaders httpHeaders, @Nullable WebRequest webRequest) {
        log.info("Building Auth Context...");
        AuthContext authContext = new AuthContext();
        if(!httpHeaders.containsKey(AUTHORIZATION_HEADER)) {
            log.info("用户未认证");
            return authContext;
        }
        String authorization = httpHeaders.getFirst(AUTHORIZATION_HEADER);
        String token = authorization.replace("Bearer ", "");

        Integer userId;
        try {
            userId = TokenUtils.verifyToken(token);
        } catch (Exception e) {
            authContext.setTokenInvalid(true);
            return authContext;
        }

        UserEntity userEntity = userEntityMapper.selectById(userId);
        if(userEntity == null) {
            authContext.setTokenInvalid(true);
            return authContext;
        }

        authContext.setUserEntity(userEntity);
        log.info("用户认证成功，{}", userId);
        return authContext;
    }
}
