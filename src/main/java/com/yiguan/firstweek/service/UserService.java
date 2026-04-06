package com.yiguan.firstweek.service;

//用户业务层接口，为了不让UserController不直接茶数据库，是一个登录逻辑
public interface UserService {

    String login(String username, String password);
}