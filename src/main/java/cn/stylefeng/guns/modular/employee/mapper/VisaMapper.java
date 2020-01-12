package cn.stylefeng.guns.modular.employee.mapper;

import cn.stylefeng.guns.modular.employee.entity.Visa;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-29
 */
@Repository
public interface VisaMapper extends BaseMapper<Visa> {
    Page<Map<String, Object>> list(@Param("page") Page page, @Param("employeeNameCn") String employeeNameCn);

    List<Map<String, Object>> getUnregister();

    int insertVisa(Visa visa);

}
