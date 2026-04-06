package com.yiguan.firstweek.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yiguan.firstweek.model.User;
import org.apache.ibatis.annotations.Mapper;

//操作user表的数据库访问层
@Mapper
public interface UserMapper extends BaseMapper<User> {
}