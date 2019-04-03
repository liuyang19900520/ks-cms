package cn.stylefeng.guns.modular.attendance.service;

import cn.stylefeng.guns.modular.attendance.entity.AttendanceRecord;
import cn.stylefeng.guns.modular.attendance.mapper.AttendanceMapper;
import cn.stylefeng.guns.modular.attendance.model.AttendanceRecordDto;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@Service
public class AttendanceService {

    @Resource
    AttendanceMapper attendanceMapper;

    public List<AttendanceRecordDto> listMyAttendance(Long userId, String currentMonth) {

        List<AttendanceRecordDto> result = Lists.newArrayList();
        List<AttendanceRecord> resultDB = attendanceMapper.selectMyAttendance(userId, currentMonth);


        String year = currentMonth.substring(0, 4);
        String month = currentMonth.substring(4, 6);
        Calendar cal = Calendar.getInstance();
        Calendar calDB = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);//7月
        int maxDate = cal.getActualMaximum(Calendar.DATE);

        for (int i = 1; i <= maxDate; i++) {

            AttendanceRecordDto oneDay = new AttendanceRecordDto();
            oneDay.setUserId(userId);
            cal.set(Calendar.DATE, i);
            oneDay.setWorkDate(cal.getTime());
            for (int j = 0; j < resultDB.size(); j++) {
                calDB.setTime(resultDB.get(j).getWorkDate());
                if (calDB.get(Calendar.DATE) == cal.get(Calendar.DATE)) {
                    oneDay.setWorkDate(resultDB.get(j).getWorkDate());
                    oneDay.setStartTime(resultDB.get(j).getStartTime());
                    oneDay.setEndTime(resultDB.get(j).getEndTime());
                    oneDay.setDayPeriod(resultDB.get(j).getDayPeriod());
                    break;
                }
            }

            result.add(oneDay);
        }


        return result;
    }


}
