/**
 * 用户详情对话框
 */
var UserInfoDlg = {
    data: {
        deptId: "",
        deptName: ""
    }
};

layui.use(['layer', 'form', 'admin', 'laydate', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取用户信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/getUserInfo?userId=" + Feng.getUrlParam("userId"));
    var result = ajax.start();
    form.val('userForm', result.data);

    // 渲染时间选择框
    laydate.render({
        elem: '#birthday'
    });
});