/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.company.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.constant.dictmap.CustomerSiteDict;
import cn.stylefeng.guns.core.common.constant.dictmap.ProjectDict;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.company.entity.CustomerSite;
import cn.stylefeng.guns.modular.company.entity.Project;
import cn.stylefeng.guns.modular.company.service.CompanyService;
import cn.stylefeng.guns.modular.company.service.CustomerSiteService;
import cn.stylefeng.guns.modular.company.service.ProjectService;
import cn.stylefeng.guns.modular.system.model.CustomerSiteDto;
import cn.stylefeng.guns.modular.system.warpper.CustomerSiteWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 部门控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/customer/site")
public class CustomerSiteController extends BaseController {

    private String PREFIX = "/modular/customer/customer_site/";

    @Autowired
    private CustomerSiteService customerSiteService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CompanyService companyService;


    @RequestMapping("")
    public String index() {
        return PREFIX + "customer_site.html";
    }

    /**
     * 获取客户信息列表
     *
     * @author tongyue
     * @Date 2018/12/23 6:31 PM
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String customerSiteName) {
        if (ShiroKit.isAdmin()) {
            Page<Map<String, Object>> sites = customerSiteService.list(null, customerSiteName);
            Page wrapped = new CustomerSiteWrapper(sites).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        } else {
            DataScope dataScope = new DataScope(ShiroKit.getDeptDataScope());
            Page<Map<String, Object>> sites = customerSiteService.list(dataScope, customerSiteName);
            Page wrapped = new CustomerSiteWrapper(sites).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        }
    }

    /**
     * 跳转到录入现场信息详细页
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping("/customersite_add")
    public String customersiteAdd() {
        return PREFIX + "customer_site_add.html";
    }


    /**
     * 录入现场信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "添加现场信息", key = "customerID", dict = CustomerSiteDict.class)
    @RequestMapping(value = "/add")
//    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData add(CustomerSite customer) {
        this.customerSiteService.addCustomerSite(customer);
        return SUCCESS_TIP;
    }


    /**
     * 跳转到录入项目信息详细页
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping("/customerSite_add_project")
    public String customersiteAdd1() {
        return PREFIX + "customer_project_add.html";
    }

    @BussinessLog(value = "添加项目信息", key = "customerSiteID", dict = ProjectDict.class)
    @RequestMapping(value = "/project_add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData add(Project project) {
        this.projectService.addProject(project);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "删除部门", key = "customerSiteID", dict = CustomerSiteDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam("customerSiteID") Long customerSiteID) {

        customerSiteService.deleteCustomerSiteByCustomerSiteID(customerSiteID);

        return SUCCESS_TIP;
    }

    /**
     * 跳转到修改客户信息详细页
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @Permission
    @RequestMapping("/customer_site_edit")
    public String openEditPage(@RequestParam("customerSiteID") Long customerSiteID) {

        if (ToolUtil.isEmpty(customerSiteID)) {
            throw new RequestEmptyException();
        }
        //缓存部门修改前详细信息
        CustomerSite customerSite = customerSiteService.getById(customerSiteID);
        LogObjectHolder.me().set(customerSite);

        return PREFIX + "customer_site_edit.html";
    }

    /**
     * 现场信息详细页信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/detail/{customerSiteID}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("customerSiteID") Long customerSiteID) {
        CustomerSite customerSite = customerSiteService.getCustomerSiteByCustomerSiteID(customerSiteID);
        CustomerSiteDto customerSiteDto = new CustomerSiteDto();
        BeanUtil.copyProperties(customerSite, customerSiteDto);
        return customerSiteDto;
    }

    /**
     * 修改客户信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "修改现场信息", key = "customerSiteID", dict = CustomerSiteDict.class)
    @RequestMapping(value = "/customerSiteEdit")
    @Permission
    @ResponseBody
    public ResponseData customerSiteEdit(CustomerSite customerSite) {
        customerSiteService.updateSite(customerSite);
        return SUCCESS_TIP;
    }

    /**
     * 获取所有客户信息
     *
     * @author liuyang
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getCompanies")
    @ResponseBody
    public Object getCompanies() {
        return companyService.listForSelect();
    }
}
