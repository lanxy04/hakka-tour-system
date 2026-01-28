package com.hakka.tour.mapper;

import com.hakka.tour.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserMapper {
    int insert(User user);
    int update(User user);
    int deleteById(Long id);
    User selectById(Long id);
    User selectByUsername(String username);
    List<User> selectAll();
}
