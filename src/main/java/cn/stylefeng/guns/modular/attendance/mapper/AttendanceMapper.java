package cn.stylefeng.guns.modular.attendance.mapper;

import cn.stylefeng.guns.modular.attendance.entity.AttendanceRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 考勤内容 Mapper 接口
 * </p>
 *
 * @author liuyang
 * @since 2019-03-25
 */
public interface AttendanceMapper {

    List<AttendanceRecord> selectMyAttendance(@Param("userId") Long userId, @Param("selectMonth") String selectMonth);

    Long selectAttendanceType(@Param("userId") Long userId);


}
