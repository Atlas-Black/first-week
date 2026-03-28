package com.yiguan.firstweek.service;

import com.yiguan.firstweek.dto.DeviceAddDTO;
import com.yiguan.firstweek.vo.DeviceVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiguan.firstweek.vo.DeviceVO;

//业务逻辑
//接收DTO、组装实体类、调Mapper、转成VO返回

public interface DeviceService {
    DeviceVO addDevice(DeviceAddDTO deviceAddDTO);
    DeviceVO getDeviceById(Long id);
    Page<DeviceVO> pageDevice(long page, long size);
}