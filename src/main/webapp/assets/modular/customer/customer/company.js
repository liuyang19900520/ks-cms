layui.use(['layer', 'form', 'table', 'admin', 'ax','element'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var nav = layui.element;


    /**
     * 系统管理--角色管理
     */
    var Company = {
        tableId: "companyTable",    //表格id
        condition: {
            customerID: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Company.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'customerID', hide: true, sort: true, title: '公司ID'},
            {field: 'companyName', sort: true, title: '客户公司名称'},
            {field: 'companyAddress', sort: true, title: '客户地址'},
            {field: 'tel', sort: true, title: '联系电话'},
            {field: 'mail', sort: true, title: '邮箱'},
            {field: 'ceoName', sort: true, title: '社长姓名'},
            {field: 'ceoTel', sort: true, title: '社长联系方式'},
            {field: 'ceoMail', sort: true, title: '社长邮箱'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 280}
        ]];
    };

    // /**
    //  * 点击查询按钮
    //  */
    Company.search = function () {
        var queryData = {};
        // queryData['condition'] = $("#condition").val();
        queryData['companyName'] = $("#companyName").val();
        //queryData['customerID'] = Company.condition.customerID;
        table.reload(Company.tableId, {where: queryData});
    };

    /**
     * 弹出录入窗口
     */
    Company.openAddCompany = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加客户信息',
            content: Feng.ctxPath + 'customer/company/company_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Company.tableId);
            }
        });
    };

    /**
     * 弹出窗口
     */
    Company.openAdd1Company = function (data) {

        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加现场信息',
            content: Feng.ctxPath + 'customer/company/company_add_site?customerID=' + data.customerID,
            end: function () {
                var x = {
                    "indexTabs": [{
                        "menuId": "/customer/site",
                        "menuPath": "/customer/site",
                        "menuName": "现场管理"
                    }], "tabPosition": "/customer/site", "formOk": true
                }
                sessionStorage.setItem("tempData", JSON.stringify(x));
                top.location.reload()
            }
        });
    };


    /**
     * 点击编辑角色
     *
     * @param data 点击按钮时候的行数据
     */
    Company.onEditCompany = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改客户信息',
            content: Feng.ctxPath + 'customer/company/company_edit?customerID=' + data.customerID,
            end: function () {
                admin.getTempData('formOk') && table.reload(Company.tableId);
            }
        });
    };

    /**
     * 点击删除角色
     *
     * @param data 点击按钮时候的行数据
     */
    Company.onDeleteCompany = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/customer/company/delete", function () {
                Feng.success("删除成功!");
                table.reload(Company.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("customerID", data.customerID);
            ajax.start();
        };
        Feng.confirm("是否删除客户公司： " + data.companyName + "?", operation);
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Company.tableId,
        url: Feng.ctxPath + 'company/list',
        page: true,
        height: "full-158",
        cellMinWidth: 128,
        cols: Company.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Company.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Company.openAddCompany();
    });

    // 工具条点击事件
    table.on('tool(' + Company.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Company.onEditCompany(data);
        } else if (layEvent === 'delete') {
            Company.onDeleteCompany(data);
        } else if (layEvent === 'add') {
            Company.openAdd1Company(data);
        }
    });


});
