package com.yiguan.firstweek.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiguan.firstweek.mapper.UserMapper;
import com.yiguan.firstweek.model.User;
import com.yiguan.firstweek.service.UserService;
import com.yiguan.firstweek.utils.JwtUtils;
import org.springframework.stereotype.Service;
//登录真正执行的地方

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    //校验密码，成功后发身份证
    @Override
    public String login(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //构造查询条件，取数据库查
        queryWrapper.eq(User::getUsername, username)
                .eq(User::getPassword, password);
        //查数据库
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            return null;
        }
        //生成JWT，返回真正的Token
        return JwtUtils.createToken(user.getId());
    }
}