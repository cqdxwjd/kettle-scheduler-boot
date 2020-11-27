# kettle-scheduler-boot
    
## Bug列表

##### 通过源码创建集群，目前发现的问题是jettry的包冲突导致的问题
```  java
        SlaveServer slaveServer = new SlaveServer("kettle", "127.0.0.1", "9080", "user", "password");
        log.info("创建slave Config");
        SlaveServerConfig slaveServerConfig = new SlaveServerConfig(slaveServer);
        Carte.runCarte(slaveServerConfig);
```