package com.hakka.tour.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private Long userId;
    private Long routeId;
    private String orderNo;
    private Double amount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
