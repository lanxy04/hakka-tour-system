package com.hakka.tour.mapper;

import com.hakka.tour.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface ResourceMapper {
    int insert(Resource resource);
    int update(Resource resource);
    int deleteById(Long id);
    Resource selectById(Long id);
    List<Resource> selectAll();
    List<Resource> selectByCondition(Map<String, Object> params);
    int incrementHotCount(Long id);
    int updateScore(@Param("id") Long id, @Param("score") Double score);
}
