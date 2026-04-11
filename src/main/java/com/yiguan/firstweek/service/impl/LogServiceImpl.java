package com.yiguan.firstweek.service.impl;

import com.yiguan.firstweek.service.LogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    //这个方法不要在主线程里同步执行，而是交给异步线程池去跑
    @Async
    @Override
    public void saveBorrowLog(Long deviceId) {
        System.out.println("开始记录借用日志，线程名：" + Thread.currentThread().getName());

        try {
            //模拟一个“慢动作”，主线程已经返回，但后台线程还在继续工作
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        //对比主线程名字与异步线程名字
        System.out.println("设备 " + deviceId + " 借用日志记录完成，线程名：" + Thread.currentThread().getName());
    }
}