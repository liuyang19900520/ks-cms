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
package cn.stylefeng.guns.modular.employee.controller;

import java.util.HashMap;
import java.util.Map;

import cn.stylefeng.guns.modular.company.service.CompanyService;
import cn.stylefeng.guns.modular.company.service.CustomerSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.dictmap.CompanyDict;
import cn.stylefeng.guns.core.common.constant.dictmap.UserDict;
import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.company.entity.Company;
import cn.stylefeng.guns.modular.employee.entity.InfoMgr;
import cn.stylefeng.guns.modular.employee.service.InfoMgrService;
import cn.stylefeng.guns.modular.employee.wrapper.InfoMgrWrapper;
import cn.stylefeng.guns.modular.system.entity.Employee;
import cn.stylefeng.guns.modular.system.entity.User;
import cn.stylefeng.guns.modular.system.factory.UserFactory;
import cn.stylefeng.guns.modular.system.model.CompanyDto;
import cn.stylefeng.guns.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * 员工客户信息控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("employee/info")
public class InfoMgrController extends BaseController {

    private String PREFIX = "/modular/employee/info/";

    @Autowired
    private InfoMgrService infoMgrService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CustomerSiteService customerSiteService;

    /**
     * 跳转到员工信息管理首页
     *
     * @author tongyue
     * @Date 2019/10/28 14:58 PM
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "infoMgr.html";
    }

    /**
     * 获取员工信息列表
     *
     * @author tongyue
     * @Date 2018/12/23 6:31 PM
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Map<String, Object>> list = this.infoMgrService.list(condition);
        Page<Map<String, Object>> wrap = new InfoMgrWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }

    /**
     * 跳转到修改客户信息详细页
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping("/open_detail")
    public String openDetail(@RequestParam("userId") Long userId) {

        if (ToolUtil.isEmpty(userId)) {
            throw new RequestEmptyException();
        }
        //缓存部门修改前详细信息
        User user = this.userService.getById(userId);
        LogObjectHolder.me().set(user);

        return PREFIX + "infoDetail.html";
    }

    /**
     * 获取用户详情
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Object getUserInfo(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new RequestEmptyException();
        }

        this.userService.assertAuth(userId);
        User user = this.userService.getById(userId);
        Employee employeeInfo = userService.getEmployeeInfo(userId);

        Map<String, Object> map = UserFactory.removeUnSafeFields(user);

        HashMap<Object, Object> hashMap = CollectionUtil.newHashMap();
        hashMap.putAll(map);
        hashMap.put("roleName", ConstantFactory.me().getRoleName(user.getRoleId()));
        hashMap.put("deptName", ConstantFactory.me().getDeptName(user.getDeptId()));

        //将employeeInfo的信息一个一个传到map中，map负责返回到页面上，页面name和map的key相对应
        hashMap.put("address", employeeInfo.getAddress());
        hashMap.put("station", employeeInfo.getStation());
        hashMap.put("city", employeeInfo.getCity());
        hashMap.put("province", employeeInfo.getProvince());
        hashMap.put("entryTime", employeeInfo.getEntryTime());
        hashMap.put("employeeNameKANA", employeeInfo.getEmployeeNameKANA());
        hashMap.put("employeeNameJP", employeeInfo.getEmployeeNameJP());
        hashMap.put("employeeNameCN", employeeInfo.getEmployeeNameCN());


        return ResponseData.success(hashMap);
    }

    /**
     * 跳转到角色分配页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/project/assign")
    public String projAssign(Model model) {
        return PREFIX + "projAssign.html";
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

    /**
     * 获取所有客户信息
     *
     * @author liuyang
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/customer-site/{customerId}")
    @ResponseBody
    public Object getCustomerSites(@PathVariable Long customerId) {
        return customerSiteService.listForSelect(customerId);
    }

}