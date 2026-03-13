package com.yiguan.firstweek.controller;

import com.yiguan.firstweek.model.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

//Controller 负责接收 HTTP 请求并映射到具体方法，方法返回 Java 对象后由 Spring Boot 的 Jackson 自动序列化为 JSON 写入响应体，从而实现 REST 接口的标准化返回。

@RestController

//@Controller：声明这是 Controller，能被 Spring 管理并参与路由映射
//@ResponseBody：把方法返回值写进 HTTP 响应体（Response Body）

public class DeviceTestController {

    //当请求方法是 GET 且路径是 /test/device/mock 时，执行 mockDevice()。
    @GetMapping("/test/device/mock")
    public Device mockDevice() {    //声明这个接口方法返回一个 Device 对象。
        Device device = new Device();
        device.setId(10001L);
        device.setDeviceName("会议室A投影设备");
        device.setDeviceType("投影仪");
        device.setStatus(0);
        device.setLastCheckTime(LocalDateTime.now());
        return device;  //把 device 交给 Spring，由它序列化成 JSON 返回。
    }
}