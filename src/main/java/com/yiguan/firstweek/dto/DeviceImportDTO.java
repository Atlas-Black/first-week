package com.yiguan.firstweek.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DeviceImportDTO {

    @ExcelProperty("设备名称")
    private String deviceName;

    @ExcelProperty("设备类型")
    private String deviceType;

    @ExcelProperty("设备状态")
    private Integer status;
}
