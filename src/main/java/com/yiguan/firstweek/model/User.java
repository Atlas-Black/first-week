package com.yiguan.firstweek.model;

//用户表的实体类，将数据库里的user表映射成Java对象
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {

    //id主键，自增
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;
}