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
    var claimDetail = {
        tableId: "claimDetail",
    };

    /**
     * 初始化表格的列
     */
    claimDetail.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'employeeId', sort: true, title: '员工ID'},
            {field: 'employeeName', sort: true, title: '员工姓名'},
            {field: 'claimDate', sort: true, title: '日期'},
            {field: 'price', sort: true, title: '金额'},
            {field: 'claimType', sort: true, title: '种别'},
            {field: 'remark', sort: true, title: '备注'},
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + claimDetail.tableId,
        url: Feng.ctxPath + 'detail/init?employeeId='+Feng.getUrlParam("employeeId")+'&conditionMonth='+Feng.getUrlParam("conditionDate"),
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: claimDetail.initColumn()
    });

});
