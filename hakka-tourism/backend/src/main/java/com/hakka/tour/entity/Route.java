package com.hakka.tour.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Route {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Integer days;
    private Double budget;
    private Integer peopleCount;
    private String routeData;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
