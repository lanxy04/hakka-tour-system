package com.hakka.tour.mapper;

import com.hakka.tour.entity.Route;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface RouteMapper {
    int insert(Route route);
    int update(Route route);
    int deleteById(Long id);
    Route selectById(Long id);
    List<Route> selectByUserId(Long userId);
    List<Route> selectAll();
}
