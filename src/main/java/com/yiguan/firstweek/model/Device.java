package com.yiguan.firstweek.model;
//数据库实体类，和数据库做映射
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("device")    //类对应数据库的device表
public class Device {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String deviceName;
    private String deviceType;     //投影仪
    private Integer status;     //0 = 正常； 1 = 故障 ；Integer是Int的包装类型，对象类型；可以是null

    //时间自动填充
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastCheckTime;    //最终要“返回标准 JSON”，时间字段如果用 LocalDateTime，
    // 框架可以更自然地处理它（序列化时有标准格式），你也更容易在后续扩展
    //结果的时间戳，这个状态是在什么时候更新的
    //最近一次的检查时间

    //创建人自动填充
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
}
