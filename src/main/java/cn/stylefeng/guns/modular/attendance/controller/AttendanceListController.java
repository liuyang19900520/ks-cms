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

import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.util.LDateUtils;
import cn.stylefeng.guns.modular.attendance.entity.AttendanceAllRecord;
import cn.stylefeng.guns.modular.attendance.entity.AttendanceConfirmStatus;
import cn.stylefeng.guns.modular.attendance.entity.ViewAttendance;
import cn.stylefeng.guns.modular.attendance.service.AttendanceService;
import cn.stylefeng.guns.modular.system.entity.Dict;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;


/**
 * 部门控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/attendance/list")
public class AttendanceListController extends BaseController {

    private String PREFIX = "/modular/attendance/";

    @Autowired
    AttendanceService attendanceService;


    /**
     * 我的考勤
     * @return
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "attendance-list.html";
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "workMonth",required = false) String workMonth,
                         @RequestParam(value = "employeeId",required = false) Long empId,
                         @RequestParam(value = "status",required = false) String status
                       ){

        Page<AttendanceAllRecord> attendanceAllRecords;
        Date searchMonth;

        if (workMonth != null && !workMonth.equals("")) {
            searchMonth = LDateUtils.stringToDate(workMonth, "yyyyMM");
            attendanceAllRecords = attendanceService.selectAllAttendance(searchMonth,empId,status);
        }else{
            attendanceAllRecords = attendanceService.selectAllAttendance(null,empId,status);
        }
        return LayuiPageFactory.createPageInfo(attendanceAllRecords);

    }


    @RequestMapping(value = "/getEmployeeType")
    @ResponseBody
    public List<Dict> getEmployeeType() {
        List<Dict> list = attendanceService.getEmployee();
        return list;
    }


    /**
     * 修改考勤审批状态
     * 将状态从已确认改为未确认
     */
    @RequestMapping("/unconfirm")
    @ResponseBody
    public ResponseData unconfirm(@RequestBody ViewAttendance viewAttendance) {
        if (ToolUtil.isEmpty(viewAttendance.getEmployeeId())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        System.out.println(viewAttendance.getWorkMonth());
        this.attendanceService.updateStatus(viewAttendance.getEmployeeId(), AttendanceConfirmStatus.UNCONFIRMED.getCode(), viewAttendance.getWorkMonth());
        return SUCCESS_TIP;
    }

    /**
     * 修改考勤审批状态
     * 将状态从未确认改为已确认
     */
    @RequestMapping("/confirm")
    @ResponseBody
    public ResponseData confirm(@RequestBody ViewAttendance viewAttendance) {
        if (ToolUtil.isEmpty(viewAttendance.getEmployeeId())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        this.attendanceService.updateStatus(viewAttendance.getEmployeeId(), AttendanceConfirmStatus.CONFIRMED.getCode(), viewAttendance.getWorkMonth());

        return SUCCESS_TIP;
    }
}
