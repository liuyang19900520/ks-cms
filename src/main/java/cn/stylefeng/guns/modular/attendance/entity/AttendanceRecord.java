package cn.stylefeng.guns.modular.attendance.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AttendanceRecord implements Serializable {

    private Long userId;
    private Long employeeId;
    private Integer attendanceType;
    private String userName;
    private Date workDate;
    private Date startTime;
    private Date endTime;
    private Integer hours;
    private Date dayPeriod;
    private Integer version;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;
    private String status;


}
