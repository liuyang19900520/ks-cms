package cn.stylefeng.guns.modular.attendance.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AttendanceAllRecord implements Serializable {

    private Long userId;
    private String userName;
    private Integer attendanceType;
    private Float hours;
    private String customerSiteId;
    private String customerSiteName;
    private String customerSiteAddress;
    private Integer maxHours;
    private Integer minHours;
    private Integer version;
    private Date createTime;
    private Date updateTime;
    private Long createUser;
    private Long updateUser;


}
