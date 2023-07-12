package com.why.easyevent.type;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/11 16 13
 * @Version: v1.0
 */
@Data
public class AuthData {
    private Integer userId;
    private String token;
    private Integer tokenExpiration;
}
