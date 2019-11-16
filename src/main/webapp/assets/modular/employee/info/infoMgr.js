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
        	customerID: ""
        }
    };

    /**
     * 初始化表格的列
     */
    infoMgr.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'customerID', hide: true, sort: true, title: '员工号'},
            {field: 'companyName', sort: true, title: '姓名'},
            {field: 'companyAddress', sort: true, title: '所属客户'},
            {field: 'tel', sort: true, title: '所在现场'},
            {field: 'mail', sort: true, title: '所在项目'},
            {field: 'ceoName', sort: true, title: '个人信息'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };

//    /**
//     * 点击查询按钮
//     */
//    Company.search = function () {
//        var queryData = {};
//        queryData['condition'] = $("#condition").val();
//        //queryData['customerID'] = Company.condition.customerID;
//        table.reload(Company.tableId, {where: queryData});
//    };

//    /**
//     * 弹出录入窗口
//     */
//    Company.openAddCompany = function () {
//        admin.putTempData('formOk', false);
//        top.layui.admin.open({
//            type: 2,
//            title: '添加客户信息',
//            content: Feng.ctxPath + 'customer/company/company_add',
//            end: function () {
//                admin.getTempData('formOk') && table.reload(Company.tableId);
//            }
//        });
//    };

//    /**
//     * 点击编辑角色
//     *
//     * @param data 点击按钮时候的行数据
//     */
//    Company.onEditCompany = function (data) {
//        admin.putTempData('formOk', false);
//        top.layui.admin.open({
//            type: 2,
//            title: '修改客户信息',
//            content: Feng.ctxPath + 'customer/company/company_edit?customerID=' + data.customerID,
//            end: function () {
//                admin.getTempData('formOk') && table.reload(Company.tableId);
//            }
//        });
//    };

//    /**
//     * 点击删除角色
//     *
//     * @param data 点击按钮时候的行数据
//     */
//    Company.onDeleteCompany = function (data) {
//        var operation = function () {
//            var ajax = new $ax(Feng.ctxPath + "/customer/company/delete", function () {
//                Feng.success("删除成功!");
//                table.reload(Company.tableId);
//            }, function (data) {
//                Feng.error("删除失败!" + data.responseJSON.message + "!");
//            });
//            ajax.set("customerID", data.customerID);
//            ajax.start();
//        };
//        Feng.confirm("是否删除客户公司： " + data.companyName + "?", operation);
//    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + infoMgr.tableId,
        url: Feng.ctxPath + 'employee/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: infoMgr.initColumn()
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
        }
    });
});
