package cn.stylefeng.guns.modular.system.service;


import java.util.Map;

import javax.annotation.Resource;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.modular.system.entity.Company;
import cn.stylefeng.guns.modular.system.entity.CustomerSite;
import cn.stylefeng.guns.modular.system.mapper.CustomerSiteMapper;
import cn.stylefeng.guns.modular.system.model.CustomerSiteDto;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.springframework.stereotype.Service;
;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class CustomerSiteService extends ServiceImpl<CustomerSiteMapper, CustomerSite> {

    @Resource
    private CustomerSiteMapper customerSiteMapper;

    /**
     * 获取所有现场信息列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:16 PM
     */
    public Page<Map<String, Object>> list(DataScope dataScope,String customersiteName) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.list(page, dataScope, customersiteName);

    }

    /**
     * 客户信息录入
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:00 PM
     */
    @Transactional(rollbackFor = Exception.class)
    public void addCustomerSite(CustomerSite customer) {

        if (ToolUtil.isOneEmpty(customer)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.save(customer);
    }
}
