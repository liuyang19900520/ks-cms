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

import cn.stylefeng.guns.modular.attendance.entity.AttendanceAllRecord;
import cn.stylefeng.guns.modular.attendance.service.AttendanceService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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
     *
     * @return
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "attendance-list.html";
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "currentMonth", required = false) String currentMonth) {

        List<AttendanceAllRecord> attendanceAllRecords = attendanceService.selectAllUser(currentMonth);


        HashMap<String, Object> result = Maps.newHashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("count", attendanceAllRecords.size());
        result.put("data", attendanceAllRecords);
        return result;
    }


}
