package com.yiguan.firstweek.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yiguan.firstweek.context.UserContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //插入时自动补当前时间
        this.strictInsertFill(metaObject, "lastCheckTime", LocalDateTime.class, LocalDateTime.now());

        //不需要 Controller 或 Service 传 userId，而是直接从当前请求线程上下文里拿,从UserContext里取用户id
        Long userId = UserContext.getUserId();
        if (userId != null){
            //自动填creatBy，插入 device 时，自动记录是谁创建的
            this.strictInsertFill(metaObject, "createBy", Long.class, userId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    }
}