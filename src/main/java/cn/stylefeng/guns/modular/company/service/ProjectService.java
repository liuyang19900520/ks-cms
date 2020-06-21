package cn.stylefeng.guns.modular.company.service;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.company.entity.Project;
import cn.stylefeng.guns.modular.company.mapper.ProjectMapper;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class ProjectService extends ServiceImpl<ProjectMapper, Project> {

    @Resource
    private ProjectMapper projectMapper;

    /**
     * 获取所有项目信息列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:16 PM
     */
    public Page<Map<String, Object>> list(DataScope dataScope, String projectName) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.list(page, dataScope, projectName);
    }

    /**
     * 项目信息录入
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:00 PM
     */
    @Transactional(rollbackFor = Exception.class)
    public void addProject(Project project) {

        if (ToolUtil.isOneEmpty(project)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Long pId = projectMapper.getProjectId();
        if (pId == null) {
            pId = 1L;
        }
        project.setProjectId(pId);

        this.save(project);
    }

    /**
     * 修改部门
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:00 PM
     */
    @Transactional(rollbackFor = Exception.class)
    public void editProject(Project project) {

        if (ToolUtil.isOneEmpty(project, project.getProjectId(), project.getProjectName(),project.getProjectStart(),project.getProjectEnd(), project.getProjectProcess()
                , project.getProjectTech())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        this.updateById(project);
    }


    public List<Project> selectProjects(Long customerSiteId) {
        QueryWrapper query = new QueryWrapper<Project>();
        query.eq("CUSTOMER_SITE_ID", customerSiteId);
        return this.list(query);

    }

}
