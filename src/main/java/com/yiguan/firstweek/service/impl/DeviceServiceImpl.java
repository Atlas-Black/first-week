package com.yiguan.firstweek.service.impl;

import com.yiguan.firstweek.dto.DeviceAddDTO;
import com.yiguan.firstweek.dto.DeviceUpdateDTO;
import com.yiguan.firstweek.exception.BusinessException;
import com.yiguan.firstweek.mapper.DeviceMapper;
import com.yiguan.firstweek.model.Device;
import com.yiguan.firstweek.service.DeviceService;
import com.yiguan.firstweek.vo.DeviceVO;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;

import java.time.LocalDateTime;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceMapper deviceMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final RedissonClient redissonClient;

    public DeviceServiceImpl(DeviceMapper deviceMapper, RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper, RedissonClient redissonClient) {
        this.deviceMapper = deviceMapper;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.redissonClient = redissonClient;
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
        String key = "device:" + id;

        //查Redis
        Object cacheObject = redisTemplate.opsForValue().get(key);
        if (cacheObject != null) {
            //情况是“NULL”之前数据库已经查过了，确定这个设备不存在，所以这次不用再查 MySQL
            //防穿透关键
            if ("NULL".equals(cacheObject)) {
                System.out.println("命中缓存，deviceId = " + id);
                throw new BusinessException("该设备不存在");
            }

            System.out.println("命中缓存，deviceId = " + id);
            System.out.println("缓存对象类型：" + cacheObject.getClass());
            //是正常设备数据，正常命中缓存，直接返回
            return objectMapper.convertValue(cacheObject, DeviceVO.class);
        }

        System.out.println("查询数据库，deviceId = " + id);
        Device device = deviceMapper.selectById(id);    //Redis没有，再查数据库MySQL
        if (device == null) {
            System.out.println("数据库无此设备，写入空值缓存，deviceId = " + id);
            //MySQL也没有时，写空值缓存但只缓存 2 分钟，避免长时间占用缓存
            redisTemplate.opsForValue().set(key, "NULL", 2, TimeUnit.MINUTES);
            throw new BusinessException("该设备不存在");
        }

        DeviceVO deviceVO = toVO(device);
        //MySQL查到正常数据时，正常写回Redis
        redisTemplate.opsForValue().set(key, deviceVO);
        //Entity转VO，实体库对象转成前端展示对象
        return deviceVO;
    }

    @Override
    //分页逻辑
    public Page<DeviceVO> pageDevice(long page, long size) {
        //告诉框架页码和每页条数
        Page<Device> devicePage = new Page<>(page, size);
        //执行分页查询
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

    @Override
    public void updateDevice(DeviceUpdateDTO deviceUpdateDTO) {
        Device device = new Device();
        device.setId(deviceUpdateDTO.getId());
        device.setDeviceName(deviceUpdateDTO.getDeviceName());
        device.setDeviceType(deviceUpdateDTO.getDeviceType());
        device.setStatus(deviceUpdateDTO.getStatus());

        deviceMapper.updateById(device);

        String key = "device:" + deviceUpdateDTO.getId();
        redisTemplate.delete(key);
    }

    @Override
    public void borrowDevice(Long id) {
        //先按设备id拿锁，同一台设备共用同一把锁
        RLock lock = redissonClient.getLock("lock:device:" + id);

        //因为后面finally里解锁时，需要先判断自己是不是真的拿到过锁，否则可能会误解锁
        boolean locked = false;
        try {
            // tryLock()：尝试获取分布式锁。
            // 如果当前锁没有被其他线程占用，就返回 true，当前线程可以继续执行业务逻辑；
            // 如果锁已经被占用，就返回 false，避免多个线程同时操作同一台设备，造成重复借出。
            //尝试加锁，尝试立即获取锁
            locked = lock.tryLock();

            if (!locked) {
                throw new BusinessException("当前设备借用人数过多，请稍后再试");
            }

            //检查设备是否存在
            Device device = deviceMapper.selectById(id);
            if (device == null) {
                throw new BusinessException("该设备不存在");
            }

            //判断设备是否已借出
            //0:正常未接触。2:已借出
            if (device.getStatus() != 0) {
                throw new BusinessException("该设备已被借出");
            }

            //更新状态已借出
            device.setStatus(2);
            deviceMapper.updateById(device);

            //删除缓存，由于设备状态已经变了，所以缓存里的旧设备信息也不能留着
            String key = "device:" + id;
            redisTemplate.delete(key);

            //无论中间代码成功还是报错，最终都必须释放锁
        } finally {
            // unlock()：释放分布式锁。
            // 必须写在 finally 块中，原因是：
            // 1. 无论业务执行成功还是中途抛异常，都要保证锁最终被释放；
            // 2. 如果不在 finally 中释放锁，可能导致锁一直被占用，后续请求无法获取锁；
            // 3. 释放前要先判断当前线程是否真的持有这把锁，避免误解锁。
            //确保真的是当前线程持有这把锁，才能安全解锁
            if (locked && lock.isHeldByCurrentThread()) {
                lock.unlock();
                //无论中间代码成功还是报错，最终都必须释放锁；如果没有finally，一旦中间异常，锁就不可能一直不释放，后续请求全卡死
            }
        }
    }
}