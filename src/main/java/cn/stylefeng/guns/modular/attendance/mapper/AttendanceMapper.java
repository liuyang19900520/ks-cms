package cn.stylefeng.guns.modular.attendance.mapper;

import cn.stylefeng.guns.modular.attendance.entity.AttendanceAllRecord;
import cn.stylefeng.guns.modular.attendance.entity.AttendanceRecord;
import cn.stylefeng.guns.modular.attendance.entity.ViewAttendance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    List<ViewAttendance> selectMyAttendanceByMonth(@Param("employeeId") Long employeeId);


}
