layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 系统管理--部门管理
     */
    var AttendanceRecord = {
        tableId: "attendanceMyselfTable",
        condition: {
            deptId: "",
            currentMonth: ""
        }
    };

    /**
     * 初始化表格的列
     */
    AttendanceRecord.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'userId', hide: true, sort: true, title: 'ID'},
            {field: 'userName', sort: true, title: '姓名'},
            {field: 'workDate', sort: true, title: '日期'},
            {field: 'startTime', sort: true, title: '上班时间'},
            {field: 'endTime', sort: true, title: '下班时间'},
        ]];
    };

    /**
     * 点击查询按钮
     */
    AttendanceRecord.search = function () {
        var queryData = {};
        queryData['condition'] = $("#name").val();
        queryData['deptId'] = AttendanceRecord.condition.deptId;
        table.reload(AttendanceRecord.tableId, {where: queryData});
    };

    var queryData = {};
    queryData['userId'] = 1
    queryData['currentMonth'] = "201903";

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + AttendanceRecord.tableId,
        url: Feng.ctxPath + '/attendance/myself/list',
        where: queryData,
        height: "full-158",
        cellMinWidth: 100,
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
