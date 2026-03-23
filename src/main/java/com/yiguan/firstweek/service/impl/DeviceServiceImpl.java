package com.yiguan.firstweek.service.impl;

import com.yiguan.firstweek.dto.DeviceAddDTO;
import com.yiguan.firstweek.exception.BusinessException;
import com.yiguan.firstweek.mapper.DeviceMapper;
import com.yiguan.firstweek.model.Device;
import com.yiguan.firstweek.service.DeviceService;
import com.yiguan.firstweek.vo.DeviceVO;
import org.springframework.stereotype.Service;

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
        device.setLastCheckTime(LocalDateTime.now());

        deviceMapper.insert(device);

        return toVO(device);
    }

    @Override
    public DeviceVO getDeviceById(Long id) {
        Device device = deviceMapper.selectById(id);
        if (device == null) {
            throw new BusinessException("该设备不存在");
        }
        return toVO(device);
    }

    private DeviceVO toVO(Device device) {
        DeviceVO deviceVO = new DeviceVO();
        deviceVO.setId(device.getId());
        deviceVO.setDeviceName(device.getDeviceName());
        deviceVO.setDeviceType(device.getDeviceType());
        deviceVO.setStatus(device.getStatus());
        deviceVO.setLastCheckTime(device.getLastCheckTime());
        return deviceVO;
    }
}