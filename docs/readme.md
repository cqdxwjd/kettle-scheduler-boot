
# 打包
mvn clean install
# 运行
## 新建kettle的需要的目录
```
sudo mkdir -p  /apps/logs/kettle-scheduler/run-logs
sudo mkdir -p  /apps/var/kettle-script-file
sudo mkdir -p  /apps/var/file-rep
sudo mkdir -p  /apps/var/file-rep/plugin
sudo chmod 777 /apps/logs/kettle-scheduler/run-logs
sudo chmod 777 /apps/var/kettle-script-file
sudo chmod 777 /apps/var/file-rep
sudo chmod 777 /apps/var/file-rep/plugin
```
- 导入kettle_scheduler_mysql.sql
- cd kettle-scheduler-starter/target
- 修改数据库连接配置。
```
nohup java -jar kettle-scheduler-starter-1.0-SNAPSHOT.jar "--server.port=8179" \
 "--spring.datasource.url=jdbc:mysql://127.0.0.1:3306/kettle?characterEncoding=utf8&autoReconnect=true&useSSL=false" \
 "--spring.datasource.username=root" \
 "--spring.datasource.password=root" &
``` 
