package com.yiguan.firstweek.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

//输出

@Data
public class DeviceVO {
    private Long id;
    private String deviceName;
    private String deviceType;
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastCheckTime;
}