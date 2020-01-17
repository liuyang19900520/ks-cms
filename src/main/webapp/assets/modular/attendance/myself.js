layui.use(['table', 'admin', 'ax', 'laydate', 'dateformatter'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var $dateformatter = layui.dateformatter;

    /**
     * 系统管理--部门管理
     */
    var AttendanceRecord = {
        tableId: "attendanceMyselfTable",
        condition: {
            currentMonth: ""
        }
    };

    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#ipt-current-month', //指定元素
        type: 'month',
        format: 'yyyyMM',
        done: function (value, date, endDate) {
            console.log(value); //得到日期生成的值，如：2017-08-18
            console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
            console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
        }
    });

    /**
     * 初始化表格的列
     */
    AttendanceRecord.initColumn = function () {
        return [[
            {type: 'checkbox', LAY_CHECKED: true, hide: true,},
            {field: 'employeeId', sort: true, title: 'ID', hide: true},
            {field: 'workMonth', sort: true, title: '月份'},
            {field: 'employeeNameCN', sort: true, title: '姓名'},
            {field: 'customerSiteName', sort: true, title: '现场名称'},
            {field: 'projectName', sort: true, title: '项目名称'},
            {field: 'companyName', sort: true, title: '客户名称'},
            {field: 'workTime', sort: true, title: '工作时长'},
            {field: 'standardMinTime', sort: true, title: '最低工作时长'},
            {field: 'standardMaxTime', sort: true, title: '加班计算工时'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 150}
        ]];
    };

    var queryData = {};
    //var currentMonth = $dateformatter(new Date(), "yyyyMM")
    queryData['currentMonth'] = null;

    /**
     * 点击搜索按钮
     */
    AttendanceRecord.search = function () {
    var queryData = {};
        queryData['currentMonth'] = $("#ipt-current-month").val();
        queryData['employeeId'] = AttendanceRecord.condition.employeeId;
        table.reload(AttendanceRecord.tableId, {where: queryData,page:true,limit:2});
    };



    // 渲染表格
   /* var tableResult =*/
    table.render({
        elem: '#' + AttendanceRecord.tableId,
        url: Feng.ctxPath + '/attendance/myself/list',
        where: queryData,
        height: "full-158",
        page:true,
        limit:2,
        limits:[2,4,6,8],
        cols: AttendanceRecord.initColumn()
    });

    //监听单元格编辑
    // table.on('edit(attendanceMyselfTable)', function (obj) {
    //     var value = obj.value //得到修改后的值
    //         , data = obj.data //得到所在行所有键值
    //         , field = obj.field; //得到字段
    //
    //     var reDateTime = /^(?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]$/;
    //     var regExp = new RegExp(reDateTime);
    //     if (!regExp.test(value)) {
    //         // alert("日期格式不正确，正确格式为：2014-01-01");
    //         layer.msg(value + "日期格式不正确，正确格式为：HH:mm");
    //         return;
    //     }
    // });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AttendanceRecord.search();
    });

 // 工具条点击事件
    table.on('tool(' + AttendanceRecord.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AttendanceRecord.onEditAttendance(data);
        } else if (layEvent === 'delete') {
            AttendanceRecord.onDeleteAttendance(data);
        } 
    });


/**
     * 点击编辑我的考勤
     *
     * @param data 点击按钮时候的行数据
     */
    AttendanceRecord.onEditAttendance = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑考勤',
            content: Feng.ctxPath + '/attendance/myself/attendance_edit?workMonth='+ data.workMonth,
            end: function () {
                admin.getTempData('formOk') && table.reload(AttendanceRecord.tableId,{page:true,
                    limit:2});
            }
        });
    };
       
    
       /**
     * 点击删除考勤
     *
     * @param data 点击按钮时候的行数据
     */
    AttendanceRecord.onDeleteAttendance = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + '/attendance/myself/attendance_delete?workMonth=' + data.workMonth, function () {
                Feng.success("删除成功!");
                table.reload(data.workMonth,{page:true,
                    limit:2,});
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("employeeId", data.employeeId);
            ajax.start();
            window.location.reload();
        };
        
        Feng.confirm("是否删除考勤 " + data.workMonth + "?", operation);
    };
    
//点击添加考勤
    $('#btnInput').click(function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '考勤录入',
            content: Feng.ctxPath + '/attendance/myself/input',
            end: function () {
                admin.getTempData('formOk') && table.reload({page:true,
                    limit:2});
                window.location.reload();
            }
        });
    });

});



