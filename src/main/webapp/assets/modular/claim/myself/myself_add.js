layui.use(['layer', 'form', 'admin', 'laydate', 'ax',  'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;
    var upload = layui.upload;
    
    // 让当前iframe弹层高度适应
    admin.iframeAuto();
    // 渲染时间选择框
    laydate.render({
        elem: '#claimDate',
        trigger: 'click'
        	
    });
    
    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/claim/myself/add", function (data) {
            Feng.success("添加成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });
    
    function init() { 
    	//打开页面时初始化下拉列表
    	getClaimType();
        }; 
        init(); 
        //获取字典中的报销单分类
        function getClaimType(){
        	$.ajax({
        		type: 'POST',
        		url: Feng.ctxPath + "/claim/myself/getClaimType",
        		data: {
        			
        		},
        		dataType: "json",
        		error: function (request) {

        		},
        		success: function (data) {
        			$("#claimType").empty();
        			$("#claimType").append("<option value="+0+">---请选择---</option>");
        			
        			$.each(data, function(i){
        				$("#claimType").append("<option value="+data[i].code+">"+data[i].name+"</option>");
        			});
        			form.render();
        		}
        	});
        	
         }
});