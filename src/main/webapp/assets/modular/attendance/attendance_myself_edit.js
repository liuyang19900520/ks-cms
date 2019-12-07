/**
 * 角色详情对话框
 */
var attendanceInfo = {
    data: {
        employeeId: ""
    }
};

layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取信息
    var ajax = new $ax(Feng.ctxPath + "/attendance/myself/edit_message?workMonth=" + Feng.getUrlParam("workMonth"));       
    var result = ajax.start();
    form.val('attendanceEditForm', result.data);
    

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {

        var ajax = new $ax(Feng.ctxPath + "/attendance/myself/update", function (data) {
            Feng.success("修改成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败！" + data.responseJSON.message)
        });
        ajax.setContentType("application/json;charset=UTF-8")
        ajax.setData(JSON.stringify(data.field));
        ajax.start();
    });
});