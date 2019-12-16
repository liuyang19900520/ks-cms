layui.use(['layer', 'form', 'table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 系统管理--项目管理
     */
    var Project = {
        tableId: "projectTable",    //表格id
        condition: {
            projectName: ""
        }
    };
    /**
     * 初始化表格的列
     */
    Project.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'projectId', hide: true, sort: true, title: '项目id'},
            {field: 'customerSiteId', hide: true, sort: true, title: '现场id'},
            {field: 'projectName', sort: true, title: '项目名称'},
            {field: 'projectProcess', sort: true, title: '项目情况'},
            {field: 'projectTech', sort: true, title: '项目技术'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 280}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Project.search = function () {
        var queryData = {};
        queryData['projectName'] = $("#projectName").val();
        table.reload( Project.tableId, {where: queryData});
    };
    /**
     * 弹出录入窗口
     */
   Project.openAddProject = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加项目信息',
            content: Feng.ctxPath + 'customer/project/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Project.tableId);
            }
        });
    };

    /**
     * 点击编辑角色
     *
     * @param data 点击按钮时候的行数据
     */
    Project.onEditProject = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改项目信息',
            content: Feng.ctxPath + 'customer/project/project_edit?projectId=' + data.projectId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Project.tableId);
            }
        });
    };

    /**
     * 点击删除角色
     *
     * @param data 点击按钮时候的行数据
     */
    Project.onDeleteProject = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/customer/project/delete", function () {
                Feng.success("删除成功!");
                table.reload(Project.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("projectId", data.projectId);
            ajax.start();
        };
        Feng.confirm("是否删除项目： " + data.projectName, operation);
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Project.tableId,
        url: Feng.ctxPath + 'project/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Project.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Project.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Project.openAddProject();
    });

    // 工具条点击事件
    table.on('tool(' + Project.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Project.onEditProject(data);
        } else if (layEvent === 'delete') {
            Project.onDeleteProject(data);
        }
    });

});
