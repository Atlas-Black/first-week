package com.yiguan.firstweek.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiguan.firstweek.model.Device;
import com.yiguan.firstweek.mapper.DeviceMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceCheckTask {

    private final DeviceMapper deviceMapper;

    public DeviceCheckTask(DeviceMapper deviceMapper) {
        this.deviceMapper = deviceMapper;
    }

    //每隔一分钟执行一次
    @Scheduled(cron = "0 */1 * * * ?")
    public void checkBorrowedDevice() {
        System.out.println("定时任务开始执行，线程名：" + Thread.currentThread().getName());

        LambdaQueryWrapper<Device> queryWrapper = new LambdaQueryWrapper<>();
        //先查已借出的设备，然后打印
        queryWrapper.eq(Device::getStatus, 2);

        List<Device> deviceList = deviceMapper.selectList(queryWrapper);

        for (Device device : deviceList) {
            System.out.println("【超时告警】设备 " + device.getDeviceName() + " 未归还！");
        }
    }
}