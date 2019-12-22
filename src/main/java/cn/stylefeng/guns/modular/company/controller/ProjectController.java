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
import cn.stylefeng.guns.core.common.constant.dictmap.ProjectDict;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.attendance.entity.AttendanceType;
import cn.stylefeng.guns.modular.attendance.service.AttendanceService;
import cn.stylefeng.guns.modular.company.entity.Project;
import cn.stylefeng.guns.modular.company.service.ProjectService;
import cn.stylefeng.guns.modular.company.wrapper.ProjectWrapper;
import cn.stylefeng.guns.modular.system.model.ProjectDto;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 客户信息控制器
 *
 * @author fengshuonan
 * @Date 2017年2月17日20:27:22
 */
@Controller
@RequestMapping("/customer/project")
public class ProjectController extends BaseController {

    private String PREFIX = "/modular/customer/customer_project/";

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AttendanceService attendanceService;


    /**
     * 跳转到客户信息管理首页
     *
     * @author tongyue
     * @Date 2019/10/28 14:58 PM
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "project.html";
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
    public Object list(@RequestParam(required = false) String projectName) {
        if (ShiroKit.isAdmin()) {
            Page<Map<String, Object>> projects = projectService.list(null, projectName);
            List<Map<String, Object>> records = projects.getRecords();
            //将数据库取出的数据list转成records2ShowList
            List<Map<String, Object>> records2ShowList = Lists.newArrayList();

            for (int i = 0; i < records.size(); i++) {
                Map<String, Object> projectWithAttendance = records.get(i);
                //创建这个新的是为了添加上records2ShowList
                Map<String, Object> projectWithAttendance2Show = Maps.newHashMap();

                Object projectIdFromDB = projectWithAttendance.get("projectId");
                Object projectNameFromDB = projectWithAttendance.get("projectName");
                Object projectProcessFromDB = projectWithAttendance.get("projectProcess");
                Object projectTechFromDB = projectWithAttendance.get("projectTech");
                Object standardMaxTimeFromDB = projectWithAttendance.get("standardMaxTime");
                Object standardMinTimeFromDB = projectWithAttendance.get("standardMinTime");
                Object noonStartFromDB = projectWithAttendance.get("noonStart");
                Object noonEndFromDB = projectWithAttendance.get("noonEnd");
                Object noonFromDB = noonStartFromDB + "~" + noonEndFromDB;
                projectWithAttendance2Show.put("projectId", projectIdFromDB);
                projectWithAttendance2Show.put("projectName", projectNameFromDB);
                projectWithAttendance2Show.put("projectProcess", projectProcessFromDB);
                projectWithAttendance2Show.put("projectTech", projectTechFromDB);
                projectWithAttendance2Show.put("standardMaxTime", standardMaxTimeFromDB);
                projectWithAttendance2Show.put("standardMinTime", standardMinTimeFromDB);
                projectWithAttendance2Show.put("noon", noonFromDB);
                records2ShowList.add(projectWithAttendance2Show);
            }
            Page<Map<String, Object>> projects2Show = LayuiPageFactory.defaultPage();
            projects2Show.setRecords(records2ShowList);

            Page wrapped = new ProjectWrapper(projects2Show).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        } else {
            DataScope dataScope = new DataScope(ShiroKit.getDeptDataScope());
            Page<Map<String, Object>> projects = projectService.list(dataScope, projectName);
            Page wrapped = new ProjectWrapper(projects).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        }
    }

    /**
     * 跳转到录入项目信息详细页
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping("/add")
    public String projectAdd() {
        return PREFIX + "project_add.html";
    }

    /**
     * 录入项目信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "添加项目信息", key = "projectId", dict = ProjectDict.class)
    @RequestMapping(value = "/project_add")
    @Permission
    @ResponseBody
    public ResponseData add(Project project) {
        this.projectService.addProject(project);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到修改客户信息详细页
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @Permission
    @RequestMapping("/project_edit")
    public String openEditPage(@RequestParam("projectId") Long projectId) {

        if (ToolUtil.isEmpty(projectId)) {
            throw new RequestEmptyException();
        }
        //缓存部门修改前详细信息
        Project project = projectService.getById(projectId);
        LogObjectHolder.me().set(project);

        return PREFIX + "project_edit.html";
    }

    /**
     * 客户信息详细页信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @RequestMapping(value = "/detail/{projectId}")
    @Permission
    @ResponseBody
    public Object detail(@PathVariable("projectId") Long projectId) {
        Project project = projectService.getById(projectId);
        ProjectDto projectDto = new ProjectDto();
        BeanUtil.copyProperties(project, projectDto);
        return projectDto;
    }

    /**
     * 修改项目信息
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "修改项目信息", key = "projectId", dict = ProjectDict.class)
    @RequestMapping(value = "/projectEdit")
    @Permission
    @ResponseBody
    public ResponseData projectEdit(Project project) {
        projectService.editProject(project);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:57 PM
     */
    @BussinessLog(value = "删除项目", key = "projectId", dict = ProjectDict.class)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Long projectId) {

        projectService.removeById(projectId);

        return SUCCESS_TIP;
    }

    /**
     * 获取所有考勤类型
     *
     * @author liuyang
     * @Date 2019/12/24 22:43
     */
    @RequestMapping("/attendance-type")
    @ResponseBody
    public Object listAllAttendanceType() {
        List<AttendanceType> attendanceTypes = attendanceService.listAllAttendanceType();

        List<Map<String, Object>> attendanceTypeSelect = Lists.newArrayList();

        attendanceTypes.forEach((AttendanceType attendanceType) -> attendanceTypeSelect.add(makeAttendanceTypeSelect(attendanceType)));

        return attendanceTypeSelect;
    }


    private Map<String, Object> makeAttendanceTypeSelect(AttendanceType attendanceType) {

        StringBuffer attendanceTypeShow = new StringBuffer();

        attendanceTypeShow.append("最短时长：").append(attendanceType.getStandardMinTime()).append(";  ")
                .append("最长时长：").append(attendanceType.getStandardMaxTime());
        Map<String, Object> map = Maps.newHashMap();
        map.put("attendanceType", attendanceType.getAttendanceType());
        map.put("attendanceTypeShow", attendanceTypeShow.toString());

        return map;


    }
}
