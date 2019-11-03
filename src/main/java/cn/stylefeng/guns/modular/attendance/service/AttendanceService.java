package cn.stylefeng.guns.modular.attendance.service;

import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.LDateUtils;
import cn.stylefeng.guns.modular.attendance.entity.AttendanceAllRecord;
import cn.stylefeng.guns.modular.attendance.entity.AttendanceRecord;
import cn.stylefeng.guns.modular.attendance.entity.AttendanceType;
import cn.stylefeng.guns.modular.attendance.entity.ViewAttendance;
import cn.stylefeng.guns.modular.attendance.mapper.AttendanceMapper;
import cn.stylefeng.guns.modular.attendance.mapper.AttendanceTypeMapper;
import cn.stylefeng.guns.modular.attendance.model.AttendanceRecordDto;
import cn.stylefeng.guns.modular.system.entity.Employee;
import cn.stylefeng.guns.modular.system.mapper.EmployeeMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class AttendanceService {

    @Resource
    AttendanceMapper attendanceMapper;

    @Resource
    AttendanceTypeMapper attendanceTypeMapper;

    @Resource
    EmployeeMapper employeeMapper;


    public List<ViewAttendance> listMyAttendanceByMonth(Long userId) {
        Employee employee = employeeMapper.selectEmployeeByUserId(userId);
        Long employeeId = employee.getEmployeeId();

        //将这个employeeId传入查询视图的mapper中
        List<ViewAttendance> viewAttendances = attendanceMapper.selectMyAttendanceByMonth(employeeId);

        return viewAttendances;

    }

    public ViewAttendance getCustomerSiteInfo() {
        ShiroUser currentUser = ShiroKit.getUser();
        Employee employee = employeeMapper.selectEmployeeByUserId(currentUser.getId());
        Long employeeId = employee.getEmployeeId();
        Map<String, Object> mapResult = attendanceMapper.selectCustomerSiteInfoForAddForm(employeeId);
        ViewAttendance customerSiteInfo=new ViewAttendance();

        customerSiteInfo.setCompanyName((String) mapResult.get("COMPANY_NAME"));
        customerSiteInfo.setProjectName((String) mapResult.get("PROJECT_NAME"));
        customerSiteInfo.setCustomerSiteName((String) mapResult.get("CUSTOMER_SITE_NAME"));
        customerSiteInfo.setEmployeeId((Long) mapResult.get("EMPLOYEE_ID"));
        customerSiteInfo.setEmployeeNameCN(currentUser.getName());
        return customerSiteInfo;
    }


    public List<AttendanceRecordDto> listMyAttendance(Long userId, String currentMonth) {

        List<AttendanceRecordDto> result = Lists.newArrayList();
        List<AttendanceRecord> resultDB = attendanceMapper.selectMyAttendance(userId, currentMonth);

        Integer typeId = attendanceMapper.selectAttendanceType(userId);
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
            oneDay.setId(LDateUtils.dateToString(oneDay.getWorkDate(), "yyyyMMdd") + userId);

            result.add(oneDay);
        }


        return result;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean recordAttendance(ArrayList<AttendanceRecordDto> records) {

        Long userId = ShiroKit.getUser().getId();
        Integer typeId = attendanceMapper.selectAttendanceType(userId);

        for (AttendanceRecordDto input : records) {
            AttendanceRecord oneRecord = new AttendanceRecord();
            oneRecord.setUserId(userId);
            oneRecord.setAttendanceType(typeId);

            String id = input.getId();
            String strDate = id.substring(0, 8);
            oneRecord.setWorkDate(LDateUtils.stringToDate(strDate, "yyyyMMdd"));
            oneRecord.setStartTime(input.getStartTime());
            oneRecord.setEndTime(input.getEndTime());

            if (!ToolUtil.isOneEmpty(oneRecord.getStartTime(), oneRecord.getEndTime())) {
                attendanceMapper.insertAttendanceList(oneRecord);
            }
        }
        return false;

    }

    @Transactional(rollbackFor = Exception.class)
    public List<AttendanceAllRecord> selectAllUser(String currentMonth) {

        List<AttendanceAllRecord> allUsers = attendanceMapper.selectUsers();

        for (AttendanceAllRecord user : allUsers) {
            List<AttendanceRecord> resultDB = attendanceMapper.selectMyAttendance(user.getUserId(), currentMonth);
            AttendanceType attendanceType = attendanceTypeMapper.selectById(user.getAttendanceType());
            float hoursInMonth = 0;
            for (AttendanceRecord oneDay : resultDB) {
                float workPeriod = computeDayPeriod(oneDay.getStartTime(), oneDay.getEndTime(), attendanceType);
                hoursInMonth = hoursInMonth + workPeriod;
            }
            user.setHours(hoursInMonth);

        }
        return allUsers;

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


        return Float.parseFloat(p);
    }
    
//添加考勤
	public void addAttendancer(ViewAttendance attendance) {
		Long userId = ShiroKit.getUser().getId();
		attendance.setUserId(userId);
		attendanceMapper.insertAttendance(attendance);
		
	}

}
