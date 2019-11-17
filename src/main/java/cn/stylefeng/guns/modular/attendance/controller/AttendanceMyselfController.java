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
package cn.stylefeng.guns.modular.attendance.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Maps;

import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.dictmap.DeptDict;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.modular.attendance.entity.ViewAttendance;
import cn.stylefeng.guns.modular.attendance.model.AttendanceRecordDto;
import cn.stylefeng.guns.modular.attendance.service.AttendanceService;
import cn.stylefeng.guns.modular.system.entity.Dept;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * 部门控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/attendance/myself")
public class AttendanceMyselfController extends BaseController {

    private String PREFIX = "/modular/attendance/";

    @Autowired
    AttendanceService attendanceService;


    /**
     * 我的考勤input
     *
     * @return
     */
    @RequestMapping("/input")
    public String inputIndex() {
        return PREFIX + "attendance_input.html";
    }


    /**
     * 我的考勤
     *
     * @return
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "attendance-myself.html";
    }

//渲染表格
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {

        ShiroUser currentUser = ShiroKit.getUser();
        List<ViewAttendance> viewAttendances = attendanceService.listMyAttendanceByMonth(currentUser.getId());
        HashMap<String, Object> result = Maps.newHashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("count", viewAttendances.size());
        result.put("data", viewAttendances);
        return result;
    }


    /**
     * 考勤详情
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/message")
    @ResponseBody
    public Object detail() {
        ViewAttendance attendance = this.attendanceService.getCustomerSiteInfo();       
        return SuccessResponseData.success(attendance);
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(@RequestBody ArrayList<AttendanceRecordDto> records) {

        return attendanceService.recordAttendance(records);
    }
    
    /**
     * 添加考勤
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/add")  
    @ResponseBody
    public ResponseData add(ViewAttendance attendance) {
        
        this.attendanceService.addAttendancer(attendance);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到编辑考勤页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/attendance_edit")
    public String attendanceEdit(@RequestParam("workMonth") Date workMonth) {
        if (ToolUtil.isEmpty(workMonth)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        ViewAttendance viewAttendance = attendanceService.selectMonthEmployee(workMonth);
        LogObjectHolder.me().set(viewAttendance);
        return PREFIX + "attendance_edit.html";
    }
    
    /**
     * 编辑考勤显示详情
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping("/edit_message")
    @ResponseBody
    public Object editMassage(@RequestParam Date workMonth) {
    	
        ViewAttendance attendance = this.attendanceService.selectMonthEmployee(workMonth);
        return SuccessResponseData.success(attendance);
        
    }
    
    /**
     * 编辑考勤
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
   // @BussinessLog(value = "编辑考勤", key = "simpleName", dict = DeptDict.class)
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public ResponseData update(ViewAttendance attendance) {
    	attendanceService.editAttendanceService(attendance);
        return SUCCESS_TIP;
    }

    /**
     * 删除考勤
     *
     * @author 
     * @Date 2019/11/09 14:28 PM
     */
   // @BussinessLog(value = "删除考勤", key = "simpleName", dict = DeptDict.class)
    @RequestMapping(value = "/attendance_delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Date workMonth) {
    	attendanceService.deleteAttendanceService(workMonth);
        return SUCCESS_TIP;
    }
}
