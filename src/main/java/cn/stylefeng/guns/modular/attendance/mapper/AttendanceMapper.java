package cn.stylefeng.guns.modular.attendance.mapper;

import cn.stylefeng.guns.modular.attendance.entity.AttendanceAllRecord;
import cn.stylefeng.guns.modular.attendance.entity.AttendanceRecord;
import cn.stylefeng.guns.modular.attendance.entity.ViewAttendance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤内容 Mapper 接口
 * </p>
 *
 * @author liuyang
 * @since 2019-03-25
 */
@Repository
public interface AttendanceMapper {

    List<AttendanceRecord> selectMyAttendance(@Param("userId") Long userId, @Param("selectMonth") String selectMonth);

    Integer selectAttendanceType(@Param("userId") Long userId);

    Integer insertAttendanceList(AttendanceRecord record);

    List<AttendanceAllRecord> selectUsers();

    List<ViewAttendance> selectMyAttendanceByMonth(@Param("currentMonth") Date currentMonth, @Param("employeeId") Long employeeId);

    Map<String, Object> selectCustomerSiteInfoForAddForm(@Param("employeeId") Long employeeId);

    Integer insertAttendance(ViewAttendance attendance);

    Map<String, Object> selectMonthEmployeeID(@Param("employeeId") Long employeeId, @Param("workMonth") Date workMonth);

    Integer updateMonthEmployeeID(@Param("workMonth") Date workMonth, @Param("workTime") Float workTime, @Param("employeeId") Long employeeId);

    Integer deleteMonthEmployeeID(@Param("workMonth") Date workMonth, @Param("employeeId") Long employeeId);

    List<AttendanceAllRecord> selectAllMyAttendance(@Param("currentMonth") Date currentMonth, @Param("empId") Long empId, @Param("status") Integer status);

}
