package com.yiguan.firstweek.context;

public class UserContext {

    //给当前线程单独存一份 Long 类型的数据(UserId)
    private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();

    public static void setUserId(Long userId) {     //将当前用户id放进去
        THREAD_LOCAL.set(userId);
    }

    public static Long getUserId() {        //取出当前请求对应的用户
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}