package com.hakka.tour.controller;

import com.hakka.tour.dto.ResponseDTO;
import com.hakka.tour.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@CrossOrigin
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/{type}")
    public ResponseDTO<List<Map<String, Object>>> getStatisticsByType(@PathVariable String type) {
        List<Map<String, Object>> statistics = statisticsService.getStatisticsByType(type);
        return ResponseDTO.success(statistics);
    }

    @GetMapping("/all")
    public ResponseDTO<List<Map<String, Object>>> getAllStatistics() {
        List<Map<String, Object>> statistics = statisticsService.getAllStatistics();
        return ResponseDTO.success(statistics);
    }
}
