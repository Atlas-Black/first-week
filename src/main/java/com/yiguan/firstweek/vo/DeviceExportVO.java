package com.yiguan.firstweek.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DeviceExportVO {

    @ExcelProperty("设备ID")
    private Long id;

    @ExcelProperty("设备名称")
    private String deviceName;

    @ExcelProperty("设备类型")
    private String deviceType;

    @ExcelProperty("设备状态")
    private Integer status;

    @ExcelProperty("最近检查时间")
    private String lastCheckTime;

    @ExcelProperty("创建人")
    private Long creatBy;
}
