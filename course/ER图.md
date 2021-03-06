# 福利后生功能的表设计
## 基本概念
* 实体一般指实际业务对象
* 关系是业务对象之间的关系存在

## 表和表之间的关系

### 一对一
学生表和校园网用户表
#### 表结构
student表
sid，sname，age
user表
uid，nickname，password，avatar，sid
#### 功能
校园网登录
显示登录信息


### 一对多
#### 学生 班级
student表
sid，sname，age，classid
class表
classid,cname
班级是1端，学生是多端，结合面向对象的思想，1端是父亲，多端是儿子，所以多端具有1端的属性，也就是说多端里面应该放置1端的主键，
那么学生表里面应该放置班级表里面的主键 

#### 功能
查看班级人员在线人数
所有班级
点击班级等于传递班级id，去查询classid对一个的学生（user信息）

### 自连接关系
自联接关系（或自联接）是在同一个表中定义两个匹配字段的关系。
学生表
student表
sid，sname，age，tlid



### 多对多
创建群组
user表
uid，nickname，password，avatar，sid

group表
gid，gname,gnotice


#### 功能

为1，2班所有学生创建一个群

## 数据库设计（范式）

### 第一范式
原子性  电话号码 

### 第二范式
有一个主键 非主主键完全依赖于主键

第二学生选课详情表  selectdetail


| selectid| courseid | cname |point |selectway |courseway |
1        1        刘洋讲ER   4       dean          offline
1        2        尚晓旭日语 1       dean          offline
2        1        刘洋讲ER   4       app           online  


### 第三范式 
满足第三一定满足第二，首先是 2NF，另外非主键列必须直接依赖于主键，不能存在传递依赖。
即不能存在：非主键列 A 依赖于非主键列 B，非主键列 B 依赖于主键的情况。

第二范式（2NF）和第三范式（3NF）的概念很容易混淆，区分它们的关键点在于，
2NF：非主键列是否完全依赖于主键，还是依赖于主键的一部分；
3NF：非主键列是直接依赖于主键，还是直接依赖于非主键列。


### BCNF范式
BCNF意味着在关系模式中每一个决定因素都包含候选键，也就是说，只要属性或属性组A能够决定任何一个属性B，则A的子集中必须有候选键。
BCNF范式排除了任何属性(不光是非主属性，2NF和3NF所限制的都是非主属性)对候选键的传递依赖与部分依赖。

BCNF与第三范式的不同之处在于：第三范式中不允许非主属性被另一个非主属性决定，但第三范式允许主属性被非主属性决定；
而在BCNF中，任何属性（包括非主属性和主属性）都不能被非主属性所决定。
























