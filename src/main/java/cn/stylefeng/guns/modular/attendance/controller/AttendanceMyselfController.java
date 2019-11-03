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

import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.modular.attendance.entity.ViewAttendance;
import cn.stylefeng.guns.modular.attendance.model.AttendanceRecordDto;
import cn.stylefeng.guns.modular.attendance.service.AttendanceService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.SuccessResponseData;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
     * 我的考勤input
     *
     * @return
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "attendance-myself.html";
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {

        ShiroUser currentUser = ShiroKit.getUser();
        List<ViewAttendance> viewAttendances = attendanceService.listMyAttendanceByMonth(currentUser.getId());


//        ShiroUser currentUser = ShiroKit.getUser();
//        List<AttendanceRecordDto> attendanceRecords = attendanceService.listMyAttendance(currentUser.getId(), currentMonth);
//
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
//        AttendanceRecordDto attendanceRecordDto = new AttendanceRecordDto();
//        BeanUtil.copyProperties(attendance, attendanceRecordDto);
        return SuccessResponseData.success(attendance);
    }


    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(@RequestBody ArrayList<AttendanceRecordDto> records) {

        return attendanceService.recordAttendance(records);
    }

}
