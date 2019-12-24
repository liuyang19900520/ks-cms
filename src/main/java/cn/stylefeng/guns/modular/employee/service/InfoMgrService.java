package cn.stylefeng.guns.modular.employee.service;

import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.employee.entity.InfoMgr;
import cn.stylefeng.guns.modular.employee.entity.ProjectRelation;
import cn.stylefeng.guns.modular.employee.mapper.InfoMgrMapper;
import cn.stylefeng.guns.modular.system.mapper.EmployeeMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class InfoMgrService extends ServiceImpl<InfoMgrMapper, InfoMgr> {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private InfoMgrMapper infoMgrMapper;

    /**
     * 获取员工信息列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:16 PM
     */
    public Page<Map<String, Object>> list(String condition) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.list(page, condition);
    }


    public void addProjectRelation(ProjectRelation projectRelation) {
        infoMgrMapper.insertProjectRelation(projectRelation);
    }
}
