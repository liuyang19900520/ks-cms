package cn.stylefeng.guns.modular.employee.mapper;

import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Repository
public interface EmployeeMapper {


    /**
     * 根据条件查询用户列表
     */
    Page<Map<String, Object>> selectUsers(@Param("page") Page page,
                                          @Param("dataScope") DataScope dataScope,
                                          @Param("chineseName") String name);

}
