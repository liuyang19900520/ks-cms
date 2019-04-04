package cn.stylefeng.guns.modular.attendance.service;

import cn.stylefeng.guns.modular.attendance.entity.AttendanceRecord;
import cn.stylefeng.guns.modular.attendance.entity.AttendanceType;
import cn.stylefeng.guns.modular.attendance.mapper.AttendanceMapper;
import cn.stylefeng.guns.modular.attendance.mapper.AttendanceTypeMapper;
import cn.stylefeng.guns.modular.attendance.model.AttendanceRecordDto;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceService {

    @Resource
    AttendanceMapper attendanceMapper;

    @Resource
    AttendanceTypeMapper attendanceTypeMapper;

    public List<AttendanceRecordDto> listMyAttendance(Long userId, String currentMonth) {

        List<AttendanceRecordDto> result = Lists.newArrayList();
        List<AttendanceRecord> resultDB = attendanceMapper.selectMyAttendance(userId, currentMonth);

        Long typeId = attendanceMapper.selectAttendanceType(userId);
        AttendanceType attendanceType = attendanceTypeMapper.selectById(typeId);

        String year = currentMonth.substring(0, 4);
        String month = currentMonth.substring(4, 6);
        Calendar cal = Calendar.getInstance();
        Calendar calDB = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        int maxDate = cal.getActualMaximum(Calendar.DATE);

        for (int i = 1; i <= maxDate; i++) {
            AttendanceRecordDto oneDay = new AttendanceRecordDto();
            oneDay.setUserId(userId);
            cal.set(Calendar.DATE, i);
            oneDay.setWorkDate(cal.getTime());
            for (int j = 0; j < resultDB.size(); j++) {
                calDB.setTime(resultDB.get(j).getWorkDate());
                if (calDB.get(Calendar.DATE) == cal.get(Calendar.DATE)) {
                    oneDay.setStartTime(resultDB.get(j).getStartTime());
                    oneDay.setEndTime(resultDB.get(j).getEndTime());
                    float workPeriod = computeDayPeriod(resultDB.get(j).getStartTime(), resultDB.get(j).getEndTime(), attendanceType);
                    oneDay.setDayPeriod(workPeriod);
                    break;
                }
            }

            result.add(oneDay);
        }


        return result;
    }


    private float computeDayPeriod(Date startTime, Date endTime, AttendanceType attendanceType) {

        Long period = null;

        Long noonRest = attendanceType.getNoonEnd().getTime() - attendanceType.getNoonStart().getTime();
        Long eveningRest = attendanceType.getEveningEnd().getTime() - attendanceType.getEveningStart().getTime();

        //中午吃饭前到
        if (startTime.getTime() <= (attendanceType.getNoonStart().getTime())) {
            if (endTime.getTime() <= (attendanceType.getNoonStart().getTime())) {
                period = endTime.getTime() - startTime.getTime();
            } else if (endTime.getTime() >= (attendanceType.getNoonStart().getTime()) && endTime.getTime() <= (attendanceType.getNoonEnd().getTime())) {
                period = attendanceType.getNoonStart().getTime() - startTime.getTime();
            } else if (endTime.getTime() >= (attendanceType.getNoonEnd().getTime()) && endTime.getTime() <= (attendanceType.getEveningStart().getTime())) {
                period = endTime.getTime() - startTime.getTime() - noonRest;
            } else if (endTime.getTime() >= (attendanceType.getEveningStart().getTime()) && endTime.getTime() <= (attendanceType.getEveningEnd().getTime())) {
                period = attendanceType.getEveningStart().getTime() - startTime.getTime() - noonRest;
            } else if (endTime.getTime() >= (attendanceType.getEveningEnd().getTime())) {
                period = endTime.getTime() - startTime.getTime()
                        - noonRest - eveningRest;
            }
        }
        //午休到
        if (startTime.getTime() >= (attendanceType.getNoonStart().getTime()) && startTime.getTime() <= (attendanceType.getNoonEnd().getTime())) {
            startTime = attendanceType.getNoonEnd();
            if (endTime.getTime() >= (attendanceType.getNoonStart().getTime()) && endTime.getTime() <= (attendanceType.getNoonEnd().getTime())) {
                period = 0L;
            } else if (endTime.getTime() >= (attendanceType.getNoonEnd().getTime()) && endTime.getTime() <= (attendanceType.getEveningStart().getTime())) {
                period = endTime.getTime() - startTime.getTime();
            } else if (endTime.getTime() >= (attendanceType.getEveningStart().getTime()) && endTime.getTime() <= (attendanceType.getEveningEnd().getTime())) {
                period = attendanceType.getEveningStart().getTime() - startTime.getTime();
            } else if (endTime.getTime() >= (attendanceType.getEveningEnd().getTime())) {
                period = endTime.getTime() - startTime.getTime() - eveningRest;
            }
        }
        //下午到
        if (startTime.getTime() >= (attendanceType.getNoonEnd().getTime()) && startTime.getTime() <= (attendanceType.getEveningStart().getTime())) {
            if (endTime.getTime() >= (attendanceType.getNoonEnd().getTime()) && endTime.getTime() <= (attendanceType.getEveningStart().getTime())) {
                period = endTime.getTime() - startTime.getTime();
            } else if (endTime.getTime() >= (attendanceType.getEveningStart().getTime()) && endTime.getTime() <= (attendanceType.getEveningEnd().getTime())) {
                period = attendanceType.getEveningStart().getTime() - startTime.getTime();
            } else if (endTime.getTime() >= (attendanceType.getEveningEnd().getTime())) {
                period = endTime.getTime() - startTime.getTime() - eveningRest;
            }
        }

        //晚间休息到
        if (startTime.getTime() >= (attendanceType.getEveningStart().getTime()) && startTime.getTime() <= (attendanceType.getEveningEnd().getTime())) {

            startTime = attendanceType.getEveningEnd();
            if (endTime.getTime() >= (attendanceType.getEveningStart().getTime()) && endTime.getTime() <= (attendanceType.getEveningEnd().getTime())) {
                period = 0L;
            } else if (endTime.getTime() >= (attendanceType.getEveningEnd().getTime())) {
                period = endTime.getTime() - startTime.getTime();
            }
        }


        //晚间最后到
        if (startTime.getTime() >= (attendanceType.getEveningEnd().getTime())) {

            if (endTime.getTime() >= (attendanceType.getEveningEnd().getTime())) {
                period = endTime.getTime() - startTime.getTime();
            }
        }

        float x = (float) period / (float) 1000 / (float) 60 / (float) 60;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(x);//format 返回的是字符串

        ;

        return Float.parseFloat(p);
    }

}
