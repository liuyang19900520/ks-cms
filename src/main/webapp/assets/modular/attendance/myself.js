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
            {type: 'checkbox'},
            {field: 'userId', hide: true, sort: true, title: 'ID'},
            {field: 'workDate', sort: true, title: '日期'},
            {field: 'startTime', sort: true, title: '上班时间'},
            {field: 'endTime', sort: true, title: '下班时间'},
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


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AttendanceRecord.search();
    });


    // 导出excel
    $('#btnExp').click(function () {
        AttendanceRecord.exportExcel();
    });

});



