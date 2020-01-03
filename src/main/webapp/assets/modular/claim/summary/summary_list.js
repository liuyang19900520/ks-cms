layui.use(['layer', 'form', 'table', 'admin', 'ax', 'laydate', 'dateformatter'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var $dateformatter = layui.dateformatter;

    /**
     * 系统管理--消息管理
     */
    var claimSummary = {
        tableId: "claimSummary",
    };

    //执行一个laydate实例
    laydate.render({
        elem: '#ipt-current-month', //指定元素
        type: 'month',
        value: new Date(),
        format: 'yyyy-MM',
        done: function (value, date, endDate) {
            console.log(value); //得到日期生成的值，如：2017-08-18
            console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
            console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
            var queryData = {};
            queryData['conditionDate'] = value
            table.reload(claimSummary.tableId, {where: queryData});
        }
    });

    /**
     * 初始化表格的列
     */
    claimSummary.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'employeeId', sort: true, title: '员工ID'},
            {field: 'employeeName', sort: true, title: '员工姓名'},
            {field: 'claimMonth', sort: true, title: '月份'},
            {field: 'claimAccount', sort: true, title: '金额'},
            {align: 'center', toolbar: '#tableBar', title: '操作'},
            {field: 'claimStatus', sort: true, title: '状态', templet: '#statusTpl'}
        ]];
    };

    var queryData = {};
    var conditionDate = $dateformatter(new Date(), "yyyy-MM")
    queryData['conditionDate'] = conditionDate;

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + claimSummary.tableId,
        url: Feng.ctxPath + 'summary/list',
        page: true,
        height: "full-158",
        where: queryData,
        cellMinWidth: 100,
        cols: claimSummary.initColumn()
    });


    /**
     * 点击查询按钮
     */
    claimSummary.search = function () {
        queryData['conditionDate'] = $("#ipt-current-month").val();
        table.reload(claimSummary.tableId, {where: queryData});
    };

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        claimSummary.search();
    });

    $('#btnAudit').click(function () {
        claimSummary.auditAll();
    });

    // 工具条点击事件
    table.on('tool(' + claimSummary.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'detail') {
            claimSummary.onDetail(data);
        }
    });

    claimSummary.onDetail = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '报销详情',
            area: ['800px', '500px'],
            content: Feng.ctxPath + '/claim/summary/detail?employeeId=' + data.employeeId + '&conditionDate=' + data.claimMonth,
            end: function () {
                admin.getTempData('formOk') && table.reload(claimSummary.tableId);
            }
        });
    };

    form.on('switch(claimStatus)', function (obj) {
        console.log($(obj.elem).parent().parent().parent().children().eq(3).children().text())
        var empId = obj.elem.value;
        var claimMonth = $(obj.elem).parent().parent().parent().children().eq(3).children().text();
        var checked = obj.elem.checked ? true : false;
        claimSummary.changeUserStatus(empId, claimMonth, checked);
    });

    claimSummary.changeUserStatus = function (employeeId, claimMonth, checked) {
        if (checked) {
            var statusCode = 0;
            var ajax = new $ax(Feng.ctxPath + "/claim/summary/status/" + statusCode, function (data) {
                Feng.success("该月报销内容已确认!");
            }, function (data) {
                Feng.error("该月报销内容确认失败!");
                table.reload(claimSummary.tableId);
            });
            ajax.set("employeeId", employeeId);
            ajax.set("claimMonth", claimMonth);
            ajax.start();
        } else {
            var statusCode = 1;
            var ajax = new $ax(Feng.ctxPath + "/claim/summary/status/" + statusCode, function (data) {
                Feng.success("该月报销内容已驳回");
            }, function (data) {
                Feng.error("该月报销内容驳回失败!" + data.responseJSON.message + "!");
                table.reload(claimSummary.tableId);
            });
            ajax.set("employeeId", employeeId);
            ajax.set("claimMonth", claimMonth);
            ajax.start();
        }
    };


    claimSummary.auditAll = function () {
        var checkRows = table.checkStatus(claimSummary.tableId);
        if (checkRows.data.length == 0) {
            layer.alert("请选择要进行确认的数据")
        }
        else {
            layer.confirm("确认更改所选项的状态", function () {
                layer.close(layer.index);
                checkRows.data.forEach(function (item) {
                    claimSummary.changeUserStatus(item.employeeId, item.claimMonth, true);
                })
                table.reload(claimSummary.tableId);
            })
        }
    }


});
