package cn.stylefeng.guns.modular.system.mapper;

import cn.stylefeng.guns.modular.system.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

    Integer insertEmpoyee(Employee e);

    Employee selectEmployeeByUserId(@Param("userId") Long id);

}
