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
package cn.stylefeng.guns.modular.system.controller;

import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.constant.dictmap.CompanyDict;
import cn.stylefeng.guns.core.common.constant.dictmap.CustomerSiteDict;
import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.attendance.entity.AttendanceAllRecord;
import cn.stylefeng.guns.modular.attendance.service.AttendanceService;
import cn.stylefeng.guns.modular.company.wrapper.CompanyWrapper;
import cn.stylefeng.guns.modular.system.entity.Company;
import cn.stylefeng.guns.modular.system.entity.CustomerSite;
import cn.stylefeng.guns.modular.system.model.CustomerSiteDto;
import cn.stylefeng.guns.modular.system.model.UserDto;
import cn.stylefeng.guns.modular.system.service.CustomerSiteService;
import cn.stylefeng.guns.modular.system.warpper.CustomerSiteWrapper;
import cn.stylefeng.guns.modular.system.warpper.UserWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
    public Object list(@RequestParam(required = false) String customersiteName) {
        if (ShiroKit.isAdmin()) {
            Page<Map<String, Object>> sites = customerSiteService.list(null,customersiteName );
            Page wrapped = new  CustomerSiteWrapper(sites).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        } else {
            DataScope dataScope = new DataScope(ShiroKit.getDeptDataScope());
            Page<Map<String, Object>> sites = customerSiteService.list(dataScope,customersiteName );
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
        return PREFIX + "customer_site_add.html"; }


    /**
     * 录入现场信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "添加现场信息", key = "customerID", dict = CustomerSiteDict.class)
    @RequestMapping(value = "/add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    //public ResponseData add(@Valid CustomerSiteDto customer, BindingResult result) {
       //if (result.hasErrors()) {
            //throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
       // }
      //  this.customerSiteService.addCustomerSite(customer);
      //  return SUCCESS_TIP;
   // }

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
    @RequestMapping("/customersite_add_project")
    public String customersiteAdd1() { return PREFIX + "project.html";}

    /**
     * 删除部门
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "删除部门", key = "customerID", dict = CustomerSiteDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Long customerID) {

        customerSiteService.removeById(customerID);

        return SUCCESS_TIP;
    }


}
