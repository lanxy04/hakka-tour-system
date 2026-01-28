package com.hakka.tour.service;

import com.hakka.tour.entity.Order;
import com.hakka.tour.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public Order create(Long userId, Long routeId, Double amount) {
        Order order = new Order();
        order.setUserId(userId);
        order.setRouteId(routeId);
        order.setOrderNo(generateOrderNo());
        order.setAmount(amount);
        order.setStatus(0);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.insert(order);
        return order;
    }

    public Order update(Order order) {
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.update(order);
        return orderMapper.selectById(order.getId());
    }

    public boolean delete(Long id) {
        return orderMapper.deleteById(id) > 0;
    }

    public Order getById(Long id) {
        return orderMapper.selectById(id);
    }

    public List<Order> getByUserId(Long userId) {
        return orderMapper.selectByUserId(userId);
    }

    public List<Order> getAll() {
        return orderMapper.selectAll();
    }

    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis();
    }
}
