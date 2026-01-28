package com.hakka.tour.service;

import com.hakka.tour.entity.Resource;
import com.hakka.tour.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String RESOURCE_CACHE_KEY = "resource:";
    private static final String RESOURCE_LIST_KEY = "resource:list";

    public Resource create(Resource resource) {
        resource.setCreateTime(LocalDateTime.now());
        resource.setUpdateTime(LocalDateTime.now());
        resource.setScore(0.0);
        resource.setHotCount(0);
        resourceMapper.insert(resource);
        clearCache();
        return resource;
    }

    public Resource update(Resource resource) {
        resource.setUpdateTime(LocalDateTime.now());
        resourceMapper.update(resource);
        clearCache();
        return resourceMapper.selectById(resource.getId());
    }

    public boolean delete(Long id) {
        boolean result = resourceMapper.deleteById(id) > 0;
        if (result) {
            clearCache();
        }
        return result;
    }

    public Resource getById(Long id) {
        String cacheKey = RESOURCE_CACHE_KEY + id;
        Resource resource = (Resource) redisTemplate.opsForValue().get(cacheKey);
        if (resource == null) {
            resource = resourceMapper.selectById(id);
            if (resource != null) {
                redisTemplate.opsForValue().set(cacheKey, resource, 1, TimeUnit.HOURS);
            }
        }
        return resource;
    }

    public List<Resource> getAll() {
        return resourceMapper.selectAll();
    }

    public List<Resource> search(Map<String, Object> params) {
        return resourceMapper.selectByCondition(params);
    }

    public void incrementHotCount(Long id) {
        resourceMapper.incrementHotCount(id);
        String cacheKey = RESOURCE_CACHE_KEY + id;
        redisTemplate.delete(cacheKey);
    }

    public void updateScore(Long id, Double score) {
        resourceMapper.updateScore(id, score);
        String cacheKey = RESOURCE_CACHE_KEY + id;
        redisTemplate.delete(cacheKey);
    }

    private void clearCache() {
        redisTemplate.delete(RESOURCE_LIST_KEY);
    }
}
