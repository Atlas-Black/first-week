package com.yiguan.firstweek.service.impl;

import com.yiguan.firstweek.dto.DeviceAddDTO;
import com.yiguan.firstweek.exception.BusinessException;
import com.yiguan.firstweek.mapper.DeviceMapper;
import com.yiguan.firstweek.model.Device;
import com.yiguan.firstweek.service.DeviceService;
import com.yiguan.firstweek.vo.DeviceVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.time.LocalDateTime;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceMapper deviceMapper;

    public DeviceServiceImpl(DeviceMapper deviceMapper) {
        this.deviceMapper = deviceMapper;
    }

    @Override
    public DeviceVO addDevice(DeviceAddDTO deviceAddDTO) {
        Device device = new Device();
        device.setDeviceName(deviceAddDTO.getDeviceName());
        device.setDeviceType(deviceAddDTO.getDeviceType());
        device.setStatus(deviceAddDTO.getStatus());

        //把数据写进数据库
        deviceMapper.insert(device);

        return toVO(device);
    }

    @Override
    public DeviceVO getDeviceById(Long id) {
        Device device = deviceMapper.selectById(id);    //查数据库
        if (device == null) {
            throw new BusinessException("该设备不存在");
        }
        //Entity转VO，实体库对象转成前端展示对象
        return toVO(device);
    }

    @Override
    public Page<DeviceVO> pageDevice(long page, long size) {
        Page<Device> devicePage = new Page<>(page, size);
        Page<Device> resultPage = deviceMapper.selectPage(devicePage, null);

        Page<DeviceVO> voPage = new Page<>();
        voPage.setCurrent(resultPage.getCurrent());
        voPage.setSize(resultPage.getSize());
        voPage.setTotal(resultPage.getTotal());
        voPage.setPages(resultPage.getPages());
        voPage.setRecords(
                resultPage.getRecords().stream().map(this::toVO).toList()
        );

        return voPage;
    }

    //把前端传进来的参数对象转换为数据库实体对象
    private DeviceVO toVO(Device device) {
        DeviceVO deviceVO = new DeviceVO();
        deviceVO.setId(device.getId());
        deviceVO.setDeviceName(device.getDeviceName());
        deviceVO.setDeviceType(device.getDeviceType());
        deviceVO.setStatus(device.getStatus());
        deviceVO.setLastCheckTime(device.getLastCheckTime());
        deviceVO.setCreateBy(device.getCreateBy());
        return deviceVO;
    }
}