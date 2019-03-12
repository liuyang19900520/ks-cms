### 程序入口
整个程序的index我们先放在一边，首先关心我们自己能够写代码的地方。

首先我建议在业务层的DeptController的index()方法上打一个断点
``` java
    @RequestMapping("")
    public String index() {
        return PREFIX + "dept.html";
    }
```
这样我们发现直接返给我们一个String，这个String可以理解为通过SpringMVC映射到我们的路径上（或者说前端的页面你上），
建议只记住就可以，配置映射的根目录在application.yml中配置了
``` xml
spring:
  profiles:
    active: @spring.active@
  mvc:
    view:
      prefix: /pages
```
### 看看前端
前端上，项目使用了Beetl模板引擎（http://ibeetl.com/）和layui（https://www.layui.com/）

首先了解一下html结构，可以看出dept.html只是负责侧边栏外剩余的部分。并且不包含上部分的title栏（通知、用户名等内容。）
备注：我们先了解最简单的业务，随着系统学习的深入，再来看看是怎么设计的。

我们看不去看导入的内容，就dept的业务
分为了headertitle和主体部分，主体部分进行了28分，右侧上下栏目分层，有控制栏和列表部分。我们主要来看列表部分。
```html

<!--树结构-->
 <div class="layui-card-body mini-bar">
    <div class="ztree" id="deptTree"></div>
</div>
<!--表格结构-->
<table class="layui-table" id="deptTable" lay-filter="deptTable"></table>
```

在这里我们发现id=deptTree和id=deptTable，找到dept.js文件。位置在assets/modular/system/dept/dept.js
我前端开发的经验不是很丰富，所以我们猜测理解一下代码
```js
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var $ZTree = layui.ztree;
```
上述内容主要是导入js模块，或者理解为需要使用的功能块，中间的声明的方法先不看，之间看到中间有一行
```js
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Dept.tableId,
        url: Feng.ctxPath + '/dept/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Dept.initColumn()
    });

    //初始化左侧部门树
    var ztree = new $ZTree("deptTree", "/dept/tree");
    ztree.bindOnClick(Dept.onClickDept);
    ztree.init();
```
上述代码完成了2个操作，第一个操作是table的渲染，第二个方法是ztree的初始化。
这里我觉得没有必要去看table和$ZTree的实现了。如果一定想了解一下，我们可以去看一下这两个组件的js代码（我前端经验比较少，所以也是猜着看）

很显然，ztree是自定义的组件，在common.js中进行了声明。源码的位置在assets/common/module/ztree/ztree-object.js
我们也知道这2个参数一个是在html树控件的id，另一个是请求的地址，也就是和服务器进行交互的路由的路径。
看一下ztree插件代码中有一段
```js
    /**
     * 初始化ztree
     */
    init : function() {
        var zNodeSeting = null;
        if(this.settings != null){
            zNodeSeting = this.settings;
        }else{
            zNodeSeting = this.initSetting();
        }
        var zNodes = this.loadNodes();
        $.fn.zTree.init($("#" + this.id), zNodeSeting, zNodes);
    }
```
集中loadNodes()这个方法就是在代码中可以找到就是一段ajax请求，如果培训过都会了解ajax是做异步请求用的，或者简单理解为发送请求，获得响应。
我们来看一下请求的路径。
```js
 /**
         * 加载节点
         */
        loadNodes : function() {
            var zNodes = null;
            var ajax = new $ax(Feng.ctxPath + this.url, function(data) {
                zNodes = data;
            }, function(data) {
                Feng.error("加载ztree信息失败!");
            });
            ajax.start();
            return zNodes;
        }

```
这个Feng.ctxPath是我们请求的根目录，回过头来看我们的dept.html.他在最开始引入了_container.html，其中添加了ctxPath
```js
@/* 加入contextPath属性和session超时的配置 */
<script type="text/javascript">
    var Feng = {
        ctxPath: "",
        addCtx: function (ctx) {
            if (this.ctxPath === "") {
                this.ctxPath = ctx;
            }
        }
    };
    Feng.addCtx("${ctxPath}");
</script>

```
在freemarker的语法中，${ctxPath}是可以获得项目的相对路径，可以理解为请求的前缀。这样我们就能得到我们要请求的路径了。
准备回到controller中去寻找一下
``` java
    /**
     * 获取部门的tree列表，ztree格式
     *
     * @author fengshuonan
     * @Date 2018/12/23 4:56 PM
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
        List<ZTreeNode> tree = this.deptService.tree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }

```
这下就回到了spring开发习惯的 controller service dao的结构中了。










