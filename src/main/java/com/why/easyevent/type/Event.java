package com.why.easyevent.type;

import com.why.easyevent.entity.EventEntity;
import com.why.easyevent.utils.DateUtils;
import lombok.Data;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/10 16 58
 * @Version: v1.0
 */
@Data
public class Event {
    private String id;
    private String title;
    private String description;
    private String price;
    private String date;
    private User creator;
    private Integer creatorId;

    public static Event fromEntity(EventEntity eventEntity) {
        Event event = new Event();
        event.setId(eventEntity.getId().toString());
        event.setTitle(eventEntity.getTitle());
        event.setDescription(eventEntity.getDescription());
        event.setPrice(eventEntity.getPrice() + "");
        event.setDate(DateUtils.coverLocalDateToString(eventEntity.getDate()));
        event.setCreatorId(eventEntity.getCreatorId());
        return event;
    }
}
