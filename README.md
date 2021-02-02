# kettle-scheduler-boot
    
## 问卷调查
[问卷地址](https://www.35youth.cn/wp-admin/admin-ajax.php?action=frm_forms_preview&form=contact-form) 请提出你的宝贵意见，帮助我们优化项目。
![avatar](./docs/img/questionnaire.png)
## 开发计划

|序号|项目|状态|优先级|
|----|----|----|----|
|1|在线管理，编辑kettle脚本 |×|紧急|
|2|数据库改为MySql，逐步废弃Oracle分支|×|紧急|
|3|重构jpa部分，改为mybatis|×|一般|

#### 介绍
基于Spring-boot的kettle调度项目，参考了[zhaxiaodong9860](https://github.com/zhaxiaodong9860)的代码并引用了其中的页面管理，后台代码自行参考了API进行工具化编写，方便使用   
在原代码的基础上加入以下功能   
1、修改数据库为oracle   
2、增加mybatis相关依赖，陆续将jpa替换为mybatis   
3、增加向kettle脚本中传递参数，用于动态化脚本   
4、删除转换名不允许重复约束   
5、页面上增加转换描述显示   
6、增加了一些扩展功能   
7、增加在线编辑脚本   

#### 演示环境
[点击查看演示环境](http://kettle.35youth.cn)   
账号：demo   
密码：demo@1234   
**演示环境，请善待。**   

[点击下载可运行程序包](https://pan.baidu.com/s/1xnd2bYZ_3cZGmSm4azg8iw) 提取码：e150

#### [点击查看完整更新记录](./docs/md/update.md) 

**2020-0202 更新说明**  
1、【增加】增加脚本管理返回类型字段objectType，用于前台区分trans和job   
2、【BUG】解决前台无法切换资源库BUG   
![avatar](./docs/img/script_bug.jpg)
根目录转换预览

**2020-12-17 更新说明**  
1、【新增】增加表输入预览数据   
![avatar](./docs/img/dataPreview.png)
数据预览 

#### [已知Bug列表](./docs/md/Bug.md)

#### 项目截图

![avatar](./docs/img/login.png)
 **<centeer>登录页截图</center>** 


![avatar](./docs/img/index_new.png)
 **<centeer>首页截图(已更新风格)</center>** 


![avatar](./docs/img/trans_new.png)
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

![web kettle](https://images.gitee.com/uploads/images/2020/1029/095456_a0903322_720502.png "屏幕截图.png")   
**在线编辑kettle脚本工具，和桌面端的kettle一样用法**    
项目地址：https://github.com/HiromuHota/pentaho-kettle   
资源库字典：https://35youth.cn/kettleRepository.html

#### 安装教程
1、导入docs中的1_XXX.sql 2_XXX、3_XXX文件至oracle数据库   

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

#### 感谢
项目开源至今，感谢一下朋友的各种帮助。（排名按时间先后顺序）   

| 序号  |     名称     |            个人主页               |    捐赠方式    | 金额 |          备注                |  时间 |
| ---- | ----------- | -------------------------------- | ------------ | ---- | --------------------------- | ----- |
|  1   | 啦啦啦啦啦啦  | https://www.cnblogs.com/wlh1995/ | 维护Mysql分支  | XX   | kettle作者热心，一起加油。   | 2020-12-11 |
|  2   | 华子哥 | XXX | 微信  | 50 |  | 2021-01-20 |
#### 关于我
1.  半码农
<img src="https://images.gitee.com/uploads/images/2020/1029/100546_32f86823_720502.png" width="50%" height="50%" />



