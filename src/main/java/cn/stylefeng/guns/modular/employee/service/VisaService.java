package cn.stylefeng.guns.modular.employee.service;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.employee.entity.Visa;
import cn.stylefeng.guns.modular.employee.mapper.InfoMgrMapper;
import cn.stylefeng.guns.modular.employee.mapper.VisaMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class VisaService extends ServiceImpl<VisaMapper, Visa> {


    @Autowired
    private VisaMapper visaMapper;

    /**
     * 获取员工信息列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:16 PM
     */
    public Page<Map<String, Object>> list(String employeeNameCn) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.list(page, employeeNameCn);
    }

    //添加签证信息
    public void addVisa(Visa visa) {

        String[] split = visa.getEmployeeSelectValue().split(",");
        visa.setUserId(Long.parseLong(split[0]));
        visa.setEmployeeId(Long.parseLong(split[1]));

        visaMapper.insertVisa(visa);
    }


    /**
     * 未注册人员信息
     *
     * @return 未注册人员信息
     */
    public List<Map<String, Object>> getUnregister() {

        return visaMapper.getUnregister();

    }
}
