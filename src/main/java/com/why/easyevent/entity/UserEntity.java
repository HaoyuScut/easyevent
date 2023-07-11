package com.why.easyevent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.why.easyevent.type.UserInput;
import lombok.Data;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/11 11 26
 * @Version: v1.0
 */

@Data
@TableName(value = "tb_user")
public class UserEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String email;

    private String password;

    public UserEntity fromUserInput(UserInput userInput) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userInput.getEmail());
        userEntity.setPassword(userInput.getPassword());
        return userEntity;
    }

}
