package com.yiguan.firstweek.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.yiguan.firstweek.dto.DeviceImportDTO;
import com.yiguan.firstweek.model.Device;
import com.yiguan.firstweek.service.DeviceService;

public class DeviceImportListener implements ReadListener<DeviceImportDTO> {

    private final DeviceService deviceService;

    public DeviceImportListener(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void invoke(DeviceImportDTO data, AnalysisContext context) {
        Device device = new Device();
        device.setDeviceName(data.getDeviceName());
        device.setDeviceType(data.getDeviceType());
        device.setStatus(data.getStatus());

        //读取上传的 Excel 输入流
        //每一行按 DeviceImportDTO 去映射
        //每读到一行，就交给 DeviceImportListener 处理
        deviceService.saveImportedDevice(device);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("Excel 导入完成");
    }
}