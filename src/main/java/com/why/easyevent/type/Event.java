package com.why.easyevent.type;

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
}
