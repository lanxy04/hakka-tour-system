package com.hakka.tour.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Resource {
    private Long id;
    private String name;
    private String region;
    private String type;
    private String theme;
    private String description;
    private String address;
    private Double score;
    private Integer hotCount;
    private Double price;
    private String images;
    private String tags;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
