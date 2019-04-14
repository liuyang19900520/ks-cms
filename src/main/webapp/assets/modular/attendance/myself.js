layui.use(['table', 'admin', 'ax', 'laydate', 'dateformatter'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var $dateformatter = layui.dateformatter;

    /**
     * 系统管理--部门管理
     */
    var AttendanceRecord = {
        tableId: "attendanceMyselfTable",
        condition: {
            currentMonth: ""
        }
    };

    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#ipt-current-month', //指定元素
        type: 'month',
        value: new Date(),
        format: 'yyyyMM',
        done: function (value, date, endDate) {
            console.log(value); //得到日期生成的值，如：2017-08-18
            console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
            console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。

            var queryData = {};
            queryData['currentMonth'] = value
            table.reload(AttendanceRecord.tableId, {where: queryData});

        }
    });

    /**
     * 初始化表格的列
     */
    AttendanceRecord.initColumn = function () {
        return [[
            {type: 'checkbox', LAY_CHECKED: true, hidden: true,},
            {field: 'id', sort: true, title: 'ID'},
            {field: 'workDate', sort: true, title: '日期'},
            {field: 'startTime', sort: true, title: '上班时间', edit: "text"},
            {field: 'endTime', sort: true, title: '下班时间', edit: "text"},
            {
                field: 'dayPeriod', sort: true, title: '工作时长', templet: function (d) {
                    return d.dayPeriod.toFixed(2) + ' h'
                }
            },
        ]];
    };

    /**
     * 点击查询按钮
     */
    AttendanceRecord.search = function () {
        var queryData = {};
        queryData['currentMonth'] = $("#ipt-current-month").val();
        table.reload(AttendanceRecord.tableId, {where: queryData});
    };

    var queryData = {};
    var currentMonth = $dateformatter(new Date(), "yyyyMM")
    queryData['currentMonth'] = currentMonth;

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + AttendanceRecord.tableId,
        url: Feng.ctxPath + '/attendance/myself/list',
        where: queryData,
        height: "full-158",
        cols: AttendanceRecord.initColumn()
    });

    //监听单元格编辑
    table.on('edit(attendanceMyselfTable)', function (obj) {
        var value = obj.value //得到修改后的值
            , data = obj.data //得到所在行所有键值
            , field = obj.field; //得到字段
        layer.msg('[ID: ' + data.id + '] ' + field + ' 字段更改为：' + value);
    });


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AttendanceRecord.search();
    });


    // 保存
    $('#btnSave').click(function () {
        var checkRows = table.checkStatus(AttendanceRecord.tableId);

        var ajax = new $ax(Feng.ctxPath + "/attendance/myself/save", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.setContentType("application/json")
        ajax.setData(JSON.stringify(checkRows.data));
        ajax.start();

    });

});



