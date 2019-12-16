layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取用户信息
    var ajax = new $ax(Feng.ctxPath + "/customer/project/getCompanies");
    var result = ajax.start();

    var optionsCompany;
    $.each(result, function (key, value) {  //循环遍历后台传过来的json数据
        optionsCompany += "<option value=\"" + value.customerID + "\" >" + value.companyName + "</option>";
    });
    $("#sel-company").append("<option value=''>请选择客户</option> " + optionsCompany);

    form.on('select(sel-company)', function(data){
        var sitePath = Feng.ctxPath + "/customer/project/customer-site/" + $("#sel-company").val();
        var ajaxSite = new $ax(sitePath);
        var sites = ajaxSite.start();
        alert(JSON.stringify(sites))
        var optionsSites

        $.each(sites, function (key, value) {  //循环遍历后台传过来的json数据
            optionsSites += "<option value=\"" + value.customerSiteID + "\" >" + value.customerSiteName + "</option>";
        });
        $("#sel-site").html("<option value=''>请选择现场</option> " + optionsSites); //获得要赋值的select的id，进行赋值

        form.render('select');//select是固定写法 不是选择器

    });
    form.render('select');//select是固定写法 不是选择器
});