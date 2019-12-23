package cn.stylefeng.guns.modular.company.mapper;

import cn.stylefeng.guns.modular.company.entity.Project;
import cn.stylefeng.roses.core.datascope.DataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-29
 */
public interface ProjectMapper extends BaseMapper<Project> {
    /**
     * 获取所有部门列表
     */
	Page<Map<String, Object>> list(@Param("page") Page page, @Param("dataScope") DataScope dataScope, @Param("projectName") String projectName);

    Integer updateByProjectId(@Param("projectId") Long projectId,@Param("projectName") String projectName, @Param("projectProcess")String projectProcess, @Param("projectTech") String projectTech);

    Long getProjectId();
}
