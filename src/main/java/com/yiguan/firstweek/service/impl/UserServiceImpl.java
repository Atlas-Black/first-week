package com.yiguan.firstweek.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiguan.firstweek.mapper.UserMapper;
import com.yiguan.firstweek.model.User;
import com.yiguan.firstweek.service.UserService;
import com.yiguan.firstweek.utils.JwtUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public String login(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username)
                .eq(User::getPassword, password);

        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            return null;
        }

        return JwtUtils.createToken(user.getId());
    }
}