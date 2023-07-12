package com.why.easyevent.custom;

import com.why.easyevent.entity.UserEntity;
import lombok.Data;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/11 17 05
 * @Version: v1.0
 */
@Data
public class AuthContext {
    private UserEntity userEntity;
    private boolean tokenInvalid;

    public void ensureAuthenticated() {
        if(tokenInvalid) throw new RuntimeException("令牌无效");
        if(userEntity == null) throw new RuntimeException("未登录，请先登录");
    }
}
