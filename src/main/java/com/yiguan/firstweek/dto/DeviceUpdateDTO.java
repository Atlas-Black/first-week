package com.yiguan.firstweek.dto;

import lombok.Data;

@Data
public class DeviceUpdateDTO {
    private Long id;
    private String deviceName;
    private String deviceType;
    private Integer status;
}