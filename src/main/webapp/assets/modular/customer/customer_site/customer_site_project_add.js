layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    var customerSiteID = Feng.getUrlParam("customerSiteID");
    $("#ipt-add-customer-site-id").val(customerSiteID);
    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/customer/site/project_add", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();


        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });


    //获取用户信息
    var ajax = new $ax(Feng.ctxPath + "/customer/project/attendance-type");
    var result = ajax.start();

    var optionsAttendanceType;
    $.each(result, function (key, value) {  //循环遍历后台传过来的json数据
        optionsAttendanceType += "<option value=\"" + value.attendanceType + "\" >" + value.attendanceTypeShow + "</option>";
    });
    $("#sel-attendance-type").append("<option value=''>请选择考勤类型</option> " + optionsAttendanceType);
    form.render('select');


});