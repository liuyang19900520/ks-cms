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

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.employee.service.EmployeeService;
import cn.stylefeng.guns.modular.employee.wrapper.EmployeeWrapper;
import cn.stylefeng.guns.sys.core.constant.Const;
import cn.stylefeng.guns.sys.core.constant.dictmap.UserDict;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.model.UserDto;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.sys.modular.system.warpper.UserWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import cn.stylefeng.roses.kernel.model.response.SuccessResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;

/**
 * 系统管理员控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("/employee")
@Validated
public class EmployeeController extends BaseController {

    private static String PREFIX = "/employee/";

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    /**
     * 跳转到查看管理员列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "employee.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/user_add")
    public String addView() {
        return PREFIX + "employee_add.html";
    }

    /**
     * 跳转到角色分配页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/role_assign")
    public String roleAssign(@RequestParam Long userId, Model model) {
        model.addAttribute("userId", userId);
        return PREFIX + "user_roleassign.html";
    }

    /**
     * 跳转到编辑管理员页面
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @Permission
    @RequestMapping("/user_edit")
    public String userEdit(@RequestParam Long userId) {
        User user = this.userService.getById(userId);
        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * 获取用户详情
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public SuccessResponseData getUserInfo(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new RequestEmptyException();
        }

        this.userService.assertAuth(userId);
        return new SuccessResponseData(userService.getUserInfo(userId));
    }


    /**
     * 查询管理员列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String ChineseName) {

        if (LoginContextHolder.getContext().isAdmin()) {
            Page<Map<String, Object>> users = employeeService.selectEmployees(null, ChineseName);
            Page wrapped = new EmployeeWrapper(users).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        } else {
            DataScope dataScope = new DataScope(LoginContextHolder.getContext().getDeptDataScope());
            Page<Map<String, Object>> users = employeeService.selectEmployees(dataScope, ChineseName);
            Page wrapped = new UserWrapper(users).wrap();
            return LayuiPageFactory.createPageInfo(wrapped);
        }
    }

    /**
     * 添加管理员
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/add")
    @BussinessLog(value = "添加管理员", key = "account", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData add(@Valid UserDto user) {
        this.userService.addUser(user);
        return SUCCESS_TIP;
    }
//
//    /**
//     * 修改管理员
//     *
//     * @author fengshuonan
//     * @Date 2018/12/24 22:44
//     */
//    @RequestMapping("/edit")
//    @BussinessLog(value = "修改管理员", key = "account", dict = UserDict.class)
//    @ResponseBody
//    public ResponseData edit(UserDto user) {
//        this.userService.editUser(user);
//        return SUCCESS_TIP;
//    }
//
//    /**
//     * 删除管理员（逻辑删除）
//     *
//     * @author fengshuonan
//     * @Date 2018/12/24 22:44
//     */
//    @RequestMapping("/delete")
//    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
//    @Permission
//    @ResponseBody
//    public ResponseData delete(@RequestParam Long userId) {
//        if (ToolUtil.isEmpty(userId)) {
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
//        }
//        this.userService.deleteUser(userId);
//        return SUCCESS_TIP;
//    }

//    /**
//     * 查看管理员详情
//     *
//     * @author fengshuonan
//     * @Date 2018/12/24 22:44
//     */
//    @RequestMapping("/view/{userId}")
//    @ResponseBody
//    public User view(@PathVariable Long userId) {
//        if (ToolUtil.isEmpty(userId)) {
//            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
//        }
//        this.userService.assertAuth(userId);
//        return this.userService.getById(userId);
//    }


//    /**
//     * 上传图片
//     *
//     * @author fengshuonan
//     * @Date 2018/12/24 22:44
//     */
//    @RequestMapping(method = RequestMethod.POST, path = "/upload")
//    @ResponseBody
//    public String upload(@RequestPart("file") MultipartFile picture) {
//
//        String pictureName = UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(picture.getOriginalFilename());
//        try {
//            String fileSavePath = ConstantsContext.getFileUploadPath();
//            picture.transferTo(new File(fileSavePath + pictureName));
//        } catch (Exception e) {
//            throw new ServiceException(BizExceptionEnum.UPLOAD_ERROR);
//        }
//        return pictureName;
//    }
//
//    /**
//     * 选择办理人
//     *
//     * @author fengshuonan
//     * @Date 2019-8-22 15:48
//     */
//    @RequestMapping("/listUserAndRoleExpectAdmin")
//    @ResponseBody
//    public LayuiPageInfo listUserAndRoleExpectAdmin() {
//        Page pageContext = LayuiPageFactory.defaultPage();
//        IPage page = userService.listUserAndRoleExpectAdmin(pageContext);
//        return LayuiPageFactory.createPageInfo(page);
//    }
}
