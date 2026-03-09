package com.yiguan.firstweek.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Device {
    private Long deviceId;
    private String deviceName;
    private String deviceType;     //投影仪
    private Integer status;     //0 = 正常； 1 = 故障 ；Integer是Int的包装类型，对象类型；可以是null
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastCheckTime;    //最终要“返回标准 JSON”，时间字段如果用 LocalDateTime，
                                            // 框架可以更自然地处理它（序列化时有标准格式），你也更容易在后续扩展
                                            //结果的时间戳，这个状态是在什么时候更新的
                                            //最近一次的检查时间

    public Device(){}
    public Long getDeviceId(){
        return deviceId;
    }
    public void setDeviceId(Long deviceId){
        this.deviceId = deviceId;
    }
    public String getDeviceName(){
        return deviceName;
    }
    public void setDeviceName(String deviceName){
        this.deviceName = deviceName;
    }
    public String getDeviceType(){
        return deviceType;
    }
    public void setDeviceType(String deviceType){
        this.deviceType = deviceType;
    }
    public Integer getStatus(){
        return status;
    }
    public void setStatus(Integer status){
        this.status = status;
    }
    public LocalDateTime getLastCheckTime(){
        return lastCheckTime;
    }
    public void setLastCheckTime(LocalDateTime lastCheckTime){
        this.lastCheckTime = lastCheckTime;
    }
}
