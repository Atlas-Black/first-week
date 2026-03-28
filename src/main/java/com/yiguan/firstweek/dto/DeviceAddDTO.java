package com.yiguan.firstweek.dto;

//输入
//前端新增设备时，传入的对象
//前端需要传什么，定义什么
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeviceAddDTO {

    @NotBlank(message = "设备名不能为空")      //用于字符串，不能是null，空字符串、空格
    private String deviceName;

    private String deviceType;

    @NotNull(message = "状态不能为空")        //值不能是null
    @Min(value = 0, message = "状态只能是0或1")
    @Max(value = 1, message = "状态只能是0或1")       //0～1
    private Integer status;
}