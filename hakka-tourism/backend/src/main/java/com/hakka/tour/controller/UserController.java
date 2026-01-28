package com.hakka.tour.controller;

import com.hakka.tour.dto.ResponseDTO;
import com.hakka.tour.entity.User;
import com.hakka.tour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseDTO<User> register(@RequestBody User user) {
        User registered = userService.register(user);
        return ResponseDTO.success(registered);
    }

    @PostMapping("/login")
    public ResponseDTO<User> login(@RequestBody User user) {
        User loggedUser = userService.login(user.getUsername(), user.getPassword());
        if (loggedUser != null) {
            loggedUser.setPassword(null);
            return ResponseDTO.success(loggedUser);
        }
        return ResponseDTO.error("用户名或密码错误");
    }

    @PutMapping("/update")
    public ResponseDTO<User> update(@RequestBody User user) {
        User updated = userService.update(user);
        return ResponseDTO.success(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Boolean> delete(@PathVariable Long id) {
        boolean result = userService.delete(id);
        return ResponseDTO.success(result);
    }

    @GetMapping("/{id}")
    public ResponseDTO<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseDTO.success(user);
    }

    @GetMapping("/list")
    public ResponseDTO<java.util.List<User>> getAll() {
        java.util.List<User> users = userService.getAll();
        users.forEach(u -> u.setPassword(null));
        return ResponseDTO.success(users);
    }
}
