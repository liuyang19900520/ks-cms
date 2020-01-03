package cn.stylefeng.guns.modular.employee.mapper;

import java.util.Map;

import cn.stylefeng.guns.modular.employee.entity.ProjectRelation;
import cn.stylefeng.guns.modular.employee.entity.Visa;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.stylefeng.guns.modular.employee.entity.InfoMgr;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-29
 */
public interface InfoMgrMapper extends BaseMapper<InfoMgr> {
	Page<Map<String, Object>> list(@Param("page") Page page, @Param("condition") String condition);

    Integer insertProjectRelation(ProjectRelation projectRelation);
    Integer insertEmployee(Visa visa);
}
