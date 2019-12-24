layui.use(['layer', 'form', 'table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 系统管理--角色管理
     */
    var infoMgr = {
        tableId: "employeeInfoTable",    //表格id
        condition: {
            employeeNameCn: ""
        }
    };

    /**
     * 初始化表格的列
     */
    infoMgr.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'userId', sort: true, hidden:true},
            {field: 'employeeId', sort: true, title: '员工号'},
            {field: 'employeeNameCn', sort: true, title: '姓名'},
            {field: 'companyName', sort: true, title: '所属客户'},
            {field: 'customerSiteName', sort: true, title: '所在现场'},
            {field: 'projectName', sort: true, title: '所在项目'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

    /**
     * 点击搜索按钮
     */
    infoMgr.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(infoMgr.tableId, {where: queryData});
    };

    /**
     * 点击个人信息按钮
     *
     * @param data 点击按钮时候的行数据
     */
    infoMgr.onOpenDetail = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '员工个人信息',
            content: Feng.ctxPath + '/info/open_detail?userId=' + data.userId,
            end: function () {
                admin.getTempData('formOk') && table.reload(infoMgr.tableId);
            }
        });
    };
    /**
     * 点击分配项目按钮
     *
     * @param data 点击按钮时候的行数据
     */
    infoMgr.onProjAssign = function (data) {
        layer.open({
            type: 2,
            title: '项目分配',
            area: ['300px', '400px'],
            content: Feng.ctxPath + 'info/project/assign?userId=' + data.userId,
            end: function () {
                table.reload(infoMgr.tableId);
            }
        });
    };
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + infoMgr.tableId,
        url: Feng.ctxPath + 'info/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: infoMgr.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        infoMgr.search();
    });


    // 工具条点击事件
    table.on('tool(' + infoMgr.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'detail') {
            infoMgr.onOpenDetail(data);
        } else if (layEvent === 'projAssign') {
            infoMgr.onProjAssign(data);
        }
    });
});
