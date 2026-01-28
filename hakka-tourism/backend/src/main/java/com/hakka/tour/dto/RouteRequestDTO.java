package com.hakka.tour.dto;

import lombok.Data;

@Data
public class RouteRequestDTO {
    private Long userId;
    private String name;
    private String description;
    private Integer days;
    private Double budget;
    private Integer peopleCount;
    private String preferences;
    private String startTime;
}
