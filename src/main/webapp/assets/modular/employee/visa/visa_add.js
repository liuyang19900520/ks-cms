layui.use(['layer', 'form', 'admin', 'laydate', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    // 渲染时间选择框
    laydate.render({
        elem: '#visaUpdateTime'
    });

    laydate.render({
        elem: '#visaExpireTime'
    });

    //公司信息
    var ajax = new $ax(Feng.ctxPath + "/employee/visa/unregister");
    var result = ajax.start();
    var optionsEmployee = '<option value="">请选择员工</option> ';

    $.each(result, function (key, value) {  //循环遍历后台传过来的json数据
        optionsEmployee += "<option value=\"" + value.employeeSelectValue + "\" >" + value.employeeNameCn + "</option>";
    });
    $("#sel-employee").append(optionsEmployee);
    form.render('select');//select是固定写法 不是选择器


    //公司信息
   // var ajax = new $ax(Feng.ctxPath + "/employee/visa/getType");
    //var result = ajax.start();
   // var optionsVisaType = '<option value="">请选择签证类型</option> ';

  //  $.each(result, function (key, value) {  //循环遍历后台传过来的json数据
        //optionsVisaType += "<option value=\"" + value.visaTypeSelectValue + "\" >" + value.visaType + "</option>";
   // });
   // $("#sel-visaType").append(optionsVisaType);
   // form.render('select');//select是固定写法 不是选择器

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/employee/visa/add", function (data) {
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
    function init() {
        //打开页面时初始化下拉列表
        getVisaType();
    };
    init();
    //获取字典中的报销单分类
    function getVisaType(){
        $.ajax({
            type: 'POST',
            url: Feng.ctxPath + "/employee/visa/getVisaType",
            data: {

            },
            dataType: "json",
            error: function (request) {

            },
            success: function (data) {
                $("#visaType").empty();
                $("#visaType").append("<option value="+0+">---请选择签证类型---</option>");

                $.each(data, function(i){
                    $("#visaType").append("<option value="+data[i].code+">"+data[i].name+"</option>");
                });
                form.render();
            }
        });

    }
});