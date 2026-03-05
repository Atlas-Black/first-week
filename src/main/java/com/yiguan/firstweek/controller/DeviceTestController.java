package com.yiguan.firstweek.controller;

import com.yiguan.firstweek.model.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class DeviceTestController {

    @GetMapping("/test/device/mock")
    public Device mockDevice() {
        Device device = new Device();
        device.setDeviceId(10001L);
        device.setDeviceName("会议室A投影设备");
        device.setDeviceType("投影仪");
        device.setStatus(0);
        device.setLastCheckTime(LocalDateTime.now());
        return device;
    }
}