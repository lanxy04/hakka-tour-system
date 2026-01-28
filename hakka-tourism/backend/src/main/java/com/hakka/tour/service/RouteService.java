package com.hakka.tour.service;

import com.hakka.tour.dto.RouteRequestDTO;
import com.hakka.tour.entity.Resource;
import com.hakka.tour.entity.Route;
import com.hakka.tour.mapper.ResourceMapper;
import com.hakka.tour.mapper.RouteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    public Route createCustomRoute(RouteRequestDTO dto) {
        Route route = new Route();
        route.setUserId(dto.getUserId());
        route.setName(dto.getName());
        route.setDescription(dto.getDescription());
        route.setDays(dto.getDays());
        route.setBudget(dto.getBudget());
        route.setPeopleCount(dto.getPeopleCount());
        route.setStatus(0);
        route.setCreateTime(LocalDateTime.now());
        route.setUpdateTime(LocalDateTime.now());

        Map<String, Object> params = new HashMap<>();
        if (dto.getPreferences() != null && !dto.getPreferences().isEmpty()) {
            params.put("theme", dto.getPreferences());
        }
        params.put("maxPrice", dto.getBudget() / dto.getDays());

        List<Resource> resources = resourceMapper.selectByCondition(params);
        String routeData = generateRouteData(resources, dto.getDays());

        route.setRouteData(routeData);
        routeMapper.insert(route);

        return route;
    }

    public Route update(Route route) {
        route.setUpdateTime(LocalDateTime.now());
        routeMapper.update(route);
        return routeMapper.selectById(route.getId());
    }

    public boolean delete(Long id) {
        return routeMapper.deleteById(id) > 0;
    }

    public Route getById(Long id) {
        return routeMapper.selectById(id);
    }

    public List<Route> getByUserId(Long userId) {
        return routeMapper.selectByUserId(userId);
    }

    public List<Route> getAll() {
        return routeMapper.selectAll();
    }

    private String generateRouteData(List<Resource> resources, Integer days) {
        if (resources.isEmpty()) {
            return "[]";
        }

        Map<String, List<Resource>> groupedByRegion = resources.stream()
                .collect(Collectors.groupingBy(Resource::getRegion));

        List<Map<String, Object>> routePlan = new ArrayList<>();
        int day = 1;

        for (Map.Entry<String, List<Resource>> entry : groupedByRegion.entrySet()) {
            if (day > days) break;

            Map<String, Object> dayPlan = new HashMap<>();
            dayPlan.put("day", day);
            dayPlan.put("region", entry.getKey());
            dayPlan.put("resources", entry.getValue().subList(0, Math.min(3, entry.getValue().size())));

            routePlan.add(dayPlan);
            day++;
        }

        return Arrays.toString(routePlan.toArray());
    }
}
