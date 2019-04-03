package cn.stylefeng.guns.modular.attendance.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AttendanceRecord implements Serializable {

    private Long userId;
    private Integer attendanceType;
    private String userName;
    @JSONField(format = "MM-dd")
    private Date workDate;
    @JSONField(format = "HH:mm:ss")
    private Date startTime;
    @JSONField(format = "HH:mm:ss")
    private Date endTime;
    private Integer hours;
    @JSONField(format = "HH:mm:ss")
    private Date dayPeriod;
    private Integer version;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;


}
