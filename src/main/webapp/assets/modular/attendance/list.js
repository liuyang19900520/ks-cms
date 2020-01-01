layui.use(['table', 'admin', 'ax', 'laydate', 'dateformatter', 'form', 'layer'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var $dateformatter = layui.dateformatter;
    var form = layui.form;
    var layer = layui.layer;

    /**
     * 系统管理--部门管理
     */
    var AttendanceRecord = {
        tableId: "attendanceListTable",
        condition: {
            currentMonthDate: ""
        }
    };

    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#ipt-current-month', //指定元素
        type: 'month',
        value: new Date(),
        format: 'yyyyMM',
        done: function (value, date, endDate) {
            console.log(value); //得到日期生成的值，如：2017-08-18
            console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
            console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。


            queryData['currentMonthDate'] = value
            queryData['empId'] = null
            queryData['status'] = false

            AttendanceRecord.search();

        }
    });

    /**
     * 初始化表格的列
     */
    AttendanceRecord.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'workMonth', sort: true, title: '月份'},
            {field: 'employeeId', sort: true, title: '员工号'},
            {field: 'employeeNameCN', sort: true, title: '姓名', edit: "text"},
            {field: 'workTime', sort: true, title: '工作时长', edit: "text"},
            {field: 'projectName', sort: true, title: '项目名称', edit: "text"},
            {field: 'customerSiteName', sort: true, title: '现场名称', edit: "text"},
            {field: 'companyName', sort: true, title: '客户名称', edit: "text"},
            {field: 'status', sort: true, title: '状态', templet: '#statusTpl'}
        ]];
    };

    var queryData = {};
    queryData['empId'] = null
    queryData['status'] = false


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {

        AttendanceRecord.search();
    });


    /**
     * 点击搜索按钮
     */
    AttendanceRecord.search = function () {
        queryData['currentMonthDate'] = $('#ipt-current-month').val();
        queryData['empId'] = null
        /*queryData['status'] = false*/
        var tableResult = table.render({
            elem: '#' + AttendanceRecord.tableId,
            url: Feng.ctxPath + '/attendance/list/checklist',
            where: queryData,
            height: "full-158",
            cols: AttendanceRecord.initColumn()
        });
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + AttendanceRecord.tableId,
        url: Feng.ctxPath + '/attendance/list/list',
        where: queryData,
        height: "full-158",
        cols: AttendanceRecord.initColumn()
    });


    function init() {
        //打开页面时初始化下拉列表
        getClaimType();
    };
    init();

    //获取姓名
    function getClaimType() {
        $.ajax({
            type: 'POST',
            url: Feng.ctxPath + "/attendance/list/getEmployeeType",
            data: {},
            dataType: "json",
            error: function (request) {

            },
            success: function (data) {
                $("#claimType").empty();
                $("#claimType").append("<option value=" + 0 + ">---请选择员工---</option>");

                $.each(data, function (i) {
                    $("#claimType").append("<option value=" + data[i].code + ">" + data[i].name + "</option>");
                });
                form.render();
            }
        });

    }

    /**
     * 修改"确认"状态
     *
     * @param employeeId 员工id
     * @param checked 是否选中（true,false），选中就是已确认，未选中就是未确认
     */
    AttendanceRecord.changeUserStatus = function (employeeId, checked,workMonth) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/attendance/list/confirm", function (data) {
                Feng.success("确认成功!");
            }, function (data) {
                Feng.error("确认失败!" + data.responseJSON.message + "!");
                table.reload(AttendanceRecord.tableId);
            });
            ajax.set("employeeId", employeeId);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/attendance/list/unconfirm", function (data) {
                Feng.success("取消确认成功!");
            }, function (data) {
                Feng.error("取消确认失败!");
                table.reload(AttendanceRecord.tableId);
            });
            ajax.set("employeeId", employeeId);
            ajax.start();
        }
    };

    // 修改user状态
    form.on('switch(attendanceStatus)', function (obj) {
        var employeeId = obj.elem.value;
        var checked = obj.elem.checked ? true : false;
        var workMonth = obj.elem.name;
        AttendanceRecord.changeUserStatus(employeeId, checked,workMonth);
    });

    /**
     * 一键审批
     */
    AttendanceRecord.multiConfirm = function () {
        var checkRows = table.checkStatus(AttendanceRecord.tableId);
        if (checkRows.data.length == 0) {
            layer.alert("请选择要进行确认的数据")
        }
        else {
            layer.confirm("确认更改所选项的状态", function () {
                layer.close(layer.index);
                checkRows.data.forEach(function (item) {
                    AttendanceRecord.changeUserStatus(item.employeeId, true,item.workMonth);
                })
                table.reload(AttendanceRecord.tableId);
            })
        }
    }


    //点击“批量确认”按钮，批量确认员工考勤状态
    $("#multiConfirm").click(function () {
        AttendanceRecord.multiConfirm();
    })
});



