package com.hakka.tour.mapper;

import com.hakka.tour.entity.Statistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper {
    int insert(Statistics statistics);
    int update(Statistics statistics);
    Statistics selectByTypeAndDate(@Param("statisticType") String statisticType, @Param("recordDate") LocalDate recordDate);
    List<Map<String, Object>> selectStatisticsByType(@Param("statisticType") String statisticType);
    List<Map<String, Object>> selectAllStatistics();
    int incrementValue(@Param("statisticType") String statisticType, @Param("recordDate") LocalDate recordDate);
}
