package com.hakka.tour.controller;

import com.hakka.tour.dto.ResponseDTO;
import com.hakka.tour.entity.Resource;
import com.hakka.tour.service.ResourceService;
import com.hakka.tour.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resource")
@CrossOrigin
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private StatisticsService statisticsService;

    @PostMapping("/create")
    public ResponseDTO<Resource> create(@RequestBody Resource resource) {
        Resource created = resourceService.create(resource);
        return ResponseDTO.success(created);
    }

    @PutMapping("/update")
    public ResponseDTO<Resource> update(@RequestBody Resource resource) {
        Resource updated = resourceService.update(resource);
        return ResponseDTO.success(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Boolean> delete(@PathVariable Long id) {
        boolean result = resourceService.delete(id);
        return ResponseDTO.success(result);
    }

    @GetMapping("/{id}")
    public ResponseDTO<Resource> getById(@PathVariable Long id) {
        resourceService.incrementHotCount(id);
        statisticsService.increment("resource_view");
        Resource resource = resourceService.getById(id);
        return ResponseDTO.success(resource);
    }

    @GetMapping("/list")
    public ResponseDTO<List<Resource>> getAll() {
        List<Resource> resources = resourceService.getAll();
        return ResponseDTO.success(resources);
    }

    @PostMapping("/search")
    public ResponseDTO<List<Resource>> search(@RequestBody Map<String, Object> params) {
        List<Resource> resources = resourceService.search(params);
        return ResponseDTO.success(resources);
    }

    @PostMapping("/{id}/rate")
    public ResponseDTO<Boolean> rate(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        Double score = Double.parseDouble(params.get("score").toString());
        resourceService.updateScore(id, score);
        return ResponseDTO.success(true);
    }
}
