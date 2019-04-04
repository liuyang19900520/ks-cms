package cn.stylefeng.guns.modular.attendance.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("attendance_type")
public class AttendanceType implements Serializable {

    @TableId(value = "ATTENDANCE_TYPE", type = IdType.AUTO)
    private Integer attendanceType;
    private Integer standardMinTime;
    private Integer standardMaxTime;
    @JSONField(format = "HH:mm:ss")
    private Date noonStart;
    @JSONField(format = "HH:mm:ss")
    private Date noonEnd;
    @JSONField(format = "HH:mm:ss")
    private Date eveningStart;
    @JSONField(format = "HH:mm:ss")
    private Date eveningEnd;
    private Integer version;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;


}
