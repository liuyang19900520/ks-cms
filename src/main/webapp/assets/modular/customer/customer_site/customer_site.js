layui.use(['layer', 'form', 'table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 系统管理--现场管理
     */
    var Customer = {
        tableId: "customer_siteTable",    //表格id
        condition: {
            customersiteName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Customer.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'customerID', hide: true, sort: true, title: '公司ID'},
            {field: 'customersiteID', sort: true, title: '客户现场ID'},
            {field: 'customersiteName', sort: true, title: '客户现场名称'},
            {field: 'customersiteAddress', sort: true, title: '客户现场地址'},
            {field: 'customersiteStation', sort: true, title: '客户现场车站'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

     /**
     * 点击查询按钮
      */
     Customer.search = function () {
        var queryData = {};
        queryData['customersiteName'] = $("#customersiteName").val();
        table.reload( Customer.tableId, {where: queryData});
    };
    /**
     * 弹出录入窗口
     */
    Customer.openAddCustomer = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加客户信息',
            content: Feng.ctxPath + 'customer/site/customersite_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Customer.tableId);
            }
        });
    };



    /**
     * 弹出窗口
     */
    Customer.openAdd1Customer = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加项目信息',
            content: Feng.ctxPath + 'customer/site/customersite_add_project',
            end: function () {
                admin.getTempData('formOk') && table.reload(Customer.tableId);
            }
        });
    };
    /**
     * 点击删除现场按钮
     *
     * @param data 点击按钮时候的行数据
     */
    Customer.onDeleteUser = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/customer/site/delete", function () {
                table.reload( Customer.tableId);
                Feng.success("删除成功!");
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("customerId", data.customerId);
            ajax.start();
        };
        Feng.confirm("是否删除用户" + data.customersiteName + "?", operation);
    };




    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Customer.tableId,
        url: Feng.ctxPath + 'site/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Customer.initColumn()
    });

    // 工具条点击事件
    table.on('tool(' + Customer.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'delete') {
            Customer.onDeleteUser(data);
        } else if (layEvent === 'add'){
            Customer.openAdd1Customer(data);
        }
    });
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Customer.search();
    });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Customer.openAddCustomer();
    });
});
