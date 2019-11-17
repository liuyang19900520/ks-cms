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
    var myClaim = {
        tableId: "myClaimTable",    //表格id
        condition: {
        	currentMonth: ""
        }
    };
    
    
  //执行一个laydate实例
    var laydate = layui.laydate;
    
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
            table.reload(myClaim.tableId, {where: queryData});

        }
    });
    /**
     * 初始化表格的列
     */
    myClaim.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'claimId', hide: true, sort: true, title: '报销单ID'},
            {field: 'employeeId', hide: true, sort: true, title: '员工ID'},
            {field: 'claimDate', sort: true, title: '报销日期'},
            {field: 'claimType', sort: true, title: '报销类型'},
            {field: 'price', sort: true, title: '报销金额'},
            {field: 'claimStatus', sort: true, title: '状态'},
            {field: 'picUrl', sort: true, title: '图片URL'},
            {field: 'remark', sort: true, title: '备注'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}
        ]];
    };
    
    var queryData = {};
    var currentMonth = $dateformatter(new Date(), "yyyyMM")
    queryData['currentMonth'] = currentMonth;
    
    /**
     * 点击查询按钮
     */
    myClaim.search = function () {
        queryData['currentMonth']  = $("#ipt-current-month").val();
        table.reload(myClaim.tableId, {where: queryData});
    };
    
    
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + myClaim.tableId,
        url: Feng.ctxPath + 'myself/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: myClaim.initColumn()
    });

    /**
     * 添加按钮点击弹出事件
     */
    $('#btnAdd').click(function () {
    	myClaim.openAddMyClaim();
    });
    /**
     * 弹出报销单录入窗口
     */
    myClaim.openAddMyClaim = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加我的报销单信息',
            content: Feng.ctxPath + 'claim/myself/myself_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(myClaim.tableId);
            }
        });
    };
    
    /**
     * 点击删除报销单
     *
     * @param data 点击按钮时候的行数据
     */
    myClaim.onDeleteClaim = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/claim/myself/delete", function () {
                Feng.success("删除成功!");
                table.reload(myClaim.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("claimId", data.claimId);
            ajax.start();
        };
        Feng.confirm("是否删除此报销单？ " , operation);
    };
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
    	myClaim.search();
    });
    
    // 工具条点击事件
    table.on('tool(' + myClaim.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
        	myClaim.onEditClaim(data);
        } else if (layEvent === 'delete') {
        	myClaim.onDeleteClaim(data);
        }
    });
    /**
     * 点击编辑角色
     *
     * @param data 点击按钮时候的行数据
     */
    myClaim.onEditClaim = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改客户信息',
            content: Feng.ctxPath + 'claim/myself/myself_edit?claimId=' + data.claimId,
            end: function () {
                admin.getTempData('formOk') && table.reload(myClaim.tableId);
            }
        });
    };
});
