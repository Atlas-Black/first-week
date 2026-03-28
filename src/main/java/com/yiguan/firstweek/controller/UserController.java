package com.yiguan.firstweek.controller;

import com.yiguan.firstweek.common.Result;
import com.yiguan.firstweek.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");

        String result = userService.login(username, password);

        if (result == null) {
            return Result.fail(500, "用户名或密码错误");
        }

        return Result.success(result);
    }
}