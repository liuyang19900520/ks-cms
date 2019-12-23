layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //公司信息
    var ajax = new $ax(Feng.ctxPath + "/employee/info/getCompanies?userId=" + Feng.getUrlParam("userId"));
    var result = ajax.start();


    var optionsCompany;
    $.each(result, function (key, value) {  //循环遍历后台传过来的json数据
        optionsCompany += "<option value=\"" + value.customerID + "\" >" + value.companyName + "</option>";
    });
    $("#sel-company").append("<option value=''>请选择客户</option> " + optionsCompany);

    form.on('select(sel-company)', function (data) {
        var sitePath = Feng.ctxPath + "/employee/info/customer-site/" + $("#sel-company").val();
        var ajaxSite = new $ax(sitePath);
        var sites = ajaxSite.start();
        var optionsSites

        $.each(sites, function (key, value) {  //循环遍历后台传过来的json数据
            optionsSites += "<option value=\"" + value.customerSiteID + "\" >" + value.customerSiteName + "</option>";
        });
        $("#sel-site").html("<option value=''>请选择现场</option> " + optionsSites); //获得要赋值的select的id，进行赋值

        form.render('select');//select是固定写法 不是选择器

    });

    //获取考勤
    var ajax = new $ax(Feng.ctxPath + "/customer/project/attendance-type");
    var result = ajax.start();

    var optionsAttendanceType;
    $.each(result, function (key, value) {  //循环遍历后台传过来的json数据
        optionsAttendanceType += "<option value=\"" + value.attendanceType + "\" >" + value.attendanceTypeShow + "</option>";
    });
    $("#sel-attendance-type").append("<option value=''>请选择考勤类型</option> " + optionsAttendanceType);
    form.render('select');



    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/customer/project/project_add", function (data) {
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