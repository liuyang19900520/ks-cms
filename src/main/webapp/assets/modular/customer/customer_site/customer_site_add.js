layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/customer/site/add", function (data) {
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
    var ajax = new $ax(Feng.ctxPath + "/customer/site/getCompanies");
    var result = ajax.start();

    var optionsCompany;
    $.each(result, function (key, value) {  //循环遍历后台传过来的json数据
        optionsCompany += "<option value=\"" + value.customerID + "\" >" + value.companyName + "</option>";
    });
    $("#sel-company").append("<option value=''>请选择客户</option> " + optionsCompany);
    form.render('select');
});