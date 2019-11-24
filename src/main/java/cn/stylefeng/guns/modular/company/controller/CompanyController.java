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

import java.util.Map;

import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.constant.dictmap.CustomerSiteDict;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.company.entity.CustomerSite;
import cn.stylefeng.guns.modular.company.service.CustomerSiteService;
import cn.stylefeng.guns.modular.system.warpper.CustomerSiteWrapper;
import cn.stylefeng.roses.core.datascope.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.dictmap.CompanyDict;
import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.company.service.CompanyService;
import cn.stylefeng.guns.modular.company.wrapper.CompanyWrapper;
import cn.stylefeng.guns.modular.company.entity.Company;
import cn.stylefeng.guns.modular.system.model.CompanyDto;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;

/**
 * 客户信息控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("customer/company")
public class CompanyController extends BaseController {

    private String PREFIX = "/modular/customer/customer/";

    @Autowired
    private CompanyService companyService;
   // @Autowired
    private CustomerSiteService customerSiteService;

    /**
     * 跳转到客户信息管理首页
     *
     * @author tongyue
     * @Date 2019/10/28 14:58 PM
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "company.html";
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
    public Object list(@RequestParam(required = false) String companyName){
    //Page<Map<String, Object>> list = this.companyService.list(condition);
       // Page<Map<String, Object>> wrap = new CompanyWrapper(list).wrap();
       // return LayuiPageFactory.createPageInfo(wrap);
        if (ShiroKit.isAdmin()) {
            Page<Map<String, Object>> companys = companyService.list(null,companyName);
            Page wrapped = new CompanyWrapper(companys).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        } else {
            DataScope dataScope = new DataScope(ShiroKit.getDeptDataScope());
            Page<Map<String, Object>> companys = companyService.list(dataScope,companyName);
            Page wrapped = new CompanyWrapper(companys).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        }
    }

    /**
     * 跳转到录入用户信息详细页
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping("/company_add")
    public String companyAdd() {
        return PREFIX + "company_add.html";
    }

    /**
     * 录入用户信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "添加用户信息", key = "customerID", dict = CompanyDict.class)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public ResponseData add(Company company) {
        this.companyService.addCompany(company);
        return SUCCESS_TIP;
    }


    /**
     * 录入现场信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping("/company_add_site")
    public String companyAdd1() {
        return PREFIX + "company_add_site.html";
    }

    @BussinessLog(value = "添加现场信息", key = "customerID", dict = CustomerSiteDict.class)
    @RequestMapping(value = "/company_add1")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData add(CustomerSite customer) {
        this.customerSiteService.addCustomerSite(customer);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到修改客户信息详细页
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @Permission
    @RequestMapping("/company_edit")
    public String openEditPage(@RequestParam("customerID") Long customerID) {

        if (ToolUtil.isEmpty(customerID)) {
            throw new RequestEmptyException();
        }
        //缓存部门修改前详细信息
        Company company = companyService.getById(customerID);
        LogObjectHolder.me().set(company);

        return PREFIX + "company_edit.html";
    }

    /**
     * 客户信息详细页信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/detail/{customerID}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("customerID") Long customerID) {
        Company company = companyService.getById(customerID);
        CompanyDto companyDto = new CompanyDto();
        BeanUtil.copyProperties(company, companyDto);
        return companyDto;
    }

    /**
     * 修改客户信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "修改客户信息", key = "customerID", dict = CompanyDict.class)
    @RequestMapping(value = "/companyEdit")
    @Permission
    @ResponseBody
    public ResponseData companyEdit(Company company) {
        companyService.editCompany(company);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "删除部门", key = "customerID", dict = CompanyDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Long customerID) {

        companyService.removeById(customerID);

        return SUCCESS_TIP;
    }

}
