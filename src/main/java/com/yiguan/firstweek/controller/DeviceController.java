package com.yiguan.firstweek.controller;
//专门接收HTTP请求
import com.yiguan.firstweek.common.Result;
import com.yiguan.firstweek.dto.DeviceAddDTO;
import com.yiguan.firstweek.model.Device;
import com.yiguan.firstweek.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.yiguan.firstweek.vo.DeviceVO;

//接收请求、获取参数、调用DeviceService、返回结果

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
    //@RestController
    //@RequestMapping("/device")
    //public class DeviceController {

        //@Autowired
        //private DeviceService deviceService;
    //}

    //@RestController
    //@RequestMapping("/device")
    //public class DeviceController {

        //@Resource
        //private DeviceService deviceService;
    //}
    @PostMapping("/add")
    public Result<DeviceVO> addDevice(@RequestBody @Valid DeviceAddDTO deviceAddDTO) { //如果参数不合法，方法体里的代码根本不会执行，直接抛校验异常。
        return Result.success(deviceService.addDevice(deviceAddDTO));
    }

    @GetMapping("/{id}")
    public Result<DeviceVO> getDeviceById(@PathVariable Long id) {
        return Result.success(deviceService.getDeviceById(id));
    }
}