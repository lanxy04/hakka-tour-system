package com.hakka.tour.service;

import com.hakka.tour.entity.Statistics;
import com.hakka.tour.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;

    public Statistics increment(String statisticType) {
        LocalDate today = LocalDate.now();
        Statistics stats = statisticsMapper.selectByTypeAndDate(statisticType, today);

        if (stats == null) {
            stats = new Statistics();
            stats.setStatisticType(statisticType);
            stats.setStatisticValue(1L);
            stats.setRecordDate(today);
            statisticsMapper.insert(stats);
        } else {
            statisticsMapper.incrementValue(statisticType, today);
            stats.setStatisticValue(stats.getStatisticValue() + 1);
        }

        return stats;
    }

    public List<Map<String, Object>> getStatisticsByType(String statisticType) {
        return statisticsMapper.selectStatisticsByType(statisticType);
    }

    public List<Map<String, Object>> getAllStatistics() {
        return statisticsMapper.selectAllStatistics();
    }
}
