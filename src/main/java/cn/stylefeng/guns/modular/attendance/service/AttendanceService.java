package cn.stylefeng.guns.modular.attendance.service;

import cn.stylefeng.guns.modular.attendance.entity.AttendanceRecord;
import cn.stylefeng.guns.modular.attendance.mapper.AttendanceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AttendanceService {

    @Resource
    AttendanceMapper attendanceMapper;

    public List<AttendanceRecord> listMyAttendance(Long userId, String currentMonth) {
        return attendanceMapper.selectMyAttendance(userId, currentMonth);
    }


}
