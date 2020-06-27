package cn.stylefeng.guns.modular.employee.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.oauth2.entity.OauthUserInfo;
import cn.stylefeng.guns.base.oauth2.service.OauthUserInfoService;
import cn.stylefeng.guns.base.pojo.node.MenuNode;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.modular.employee.mapper.EmployeeMapper;
import cn.stylefeng.guns.sys.core.constant.Const;
import cn.stylefeng.guns.sys.core.constant.factory.ConstantFactory;
import cn.stylefeng.guns.sys.core.constant.state.ManagerStatus;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.core.util.DefaultImages;
import cn.stylefeng.guns.sys.core.util.SaltUtil;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.entity.UserPos;
import cn.stylefeng.guns.sys.modular.system.factory.UserFactory;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.sys.modular.system.model.UserDto;
import cn.stylefeng.guns.sys.modular.system.service.MenuService;
import cn.stylefeng.guns.sys.modular.system.service.UserPosService;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.util.SpringContextHolder;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;


///*    *//**
//     * 添加用戶
//     *
//     * @author fengshuonan
//     * @Date 2018/12/24 22:51
//     *//*
//    @Transactional(rollbackFor = Exception.class)
//    public void addUser(UserDto user) {
//
//        // 判断账号是否重复
//        User theUser = this.getByAccount(user.getAccount());
//        if (theUser != null) {
//            throw new ServiceException(BizExceptionEnum.USER_ALREADY_REG);
//        }
//
//        // 完善账号信息
//        String salt = SaltUtil.getRandomSalt();
//        String password = SaltUtil.md5Encrypt(user.getPassword(), salt);
//
//        User newUser = UserFactory.createUser(user, password, salt);
//        this.save(newUser);
//
//        //添加职位关联
//        addPosition(user.getPosition(), newUser.getUserId());
//    }
//
//    *//**
//     * 修改用户
//     *
//     * @author fengshuonan
//     * @Date 2018/12/24 22:53
//     *//*
//    @Transactional(rollbackFor = Exception.class)
//    public void editUser(UserDto user) {
//        User oldUser = this.getById(user.getUserId());
//
//        if (LoginContextHolder.getContext().hasRole(Const.ADMIN_NAME)) {
//            this.updateById(UserFactory.editUser(user, oldUser));
//        } else {
//            this.assertAuth(user.getUserId());
//            LoginUser shiroUser = LoginContextHolder.getContext().getUser();
//            if (shiroUser.getId().equals(user.getUserId())) {
//                this.updateById(UserFactory.editUser(user, oldUser));
//            } else {
//                throw new ServiceException(BizExceptionEnum.NO_PERMITION);
//            }
//        }
//
//        //删除职位关联
//        userPosService.remove(new QueryWrapper<UserPos>().eq("user_id", user.getUserId()));
//
//        //添加职位关联
//        addPosition(user.getPosition(), user.getUserId());
//    }
//
//    *//**
//     * 删除用户
//     *
//     * @author fengshuonan
//     * @Date 2018/12/24 22:54
//     *//*
//    @Transactional(rollbackFor = Exception.class)
//    public void deleteUser(Long userId) {
//
//        //不能删除超级管理员
//        if (userId.equals(Const.ADMIN_ID)) {
//            throw new ServiceException(BizExceptionEnum.CANT_DELETE_ADMIN);
//        }
//        this.assertAuth(userId);
//        this.setStatus(userId, ManagerStatus.DELETED.getCode());
//
//        //删除对应的oauth2绑定表
//        OauthUserInfoService oauthUserInfoService = null;
//        try {
//            oauthUserInfoService = SpringContextHolder.getBean(OauthUserInfoService.class);
//            oauthUserInfoService.remove(new QueryWrapper<OauthUserInfo>().eq("user_id", userId));
//        } catch (Exception e) {
//            //没有集成oauth2模块，不操作
//        }
//
//        //删除职位关联
//        userPosService.remove(new QueryWrapper<UserPos>().eq("user_id", userId));
//    }*/



    /**
     * 根据条件查询用户列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:45
     */
    public Page<Map<String, Object>> selectEmployees(DataScope dataScope, String chineseName) {
        Page page = LayuiPageFactory.defaultPage();
        return this.employeeMapper.selectUsers(page, dataScope, chineseName);
    }


//    /**
//     * 判断当前登录的用户是否有操作这个用户的权限
//     *
//     * @author fengshuonan
//     * @Date 2018/12/24 22:44
//     */
//    public void assertAuth(Long userId) {
//        if (LoginContextHolder.getContext().isAdmin()) {
//            return;
//        }
//        List<Long> deptDataScope = LoginContextHolder.getContext().getDeptDataScope();
//        User user = this.getById(userId);
//        Long deptId = user.getDeptId();
//        if (deptDataScope.contains(deptId)) {
//            return;
//        } else {
//            throw new ServiceException(BizExceptionEnum.NO_PERMITION);
//        }
//    }
//
//
//
//    /**
//     * 获取用户的基本信息
//     *
//     * @author fengshuonan
//     * @Date 2019-05-04 17:12
//     */
//    public Map<String, Object> getUserInfo(Long userId) {
//        User user = this.getById(userId);
//        Map<String, Object> map = UserFactory.removeUnSafeFields(user);
//
//        HashMap<String, Object> hashMap = CollectionUtil.newHashMap();
//        hashMap.putAll(map);
//        hashMap.put("roleName", ConstantFactory.me().getRoleName(user.getRoleId()));
//        hashMap.put("deptName", ConstantFactory.me().getDeptName(user.getDeptId()));
//
//        return hashMap;
//    }
//
//    /**
//     * 获取用户首页信息
//     *
//     * @author fengshuonan
//     * @Date 2019/10/17 16:18
//     */
//    public Map<String, Object> getUserIndexInfo() {
//
//        //获取当前用户角色列表
//        LoginUser user = LoginContextHolder.getContext().getUser();
//        List<Long> roleList = user.getRoleList();
//
//        //用户没有角色无法显示首页信息
//        if (roleList == null || roleList.size() == 0) {
//            return null;
//        }
//
//        List<Map<String, Object>> menus = this.getUserMenuNodes(roleList);
//
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("menus", menus);
//        result.put("avatar", DefaultImages.defaultAvatarUrl());
//        result.put("name", user.getName());
//
//        return result;
//    }

}
