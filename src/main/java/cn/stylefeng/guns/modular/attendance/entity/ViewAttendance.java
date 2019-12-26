package cn.stylefeng.guns.modular.attendance.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class ViewAttendance implements Serializable {

    @JSONField(format = "yyyyMM")
    private Date workMonth;
    private Long employeeId;
    private Long userId;
    private String userName;
    private String employeeNameCN;
    private String companyName;
    private String customerSiteName;
    private String projectName;
    private Float workTime;
    private Integer standardMinTime;
    private Integer standardMaxTime;
    private String status;
    private Integer hours;


}
