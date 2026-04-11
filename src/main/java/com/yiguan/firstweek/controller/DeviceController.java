package com.yiguan.firstweek.controller;
//专门接收HTTP请求
import com.yiguan.firstweek.common.Result;
import com.yiguan.firstweek.dto.DeviceAddDTO;
import com.yiguan.firstweek.dto.DeviceUpdateDTO;
import com.yiguan.firstweek.model.Device;
import com.yiguan.firstweek.service.DeviceService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import com.yiguan.firstweek.vo.DeviceVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.excel.EasyExcel;
import com.yiguan.firstweek.vo.DeviceExportVO;
import com.alibaba.excel.EasyExcel;
import com.yiguan.firstweek.dto.DeviceImportDTO;
import com.yiguan.firstweek.listener.DeviceImportListener;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    @GetMapping("/export")
    public void exportDevice(HttpServletResponse response) throws Exception {
        //查数据，先把要导出的设备数据准备好
        List<DeviceExportVO> deviceList = deviceService.exportDeviceList();

        //设置响应头。告诉浏览器这是一个Excel文件，请下载
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        String fileName = URLEncoder.encode("设备清单", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        //EasyExcel直接写到输出流。把Java列表直接写成Excel文件流输出给浏览器
        EasyExcel.write(response.getOutputStream(), DeviceExportVO.class)
                .sheet("设备列表")
                .doWrite(deviceList);
    }

    @PostMapping("/import")
    //接收上传文件
    public Result<Void> importDevice(@RequestParam("file") MultipartFile file) throws Exception {
        //读取上传的 Excel 输入流
        //每一行按 DeviceImportDTO 去映射
        //每读到一行，就交给 DeviceImportListener 处理
        EasyExcel.read(file.getInputStream(), DeviceImportDTO.class, new DeviceImportListener(deviceService))
                .sheet()
                .doRead();

        return Result.success(null);
    }
}