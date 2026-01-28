package com.hakka.tour.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Statistics {
    private Long id;
    private String statisticType;
    private Long statisticValue;
    private LocalDate recordDate;
    private String remarks;
}
