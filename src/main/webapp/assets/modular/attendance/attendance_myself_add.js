/**
 * 添加我的考勤
 */
var UserInfoDlg = {
    data: {
        employeeId: ""

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

    //日期显示当前月
    laydate.render({
        elem: '#workMonth',
        value: new Date(),
        done: function (value, obj) {
        }
    });




    // //获取用户信息
    var ajax = new $ax(Feng.ctxPath + "/attendance/myself/message");
    var result = ajax.start();
    form.val('attendanceInputForm', result.data);
    //
    // // 表单提交事件
    // form.on('submit(btnSubmit)', function (data) {
    //     var ajax = new $ax(Feng.ctxPath + "/attendance/myself/add", function (data) {
    //         Feng.success("修改成功！");
    //
    //         //传给上个页面，刷新table用
    //         admin.putTempData('formOk', true);
    //
    //         //关掉对话框
    //         admin.closeThisDialog();
    //     }, function (data) {
    //         Feng.error("修改成功！" + data.responseJSON.message)
    //     });
    //     ajax.set(data.field);
    //     ajax.start();
    // });
});