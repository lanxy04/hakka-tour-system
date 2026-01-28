package com.hakka.tour.controller;

import com.hakka.tour.dto.ResponseDTO;
import com.hakka.tour.dto.RouteRequestDTO;
import com.hakka.tour.entity.Route;
import com.hakka.tour.service.RouteService;
import com.hakka.tour.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/route")
@CrossOrigin
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private StatisticsService statisticsService;

    @PostMapping("/custom")
    public ResponseDTO<Route> createCustomRoute(@RequestBody RouteRequestDTO dto) {
        Route route = routeService.createCustomRoute(dto);
        statisticsService.increment("route_generate");
        return ResponseDTO.success(route);
    }

    @PutMapping("/update")
    public ResponseDTO<Route> update(@RequestBody Route route) {
        Route updated = routeService.update(route);
        return ResponseDTO.success(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Boolean> delete(@PathVariable Long id) {
        boolean result = routeService.delete(id);
        return ResponseDTO.success(result);
    }

    @GetMapping("/{id}")
    public ResponseDTO<Route> getById(@PathVariable Long id) {
        Route route = routeService.getById(id);
        return ResponseDTO.success(route);
    }

    @GetMapping("/user/{userId}")
    public ResponseDTO<List<Route>> getByUserId(@PathVariable Long userId) {
        List<Route> routes = routeService.getByUserId(userId);
        return ResponseDTO.success(routes);
    }

    @GetMapping("/list")
    public ResponseDTO<List<Route>> getAll() {
        List<Route> routes = routeService.getAll();
        return ResponseDTO.success(routes);
    }
}
