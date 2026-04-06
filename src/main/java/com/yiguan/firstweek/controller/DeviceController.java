package com.yiguan.firstweek.controller;
//专门接收HTTP请求
import com.yiguan.firstweek.common.Result;
import com.yiguan.firstweek.dto.DeviceAddDTO;
import com.yiguan.firstweek.dto.DeviceUpdateDTO;
import com.yiguan.firstweek.model.Device;
import com.yiguan.firstweek.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.yiguan.firstweek.vo.DeviceVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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

    //接收page和size，然后由Service查询，再包装成Result.success，提供企业里最常见的列表查询接口
    @GetMapping("/page")
    public Result<Page<DeviceVO>> pageDevice(@RequestParam long page,
                                             @RequestParam long size) {
        return Result.success(deviceService.pageDevice(page, size));
    }

    @PutMapping("/update")
    public Result<Void> updateDevice(@RequestBody DeviceUpdateDTO deviceUpdateDTO){
        deviceService.updateDevice(deviceUpdateDTO);
        return Result.success(null);
    }

    @PostMapping("/borrow/{id}")
    public Result<Void> borrowDevice(@PathVariable Long id){
        deviceService.borrowDevice(id);
        return Result.success(null);
    }
}