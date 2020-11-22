# kettle-scheduler-boot
    
## 开发计划

|序号|项目|状态|优先级|
|----|----|----|----|
|1|在线管理，编辑kettle脚本 |✔|紧急|
|2|通过源码实现集群，多线程执行任务 |✔|紧急|
|2|重构jpa部分，改为mybatis|✔|一般|

**注：项目在内部版本中已经定型，不会与大的更新了，有兴趣一起研究的，可以扫描最底下的微信一起开发下去.   
本来这个项目打算就此终结仅更新内部使用版本（大量内部功能代码），昨天官方突然推荐了，倒逼我继续更新。**   
![web kettle](https://images.gitee.com/uploads/images/2020/1029/095456_a0903322_720502.png "屏幕截图.png")   
**在线编辑kettle脚本工具，和桌面端的kettle一样用法**    
项目地址：https://github.com/HiromuHota/pentaho-kettle   
资源库字典：https://35youth.cn/kettleRepository.html

##### 通过源码创建集群，目前发现的问题是jettry的包冲突导致的问题
```  java
        SlaveServer slaveServer = new SlaveServer("kettle", "127.0.0.1", "9080", "user", "password");
        log.info("创建slave Config");
        SlaveServerConfig slaveServerConfig = new SlaveServerConfig(slaveServer);
        Carte.runCarte(slaveServerConfig);
```
#### 介绍
基于Spring-boot的kettle调度项目，参考了[zhaxiaodong9860](https://github.com/zhaxiaodong9860)的代码并引用了其中的页面管理，后台代码自行参考了API进行工具化编写，方便使用   
在原代码的基础上加入以下功能   
1、修改数据库为oracle   
2、增加mybatis相关依赖，陆续将jpa替换为mybatis   
3、增加向kettle脚本中传递参数，用于动态化脚本   
4、删除转换名不允许重复约束   
5、页面上增加转换描述显示   
6、增加redis，rabbitmq等中间件，用于调度抽取任务   
7、增加了一些扩展功能   

 **2020-11-22 更新说明**  
1、【优化】新增、编辑转换页面参数维护功能
 ![avatar](./docs/img/params.png)

 **2020-11-21 重要更新说明**  
1、增加读取资源库脚本，并获取脚本详情功能（获取详情暂未完成,跪求一名前端大佬助力）
 ![avatar](./docs/img/script.png)

 **2020-06-15 重要更新说明**  
 1、修改日志级别BUG，TransMate中设置日志级别不生效，将日志级别设置到Trans中    
 2、同一个脚本，同时运行，根据日志ID获取日志会有一个BUG，日志ID根据转换名称计算得来的，针对同一转换，不同参数手动设置LogChannel 中的名称参数   

 **2020-06-05 重要更新说明**   
 修改页面风格   
 ![avatar](./docs/img/index_new.png)
<centeer> **首页截图** </center>
 
 
 ![avatar](./docs/img/trans_new.png)
<centeer> **转换任务页面** </center>   
 **2020-05-13 重要更新说明**    
由于原代码数据库使用的是mysql，所有ID使用的是数据库自增长ID，迁移到oracle后，将自增长ID修改为触发器，目前根据使用到的实体类，创建了相应的序列及触发器，按照SEQ_TRANS及TRANS_TRIGGER去创建即可。   
| 序列列表 | 触发器列表 |   
| ------ | ------ |   
| SEQ_CATEGORY | CATEGORY_TRIGGER |   
| SEQ_DATABASE_TYPE | DATABASE_TYPE_TRIGGER |   
| SEQ_JOB_MONITOR | JOB_MONITOR_TRIGGER |   
| SEQ_JOB_RECORD | JOB_RECORD_TRIGGER |   
| SEQ_JOB | JOB_TRIGGER |   
| SEQ_QUARTZ | QUARTZ_TRIGGER |   
| SEQ_REPOSITORY | REPOSITORY_TRIGGER |   
| SEQ_TRANS_MONITOR | TRANS_MONITOR_TRIGGER |   
| SEQ_TRANS_RECORD | TRANS_RECORD_TRIGGER |   
| SEQ_TRANS | TRANS_TRIGGER |   
| SEQ_USER | USER_TRIGGER |  
 
**序列创建语句**
-- ----------------------------
 Sequence structure for SEQ_TRANS
 ```sql
DROP SEQUENCE "KETTLE_SCHEDULER"."SEQ_TRANS";
 CREATE SEQUENCE "KETTLE_SCHEDULER"."SEQ_TRANS" MINVALUE 1 MAXVALUE 99999999 INCREMENT BY 1 CACHE 20;
 ```
-- ----------------------------
**触发器创建语句**
-- ----------------------------
Triggers structure for table K_TRANS
```sql
CREATE TRIGGER "KETTLE_SCHEDULER"."TRANS_TRIGGER" BEFORE INSERT ON "KETTLE_SCHEDULER"."K_TRANS" REFERENCING OLD AS "OLD" NEW AS "NEW" FOR EACH ROW 
begin
select  SEQ_TRANS.Nextval into:new.id from dual;
end;
/
```
-- ----------------------------

#### 项目截图

![avatar](./docs/img/login.png)
 **<centeer>登录页截图</center>** 


![avatar](./docs/img/index.png)
 **<centeer>首页截图(已更新风格)</center>** 


![avatar](./docs/img/trans.png)
 **<centeer>新增转换任务(已更新风格)</center>   ** 
转换任务增加页面传递参数，手动写json格式的数据 
数据库k_trans表中增加trans_params字段，对应的实体类中也需要增加，通过执行任务时，从trans中获取参数，并设置到相应的脚本中
 
```java
//className  org.kettle.scheduler.system.biz.quartz.TransQuartz

@Override
public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
...........
Integer transId = jobExecutionContext.getMergedJobDataMap().getInt("id");
// 获取转换
Trans trans = transService.getTransById(transId);
// 设置执行参数
Map<String, String> params = new HashMap<>(2);
String transParams = trans.getTransParams();
Map jsonToMap = JSON.parseObject(transParams);
params.putAll(jsonToMap);
if (StringUtil.hasText(trans.getSyncStrategy())) {
	Integer day = Integer.valueOf(trans.getSyncStrategy().substring(2, trans.getSyncStrategy().length()));
	params.put("start_time", DateUtil.getDateTimeStr(DateUtil.addDays(DateUtil.getTodayStartTime(), -day)));
	params.put("end_time", DateUtil.getDateTimeStr(DateUtil.addDays(DateUtil.getTodayEndTime(), -day)));
}
............
}

```


#### 安装教程
1、导入docs中的kettle-scheduler-oracle.sql文件至oracle数据库   

#### 使用说明
1.  执行kettle-scheduler-starter下面docs下面的kettle-scheduler-oracle.sql脚本创建表

2.  生成环境执行时修改application-prod.yml中的数据库连接配置，开发环境修改application-dev.yml中的数据库配置，如果需要修改端口就在application.yml中修改

3.  修改application-kettle.yml配置，设置日志存储路径、kettle脚本保存路径、kettle-home路径（如果没有指定home路径，那么.kettle文件夹就在当前用户根路径下）

4.  如果需要自定义变量在kettle.properties中编写，并把kettle.properties文件拷贝到kettle-home路径下面的.kettle文件夹下

5.  启动项目使用调度平台(用户名：admin，密码：admin)

6.  如果要使用【文件资源库】需要单独把项目下【file-rep】拷贝到设置好的路径下，并在管理页面配置好文件资源库，因为打包后kettle不能访问到jar中的文件，所以需要单独存放

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
![微信](https://images.gitee.com/uploads/images/2020/1029/100546_32f86823_720502.png "屏幕截图.png")