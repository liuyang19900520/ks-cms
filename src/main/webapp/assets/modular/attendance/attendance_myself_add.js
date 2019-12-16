/**
 * 添加我的考勤
 */
var UserInfoDlg = {
    data: {
        employeeId: ""

    }
};

layui.use(['layer', 'form', 'admin', 'laydate', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

  
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

            var queryData = {};
            queryData['currentMonth'] = value
            // table.reload(AttendanceRecord.tableId, {where: queryData});
        }
    });
    
    // //获取用户信息
    var ajax = new $ax(Feng.ctxPath + "/attendance/myself/message");
    var result = ajax.start();
    form.val('attendanceInputForm', result.data);
    
    
     // 表单提交事件
     form.on('submit(btnSubmit)', function (data) {
         var ajax = new $ax(Feng.ctxPath + "/attendance/myself/add", function (data) {
             Feng.success("添加成功！");
    
             //传给上个页面，刷新table用
             admin.putTempData('formOk', true);
    
             //关掉对话框
             admin.closeThisDialog();
         }, function (data) {
             Feng.error("添加成功！" + data.responseJSON.message)
         });
         ajax.setContentType("application/json;charset=UTF-8")
         ajax.setData(JSON.stringify(data.field));
         ajax.start();
     });
});