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
            {field: 'customerSiteID', sort: true, title: '客户现场ID'},
            {field: 'customerSiteName', sort: true, title: '客户现场名称'},
            {field: 'customerSiteAddress', sort: true, title: '客户现场地址'},
            {field: 'customerSiteStation', sort: true, title: '客户现场车站'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 300}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Customer.search = function () {
        var queryData = {};
        queryData['customerSiteName'] = $("#customerSiteName").val();
        table.reload(Customer.tableId, {where: queryData});
    };
    /**
     * 弹出录入窗口
     */
    Customer.openAddCustomer = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加现场信息',
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
            content: Feng.ctxPath + 'customer/site/customerSite_add_project',
            end: function () {
                admin.getTempData('formOk') && table.reload(Customer.tableId);
            }
        });
    };
    /**
     * 弹出窗口
     */
    Customer.openAddProject = function (data) {

        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加项目信息',
            content: Feng.ctxPath + 'customer/site/customerSite_add_project?customerSiteID=' + data.customerSiteID,
            end: function () {
                location.href="/customer/project";
            }
        });
    };


    /**
     * 点击编辑角色
     *
     * @param data 点击按钮时候的行数据
     */
    Customer.onEditCustomer = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改现场信息',
            content: Feng.ctxPath + 'customer/site/customer_site_edit?customerSiteID=' + data.customerSiteID,
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
                table.reload(Customer.tableId);
                Feng.success("删除成功!");
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("customerSiteID", data.customerSiteID);
            ajax.start();
        };
        Feng.confirm("是否删除项目" + data.customerSiteName + "?", operation);
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
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Customer.search();
    });
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Customer.openAddCustomer();
    });

    // 工具条点击事件
    table.on('tool(' + Customer.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Customer.onEditCustomer(data);
        } else if (layEvent === 'delete') {
            Customer.onDeleteUser(data);
        } else if (layEvent === 'add') {
            Customer.openAddProject(data);
        }
    });
});
