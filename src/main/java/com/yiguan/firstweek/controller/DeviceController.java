package com.yiguan.firstweek.controller;

import com.yiguan.firstweek.mapper.DeviceMapper;
import com.yiguan.firstweek.model.Device;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceMapper deviceMapper;

    public DeviceController(DeviceMapper deviceMapper) {
        this.deviceMapper = deviceMapper;
    }

    @PostMapping("/add")
    public Device addDevice(@RequestBody Device device) {
        deviceMapper.insert(device);
        return device;
    }

    @GetMapping("/{id}")
    public Device getDeviceById(@PathVariable Long id) {
        return deviceMapper.selectById(id);
    }
}