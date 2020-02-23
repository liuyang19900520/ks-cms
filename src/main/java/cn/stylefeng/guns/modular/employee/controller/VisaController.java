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

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.dictmap.CustomerSiteDict;
import cn.stylefeng.guns.core.common.constant.dictmap.DeptDict;
import cn.stylefeng.guns.core.common.constant.dictmap.MyselfDict;
import cn.stylefeng.guns.core.common.constant.dictmap.VisaDict;
import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.claim.entity.Myself;
import cn.stylefeng.guns.modular.company.entity.CustomerSite;
import cn.stylefeng.guns.modular.employee.entity.Visa;
import cn.stylefeng.guns.modular.employee.service.VisaService;
import cn.stylefeng.guns.modular.employee.wrapper.VisaWrapper;
import cn.stylefeng.guns.modular.system.entity.Dict;
import cn.stylefeng.guns.modular.system.model.MyselfDto;
import cn.stylefeng.guns.modular.system.model.VisaDto;
import cn.stylefeng.roses.core.base.controller.BaseController;
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

import java.util.List;
import java.util.Map;

/**
 * 员工客户信息控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("employee/visa")
public class VisaController extends BaseController {

    private String PREFIX = "/modular/employee/visa/";

    @Autowired
    private VisaService visaService;

    /**
     * 跳转到员工信息管理首页
     *
     * @author tongyue
     * @Date 2019/10/28 14:58 PM
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "visa.html";
    }

    /**
     * 获取员工签证信息列表
     *
     * @author tongyue
     * @Date 2018/12/23 6:31 PM
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String employeeNameCn) {
        Page<Map<String, Object>> list = this.visaService.list(employeeNameCn);
        Page<Map<String, Object>> wrap = new VisaWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }


    /**
     * 跳转到录入签证信息详细页
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping("/visa_add")
    public String companyAdd() {
        return PREFIX + "visa_add.html";
    }

    /**
     * 录入签证信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "添加签证信息", key = "employeeId", dict = VisaDict.class)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public ResponseData add(Visa visa) {
        this.visaService.addVisa(visa);
        return SUCCESS_TIP;
    }


    /**
     * 获取未注册签证信息的人员
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/unregister")
    @ResponseBody
    public Object selectUnregister() {
        return this.visaService.getUnregister();
    }

    /**
     * 获取签证类型
     *
     * @author liuyang
     * @Date 2018/12/24 22:43
     */
    @RequestMapping(value = "/getVisaType")
    @ResponseBody
    public List<Dict> getVisaType() {
        List<Dict> list = this.visaService.getVisaType();
        return list;
    }
    /**
     * 删除部门
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "删除部门", key = "employeeId", dict = VisaDict.class)
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam Long employeeId) {

        visaService.removeById(employeeId);

        return SUCCESS_TIP;
    }
    /**
     * 跳转到修改签证信息详细页
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @Permission
    @RequestMapping("/visa_edit")
    public String openEditPage(@RequestParam("employeeId") Long employeeId) {

        if (ToolUtil.isEmpty(employeeId)) {
            throw new RequestEmptyException();
        }

        return PREFIX + "visa_edit.html";
    }
    /**
     * 签证信息详细页信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/detail/{employeeId}")
    @ResponseBody
    public Object detail(@PathVariable("employeeId") Long employeeId) {
        Map<String, Object> stringObjectMap = visaService.selectVisaDetailById(employeeId);
        String code = (String) stringObjectMap.get("visaType");
        Map<String, Object> wrap = new VisaWrapper(stringObjectMap).wrap();
        wrap.put("visaType", code);

        return wrap;
    }
    /**
     * 修改客户信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/visaEdit")
    @ResponseBody
    public ResponseData myselfEdit(Visa visa) {
        visaService.editVisa(visa);
        return SUCCESS_TIP;
    }

}