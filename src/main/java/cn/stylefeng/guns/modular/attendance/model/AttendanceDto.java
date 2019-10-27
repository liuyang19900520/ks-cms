package cn.stylefeng.guns.modular.attendance.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AttendanceDto implements Serializable {

    private Date workMonth;
    private Long employeeId;
    private String employeeNameCN;
    private String companyName;
    private String customerSiteName;
    private String projectName;
    private Float workTime;
    private Integer standardMinTime;
    private Integer standardMaxTime;

}
