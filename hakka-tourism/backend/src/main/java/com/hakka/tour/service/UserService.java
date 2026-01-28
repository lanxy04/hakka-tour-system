package com.hakka.tour.service;

import com.hakka.tour.entity.User;
import com.hakka.tour.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User register(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setRole(0);
        userMapper.insert(user);
        return user;
    }

    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
        return userMapper.selectById(user.getId());
    }

    public boolean delete(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    public List<User> getAll() {
        return userMapper.selectAll();
    }
}
