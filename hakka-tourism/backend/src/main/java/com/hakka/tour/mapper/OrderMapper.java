package com.hakka.tour.mapper;

import com.hakka.tour.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderMapper {
    int insert(Order order);
    int update(Order order);
    int deleteById(Long id);
    Order selectById(Long id);
    List<Order> selectByUserId(Long userId);
    List<Order> selectAll();
}
