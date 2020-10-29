package org.kettle.scheduler.core.init;

import lombok.extern.log4j.Log4j2;
import org.pentaho.di.cluster.SlaveServer;
import org.pentaho.di.www.Carte;
import org.pentaho.di.www.SlaveServerConfig;
import org.springframework.beans.factory.InitializingBean;

//@Component
@Log4j2
public class CarteInit implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("创建slave Server");
        SlaveServer slaveServer = new SlaveServer("kettle", "127.0.0.1", "9080", "user", "password");
        log.info("创建slave Config");
        SlaveServerConfig slaveServerConfig = new SlaveServerConfig(slaveServer);
        Carte.runCarte(slaveServerConfig);
    }
}
