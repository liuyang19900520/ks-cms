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
    var visa = {
        tableId: "employeeVisaTable",    //表格id
        condition: {
            employeeId: ""
        }
    };

    /**
     * 初始化表格的列
     */
    visa.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'employeeId', hide: true, sort: true, title: 'id'},
            {field: 'employeeNameCn', sort: true, title: '姓名'},
            {field: 'visaType', sort: true, title: '签证类型'},
            {field: 'visaUpdateTime', sort: true, title: '签证更新时间'},
            {field: 'visaExpireTime', sort: true, title: '签证到期时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + visa.tableId,
        url: Feng.ctxPath + 'visa/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: visa.initColumn()
    });

    /**
     * 点击搜索按钮
     */
    visa.search = function () {
        var queryData = {};
        queryData['employeeNameCn'] = $("#employeeNameCn").val();
        table.reload(visa.tableId, {where: queryData});
    };
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        visa.search();
    });
    /**
     * 弹出录入窗口
     */
    visa.openAddCompany = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加签证信息',
            content: Feng.ctxPath + 'employee/visa/visa_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(visa.tableId);
            }
        });
    };

     /**
         * 点击删除现场按钮
         *
         * @param data 点击按钮时候的行数据
         */
        visa.onDeleteUser = function (data) {
            var operation = function () {
                var ajax = new $ax(Feng.ctxPath + "/employee/visa/delete", function () {
                    table.reload(visa.tableId);
                    Feng.success("删除成功!");
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                });
                ajax.set("employeeId", data.employeeId);
                ajax.start();
            };
            Feng.confirm("是否删除项目" + data.employeeNameCn + "?", operation);
        };

        /**
             * 点击编辑角色
             *
             * @param data 点击按钮时候的行数据
             */
            visa.onEditVisa = function (data) {
                admin.putTempData('formOk', false);
                top.layui.admin.open({
                    type: 2,
                    title: '修改员工签证信息',
                    content: Feng.ctxPath + 'employee/visa/visa_edit?employeeId=' + data.employeeId,
                    end: function () {
                        admin.getTempData('formOk') && table.reload(visa.tableId);
                    }
                });
            };

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        visa.openAddCompany();
    });
    // 工具条点击事件
        table.on('tool(' + visa.tableId + ')', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;

            if (layEvent === 'edit') {
                visa.onEditVisa(data);
            } else if (layEvent === 'delete') {
                visa.onDeleteUser(data);
            }
        });
});
