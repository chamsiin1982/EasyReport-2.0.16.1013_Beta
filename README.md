EasyReport
==========

为 https://github.com/xianrendzw/EasyReport 项目的一个改造版本

### 1、主要是利用 JSQLParser 改造 报表查询条件添加 查询条件附加功能，这样就支持两种方式
#### 1.1 查询条件 附加(append) 新增加
根据查询参数中是否存在配置的查询参数，如果存在则 将 查询条件和参数值附加到执行语句上
#### 1.2 查询条件 替换(replace) 原设计的参数方式
 利用占位符，进行变量的替换，缺点：参数传值只能通过默认值和传参来控制
### 2、查询条件支持树形数据
对层级类数据，支持树形选择，比如：省、市、县下拉联动，只能通过三个下拉列表来处理，且三个控件之间的联动也是需要解决的，树形控件刚好解决了这个问题，不足：数据量大的时候

### 3、利用表达式列作为模板，实现父子报表
目前参数都是单值，多值未测试

### 4、为方便父子报表传参，扩展查询参数隐藏属性

参数隐藏属性，方便报表传参