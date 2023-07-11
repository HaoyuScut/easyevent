package com.why.easyevent.type;

import com.why.easyevent.entity.UserEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/11 13 56
 * @Version: v1.0
 */

@Data
public class User {
    private Integer id;
    private String email;
    private String password;
    private List<Event> createdEvents = new ArrayList<>();

    public static User fromEntity(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        return user;
    }
}
