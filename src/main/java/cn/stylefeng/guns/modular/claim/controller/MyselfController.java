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
package cn.stylefeng.guns.modular.claim.controller;

import java.util.List;
import java.util.Map;

import cn.stylefeng.guns.modular.system.mapper.DictMapper;
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
import cn.stylefeng.guns.core.common.constant.dictmap.MyselfDict;
import cn.stylefeng.guns.core.common.constant.dictmap.MyselfMap;
import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.modular.claim.service.MyselfService;
import cn.stylefeng.guns.modular.claim.wrapper.MyselfWrapper;
import cn.stylefeng.guns.modular.system.entity.Dict;
import cn.stylefeng.guns.modular.claim.entity.Myself;
import cn.stylefeng.guns.modular.system.model.MyselfDto;
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
@RequestMapping("claim/myself")
public class MyselfController extends BaseController {

    private String PREFIX = "/modular/claim/myself/";

    @Autowired
    private MyselfService myselfService;
    
    /**
     * 跳转到客户信息管理首页
     *
     * @author tongyue
     * @Date 2019/10/28 14:58 PM
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "myself.html";
    }
    /**
     * 获取我的报销信息列表
     *
     * @author tongyue
     * @Date 2018/12/23 6:31 PM
     */
    @RequestMapping(value = "/list")
    @Permission
    @ResponseBody
    public Object list(String condition) {
        Page<Map<String, Object>> list = this.myselfService.list(condition);
        Page<Map<String, Object>> wrap = new MyselfWrapper(list).wrap();
        return LayuiPageFactory.createPageInfo(wrap);
    }
    
    /**
     * 跳转到录入用户信息详细页
     *
     * @author fengshuonan     
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping("/myself_add")
    public String myselfAdd() {
        return PREFIX + "myself_add.html";
    }
    /**
     * 录入用户信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "添加我的报销单信息", key = "claimId", dict = MyselfMap.class)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public ResponseData add(Myself myself) {
        this.myselfService.addmyself(myself);
        return SUCCESS_TIP;
    }   
    /**
     * 跳转到修改客户信息详细页
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @Permission
    @RequestMapping("/myself_edit")
    public String openEditPage(@RequestParam("claimId") Long claimId) {

        if (ToolUtil.isEmpty(claimId)) {
            throw new RequestEmptyException();
        }

        return PREFIX + "myself_edit.html";
    }
    /**
     * 客户信息详细页信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/detail/{claimId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("claimId") Long claimId) {
        Myself myself = myselfService.getById(claimId);
        MyselfDto myselfDto = new MyselfDto();
        BeanUtil.copyProperties(myself, myselfDto);
        return myselfDto;
    }
    /**
     * 修改客户信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "修改我的报销单", key = "claimId", dict = MyselfDict.class)
    @RequestMapping(value = "/myselfEdit")
    @Permission
    @ResponseBody
    public ResponseData myselfEdit(Myself myself) {
        myselfService.editMyself(myself);
        return SUCCESS_TIP;
    }
    /**
     * 删除部门
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "删除部门", key = "claimId", dict = MyselfDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Long claimId) {

        myselfService.removeById(claimId);

        return SUCCESS_TIP;
    }   
    
    @RequestMapping(value = "/getClaimType")
    @ResponseBody
    public List<Dict> getClaimType() {
        List<Dict> list = this.myselfService.getClaimType();
        return list;
    }   

}