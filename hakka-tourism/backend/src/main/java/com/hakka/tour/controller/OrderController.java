package com.hakka.tour.controller;

import com.hakka.tour.dto.ResponseDTO;
import com.hakka.tour.entity.Order;
import com.hakka.tour.service.OrderService;
import com.hakka.tour.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StatisticsService statisticsService;

    @PostMapping("/create")
    public ResponseDTO<Order> create(@RequestBody Map<String, Object> params) {
        Long userId = Long.parseLong(params.get("userId").toString());
        Long routeId = Long.parseLong(params.get("routeId").toString());
        Double amount = Double.parseDouble(params.get("amount").toString());
        Order order = orderService.create(userId, routeId, amount);
        statisticsService.increment("order_create");
        return ResponseDTO.success(order);
    }

    @PutMapping("/update")
    public ResponseDTO<Order> update(@RequestBody Order order) {
        Order updated = orderService.update(order);
        return ResponseDTO.success(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Boolean> delete(@PathVariable Long id) {
        boolean result = orderService.delete(id);
        return ResponseDTO.success(result);
    }

    @GetMapping("/{id}")
    public ResponseDTO<Order> getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return ResponseDTO.success(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseDTO<List<Order>> getByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getByUserId(userId);
        return ResponseDTO.success(orders);
    }

    @GetMapping("/list")
    public ResponseDTO<List<Order>> getAll() {
        List<Order> orders = orderService.getAll();
        return ResponseDTO.success(orders);
    }
}
