package cn.stylefeng.guns.modular.company.service;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.company.entity.Company;
import cn.stylefeng.guns.modular.company.entity.CustomerSite;
import cn.stylefeng.guns.modular.company.mapper.CompanyMapper;
import cn.stylefeng.guns.modular.company.mapper.CustomerSiteMapper;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class CompanyService extends ServiceImpl<CompanyMapper, Company> {

    @Resource
    private CompanyMapper companyMapper;

    /**
     * 获取所有客户信息列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:16 PM
     */
    public Page<Map<String, Object>> list(String condition) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.list(page, condition);
    }

    /**
     * 客户信息录入
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:00 PM
     */
    @Transactional(rollbackFor = Exception.class)
    public void addCompany(Company company) {

        if (ToolUtil.isOneEmpty(company)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.save(company);
    }

    /**
     * 修改部门
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:00 PM
     */
    @Transactional(rollbackFor = Exception.class)
    public void editCompany(Company company) {

        if (ToolUtil.isOneEmpty(company, company.getCustomerID())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.updateById(company);
    }

    /**
     * <p>
     * 部门表 服务实现类
     * </p>
     *
     * @author stylefeng
     * @since 2018-12-07
     */
    @Service
    public static class CustomerSiteService extends ServiceImpl<CustomerSiteMapper, CustomerSite> {

        @Resource
        private CustomerSiteMapper customerSiteMapper;

        /**
         * 获取所有现场信息列表
         *
         * @author fengshuonan
         * @Date 2018/12/23 5:16 PM
         */
        public Page<Map<String, Object>> list(DataScope dataScope, String customersiteName) {
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

            customer.setCustomerSiteID(customerSiteMapper.getMaxCustomerSiteId());

            if (ToolUtil.isOneEmpty(customer)) {
                throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
            }
            this.save(customer);
        }
    }
}
