layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();



    $.ajax({
        type: 'POST',
        url: Feng.ctxPath + "/employee/visa/getVisaType",
        data: {},
        dataType: "json",
        error: function (request) {
        },
        success: function (data) {
            $("#sel-employee").empty();
            $("#sel-employee").append("<option value=" + 0 + ">---请选择---</option>");
            $.each(data, function (i) {
                $("#sel-employee").append("<option value=" + data[i].employeeSelectValue  + ">" + data[i].employeeNameCn + "</option>");
            });
            form.render();

            //获取部门信息
            var ajax = new $ax(Feng.ctxPath + "/employee/visa/detail/" + Feng.getUrlParam("employeeId"));
            var result = ajax.start();
            form.val('employeeForm', result);

        }
    });
    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/employee/visa/visaEdit", function (data) {
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