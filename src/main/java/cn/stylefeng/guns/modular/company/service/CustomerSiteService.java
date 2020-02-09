package cn.stylefeng.guns.modular.company.service;

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.company.entity.CustomerSite;
import cn.stylefeng.guns.modular.company.mapper.CustomerSiteMapper;
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

@Service
public class CustomerSiteService extends ServiceImpl<CustomerSiteMapper, CustomerSite> {

    @Resource
    private CustomerSiteMapper customerSiteMapper;

    @Resource
    private ProjectService projectService;

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

    public CustomerSite getCustomerSiteByCustomerSiteID(Long siteId) {
        QueryWrapper<CustomerSite> queryWhere = new QueryWrapper<CustomerSite>();
        queryWhere.eq("customer_site_id", siteId);
        return this.list(queryWhere).get(0);
    }

    /**
     * 关联删除，根据customer_site_id将下级菜单中的数据删除；
     * @param siteId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteCustomerSiteByCustomerSiteID(Long siteId) {
        QueryWrapper<CustomerSite> queryWhere = new QueryWrapper<CustomerSite>();
        queryWhere.eq("customer_site_id", siteId);
        this.remove(queryWhere);
        //根据id删除下级菜单中的数据
        projectService.removeById(siteId);

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
        Long maxCustomerSiteId = customerSiteMapper.getMaxCustomerSiteId();
        if (maxCustomerSiteId == null) {
            maxCustomerSiteId = 1L;
        }
        customer.setCustomerSiteID(maxCustomerSiteId);
        this.save(customer);
    }

    public List<CustomerSite> listForSelect(Long customerId) {
        QueryWrapper<CustomerSite> queryWhere = new QueryWrapper<CustomerSite>();
        queryWhere.eq("customer_id", customerId);

        return this.list(queryWhere);
    }

    public void updateSite(CustomerSite customer) {
        QueryWrapper<CustomerSite> queryWhere = new QueryWrapper<CustomerSite>();
        queryWhere.eq("customer_site_id", customer.getCustomerSiteID());
        this.update(customer,queryWhere);
    }

    public List<Long> getCustomerSiteIdByCustomerId(Long customerID){
       return  customerSiteMapper.getCustomerSiteIdByCustomerId(customerID);
    }
}
