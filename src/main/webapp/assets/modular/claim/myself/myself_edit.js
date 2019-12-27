layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取字典中的报销单分类

    $.ajax({
        type: 'POST',
        url: Feng.ctxPath + "/claim/myself/getClaimType",
        data: {},
        dataType: "json",
        error: function (request) {
        },
        success: function (data) {
            $("#claimType").empty();
            $("#claimType").append("<option value=" + 0 + ">---请选择---</option>");
            $.each(data, function (i) {
                $("#claimType").append("<option value=" + data[i].code + ">" + data[i].name + "</option>");
            });
            form.render();
            //获取部门信息
            var ajax = new $ax(Feng.ctxPath + "/claim/myself/detail/" + Feng.getUrlParam("claimId"));
            var result = ajax.start();
            form.val('claimForm', result);
        }
    });





    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/claim/myself/myselfEdit", function (data) {
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