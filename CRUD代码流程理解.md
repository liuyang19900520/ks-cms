之前我们通过这个树状图初步了解了程序的运行的一个过程，我们做业务层，说到底无非就是CRUD。在这个基础上做一些操作，

### 关注查询（如何显示一览表格）

上面我们已经能够在dept.html找到一行代码，并且猜测这应该是负责展示表格的内容。但我们并没有找到表格是怎么逐条渲染出来的。
```j s
 @layout("/common/_container.html",{plugins:["ztree"],js:["/assets/modular/system/dept/dept.js"]}){
 @}
```
在我们的html中，被这样的代码包围住。在了解具体的意义之前，看起来他是导入了共通的html和一个ztree插件以及一个js。这个dept.js就是我们写业务js的地方。

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
``` java
 Page page = LayuiPageFactory.defaultPage();
```
这里面的Page类型，是mybatis-plus实现的组件，里面包括如下属性。
``` java
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
``` java
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
``` java
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
上述部分暂时不用理解。只需要理解根据pid将pName设置为不同的值，最后再返回即可。 这样我们的查询一览功能基本就没问题了。

### 关注添加（如何插入一条数据）
我们在dept.html中看到以下代码，为查询的按钮：
``` java
 <button id="btnAdd" class="layui-btn icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>
```
我们关注一下他的js，在js中的openAddDept方法里进行了添加操作。
关于top.layui.admin.open的这个方法，是layui的弹出层，具体方法在：https://www.layui.com/doc/modules/layer.html
关于这个admin的变量是哪里来的，我们看一下common.js中 var admin = layui.admin;这段，我们可以先理解为创建了admin参数。

我们发现在这个content中的路径，通过controller返回得到了dept_add.html的页面，而真正的insert 数据库的逻辑在dept_add.js中，
``` js
    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/dept/add", function (data) {
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
```
简单介绍一下。
* ajax控件，完成请求操作
* Feng.success(),在common.js中封装的提示信息的代码
*  admin.putTempData('formOk', true); 页面传值，记得在openAddDept方法中，我们在打开页面时传递了一个formOk=false，目的是，当我们
这次open生命周期结束的时候，也就是在end方法中，是否刷新我们原来的table，这个刷新用的事table.reload方法，具体的方法使用我就不去查找了。
由于是第一次增删改查的理解，我们还是在回到java中看一下插入是否有一些其他需要注意的点。

controller去调用service的方法。
serivce中我们还是简单说明几点：
* 判断非空的工具类我们抛出了异常，而这个业务异常是BizExceptionEnum 枚举类型，如果不熟悉，建议自己查一下枚举的用法（https://blog.csdn.net/qq_27093465/article/details/52180865）
* 判断非空为什么要抛出异常呢。因为我们发现在service这个方法上，有 @Transactional(rollbackFor = Exception.class)这个注解：
这个注解我们可以简单理解成在这个serivce中出现exception异常的时候，发生回滚。如果不了解springboot事务的话，这里有个我之前自己写的博客（http://liuyang19900520.com/blogs/211545915243098112）
* deptSetPids(dept);这个方法简单说，就是通过输入的父部门id，找到父部门id的父部门id，在拼到一起存在数据库里。
* save方法是mybatis-plus中的方法。
剩下就没有什么可以说的了。
最后controller中返回的这个SUCCESS_TIP，是我们默认成功的json格式。
``` java
 public SuccessResponseData() {
        super(true, DEFAULT_SUCCESS_CODE, "请求成功", (Object)null);
    }
```
关于add方法，简单看，基本上就这些内容了。


