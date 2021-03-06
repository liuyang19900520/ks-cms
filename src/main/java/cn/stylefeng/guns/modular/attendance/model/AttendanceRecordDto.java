package cn.stylefeng.guns.modular.attendance.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
public class  AttendanceRecordDto implements Serializable {

    private String id;
    private Long userId;
    private Integer attendanceType;
    private String userName;
    @JSONField(format = "MM-dd")
    private Date workDate;
    @JSONField(format = "HH:mm")
    private Date startTime;
    @JSONField(format = "HH:mm")
    private Date endTime;
    private Integer hours;

    private float dayPeriod;

    private String week;
    private boolean restDay;


}
