package com.yiguan.firstweek.mapper;

//操作数据库，数据库访问层，这是专门操作 device 表的接口
//它继承了 MyBatis-Plus 的通用 CRUD 能力
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiguan.firstweek.model.Device;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

}
