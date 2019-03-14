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
/* 加入contextPath属性和session超时的配置 */
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

### 回到java
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
为了方便理解，我们还是再看一下这个常规的spring工程的结构
DeptService是实现类，它继承了mybatis-plus的父类实现了其接口，我找到了其文档如下(https://mp.baomidou.com/guide/crud-interface.html#service-crud-%E6%8E%A5%E5%8F%A3)
> 通用 Service CRUD 封装IService接口，进一步封装 CRUD 采用 get 查询单行 remove 删除 list 查询集合 page 分页 前缀命名方式区分 Mapper 层避免混淆，
> 泛型 T 为任意实体对象
> 建议如果存在自定义通用 Service 方法的可能，请创建自己的 IBaseService 继承 Mybatis-Plus 提供的基类
> 对象 Wrapper 为 条件构造器

为了方便理解，我们通俗的说一下ServiceImpl传入的2个泛型。
* 首先传入的是Mapper，这个Mapper是使我们的dao层接口，mapper也继承了BaseMapper的基类
* 其次传入了实体类型，我们知道mybatis-plus在代码生成上提供了很多方法，所以我们能够看到在Dept的实体类中对应了表名和字段的注解。

这里我们多少一下，为什么在service曾也要继承ServiceImpl<M extends BaseMapper<T>, T>这个基类，我们看一下里面基本上帮我们实现了一些常用的方法，并且已经添加了事务，避免了我们手动配置。
当然，如果service里不去继承，我认为也是没有问题的，就是回到了我们培训中遇到的普通的service模式。

回到service中，我们的方法找到了。
``` java
   /**
     * 获取ztree的节点列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:16 PM
     */
    public List<ZTreeNode> tree() {
        return this.baseMapper.tree();
    }

```

直接调用了dao层的方法。接口不看了，直接去找mapper
```xml
    <select id="tree" resultType="cn.stylefeng.guns.core.common.node.ZTreeNode">
		select DEPT_ID AS id, PID as pId, SIMPLE_NAME as name,
		(
		CASE
		WHEN (PID = 0 OR PID IS NULL) THEN
		'true'
		ELSE
		'false'
		END
		) as open from sys_dept
	</select>

```

mybatis mapper的语法我们就不看了，因为ZTreeNode这个javaBean中需要的属性分别是id，pid，name，open，所以我们需要在sql文中使用别名。

回到我们controller中，得到的tree是一个各个部门的list，最后我们再生成一个顶级，这样为前端构造树形结构提供了json。

### 关于如何显示一览表格
上面我们已经能够在dept.html找到一行代码，并且猜测这应该是负责展示表格的内容。但我们并没有找到表格是怎么逐条渲染出来的。

```js


@layout("/common/_container.html",{plugins:["ztree"],js:["/assets/modular/system/dept/dept.js"]}){
    
@}
```
在我们的html中，被这样的代码包围住。在了解具体的意义之前，看起来他是导入了共通的html和一个ztree插件以及一个js。
这个dept.js就是我们写业务js的地方。

我觉得先记住这个用法，回过头再理解。
参考：http://ibeetl.com/guide/ 2.5定界符与占位符
其中@符号在这里是定界符，而layout则是beetl的一个标签，允许指定一个layout模板文件，在渲染页面的时候，会将layout标签体的渲染内容作为一个layoutContent变量插入到layout指定模板文件里。
可以参考：http://ibeetl.com/guide/  3.12. 布局
在dept.js中我们找到渲染表格的代码
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
```
关于这个table控件的文档如下：https://www.layui.com/doc/modules/table.html ，这里的渲染方法数据这个控件的方法渲染。
作为一般的开发者，我们在开发的过程中将大量使用框架提供的相关组件、模块等等来提高开发速度，所以现阶段不建议再去看table空间的实现源码了。

在渲染表格初始化的过程中，出现了工具栏的id
```js

  {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 200}

```

这里对应的#tableBar，在dept.html中
``` js
<script type="text/html" id="tableBar">
    @if(shiro.hasPermission("/dept/update")){
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    @}
    @if(shiro.hasPermission("/dept/delete")){
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
    @}
</script>
```
参考：https://www.layui.com/doc/modules/table.html 其中table模块支持的参数一览中写到
开启表格头部工具栏区域，该参数支持四种类型值：
其中第一种，toolbar: '#toolbarDemo' //指向自定义工具栏模板选择器

之后看到url之后，指向的路径就是我们去controller中查找的路径。

在controller中遇到的@Permission注解先不用理解。

service中我们第一次遇到分页组件
```java
 Page page = LayuiPageFactory.defaultPage();
```
这里面的Page类型，是mybatis-plus实现的组件，里面包括如下属性。
```java
 private List<T> records;
    private long total;
    private long size;
    private long current;
    private String[] ascs;
    private String[] descs;
    private boolean optimizeCountSql;
    private boolean isSearchCount;

``` 
而defaultPage方法中，我们发现通过获得request中的params，来取得每页显示多少条数据和当前第几页。
```xml

 <select id="list" resultType="map">
        select
        <include refid="Base_Column_List"/>
        from sys_dept where 1 = 1
        <if test="condition != null and condition != ''">
            and SIMPLE_NAME like CONCAT('%',#{condition},'%') or FULL_NAME like CONCAT('%',#{condition},'%')
        </if>
        <if test="deptId != null and deptId != ''">
            and (DEPT_ID = #{deptId} or DEPT_ID in ( select DEPT_ID from sys_dept where PIDS like CONCAT('%[', #{deptId}, ']%') ))
        </if>
        order by SORT ASC
    </select>

```
在dao层的mapper中，我们首先不看两个if，这说明传这2个参数的时候，分别添加了不同的限制条件。
同时我们发现，检索到字段也通过引入得到。

这是我们得到了一个page对象，
之后controller类开始执行
```java
 Page<Map<String, Object>> wrap = new DeptWrapper(list).wrap();
```
实例化DeptWrapper(list)得到的是什么呢，我们通过BaseControllerWrapper发现
``` java
   public BaseControllerWrapper(Page<Map<String, Object>> page) {
        if (page != null && page.getRecords() != null) {
            this.page = page;
            this.multi = page.getRecords();
        }
    }
```
我们得到了一个page对象以及page.getRecords，也就是我们的记录list。
之后进行BaseControllerWrapper的wrap()方法的时候
``` java
    public <T> T wrap() {
        if (this.single != null) {
            this.wrapTheMap(this.single);
        }

        if (this.multi != null) {
            Iterator var1 = this.multi.iterator();

            while(var1.hasNext()) {
                Map<String, Object> map = (Map)var1.next();
                this.wrapTheMap(map);
            }
        }

        if (this.page != null) {
            return this.page;
        } else if (this.pageResult != null) {
            return this.pageResult;
        } else if (this.single != null) {
            return this.single;
        } else {
            return this.multi != null ? this.multi : null;
        }
    }
```
首先，multi不为空，将会执行wrapTheMap方法，二这个wrapTheMap是抽象方法，他的实现在子类
``` java
 @Override
    protected void wrapTheMap(Map<String, Object> map) {
        Long pid = (Long) map.get("pid");
        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }
    }
```
也就是说，这个方法把我们list中的每条记录进行了一次转换。
其中，项目作者称之为常量工厂的类，进行了deptName的查询。
```java

 @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.DEPT_NAME + "'+#deptId")
    public String getDeptName(Long deptId) {
        if (deptId == null) {
            return "";
        } else if (deptId == 0L) {
            return "顶级";
        } else {
            Dept dept = deptMapper.selectById(deptId);
            if (ToolUtil.isNotEmpty(dept) && ToolUtil.isNotEmpty(dept.getFullName())) {
                return dept.getFullName();
            }
            return "";
        }
    }
```
上述部分暂时不用理解。只需要理解根据pid将pName设置为不同的值，最后再返回即可。





















