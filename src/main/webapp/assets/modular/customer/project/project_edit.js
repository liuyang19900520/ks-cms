layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();



    //获取考勤
    var ajax = new $ax(Feng.ctxPath + "/customer/project/attendance-type");
    var result = ajax.start();

    var optionsAttendanceType;
    $.each(result, function (key, value) {  //循环遍历后台传过来的json数据
        optionsAttendanceType += "<option value=\"" + value.attendanceType + "\" >" + value.attendanceTypeShow + "</option>";
    });
    $("#sel-attendance-type").append("<option value=''>请选择考勤类型</option> " + optionsAttendanceType);
    form.render('select');

    //获取部门信息
    var ajax = new $ax(Feng.ctxPath + "/customer/project/detail/" + Feng.getUrlParam("projectId"));
    var result = ajax.start();
    form.val('projectForm', result);

    //渲染时间选择框
    laydate.render({
        elem: '#projectStart',
        trigger:'click'
    });

    laydate.render({
        elem: '#projectEnd',
        trigger:'click'
    });




     //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/customer/project/projectEdit", function (data) {
            Feng.success("修改成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });
    
});