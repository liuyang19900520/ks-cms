package cn.stylefeng.guns.modular.attendance.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AttendanceRecordDto implements Serializable {

    private Long userId;
    private String userName;
    private Date workDate;
    private Long startTime;
    private Long endTime;
    private Integer hours;
    private String selectMonth;


}
