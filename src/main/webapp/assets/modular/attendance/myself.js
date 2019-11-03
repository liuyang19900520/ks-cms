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
            {type: 'checkbox', LAY_CHECKED: true, hide: true,},
            {field: 'employeeId', sort: true, title: 'ID', hide: true},
            {field: 'workMonth', sort: true, title: '月份'},
            {field: 'employeeNameCN', sort: true, title: '姓名', edit: "text"},
            {field: 'customerSiteName', sort: true, title: '现场名称', edit: "text"},
            {field: 'projectName', sort: true, title: '项目名称', edit: "text"},
            {field: 'companyName', sort: true, title: '客户名称', edit: "text"},
            {field: 'workTime', sort: true, title: '工作时长', edit: "text"},
            {field: 'standardMinTime', sort: true, title: '最低工作时长', edit: "text"},
            {field: 'standardMaxTime', sort: true, title: '加班计算工时', edit: "text"},
            // {
            //     field: 'workTime', sort: true, title: '工作时长', templet: function (d) {
            //         return d.dayPeriod.toFixed(2) + ' h'
            //     }
            // },
        ]];
    };

    var queryData = {};
    var currentMonth = $dateformatter(new Date(), "yyyyMM")
    queryData['currentMonth'] = currentMonth;

    /**
     * 点击查询按钮
     */
    AttendanceRecord.search = function () {
        queryData['currentMonth'] = $("#ipt-current-month").val();
        table.reload(AttendanceRecord.tableId, {where: queryData});
    };



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

        var reDateTime = /^(?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]$/;
        var regExp = new RegExp(reDateTime);
        if (!regExp.test(value)) {
            // alert("日期格式不正确，正确格式为：2014-01-01");
            layer.msg(value + "日期格式不正确，正确格式为：HH:mm");
            return;
        }


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
            queryData['currentMonth'] = $("#ipt-current-month").val();
            table.reload(AttendanceRecord.tableId, {where: queryData});

        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.setContentType("application/json")
        alert(JSON.stringify(checkRows.data));
        ajax.setData(JSON.stringify(checkRows.data));
        ajax.start();

    });

    $('#btnInput').click(function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '考勤录入',
            content: Feng.ctxPath + '/attendance/myself/input',
            end: function () {
                admin.getTempData('formOk') && table.reload(MgrUser.tableId);
            }
        });
    });

});



