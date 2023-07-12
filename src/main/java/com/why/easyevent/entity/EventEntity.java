package com.why.easyevent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.why.easyevent.type.EventInput;
import com.why.easyevent.utils.DateUtils;
import lombok.Data;

import java.time.LocalDate;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/10 16 58
 * @Version: v1.0
 */
@Data
@TableName(value = "tb_event")
public class EventEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;
    private String description;
    private Float price;
    private LocalDate date;

    private Integer creatorId;

    public static EventEntity fromEventInput(EventInput input) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setTitle(input.getTitle());
        eventEntity.setDescription(input.getDescription());
        float priceInput = Float.parseFloat(input.getPrice());
        eventEntity.setPrice(priceInput);
        eventEntity.setDate(DateUtils.coverStringToLocalDate(input.getDate()));
//        eventEntity.setCreatorId((input.getCreatorId()));
        return eventEntity;
    }
}
