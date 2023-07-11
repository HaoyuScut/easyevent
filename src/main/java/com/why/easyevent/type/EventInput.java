package com.why.easyevent.type;

/**
 * @ClassName: {NAME}
 * @Auther: why
 * @Date: 2023/07/10 16 58
 * @Version: v1.0
 */
import lombok.Data;

@Data
public class EventInput {
    private String title;
    private String description;
    private String price;
    private String date;
    private Integer creatorId;
}