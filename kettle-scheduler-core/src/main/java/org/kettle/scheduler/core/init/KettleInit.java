package org.kettle.scheduler.core.init;

import lombok.extern.log4j.Log4j2;
import org.kettle.scheduler.common.utils.FileUtil;
import org.kettle.scheduler.common.utils.StringUtil;
import org.kettle.scheduler.core.constant.KettleConfig;
import org.pentaho.di.core.KettleEnvironment;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * kettle初始化
 *
 * @author lyf
 */
@Component
@Log4j2
public class KettleInit implements InitializingBean {
	@Autowired
	KettleConfig KettleConfig;

	@Override
	public void afterPropertiesSet() throws Exception {
		// 自定义环境初始化
		environmentInit();
		// kettle环境初始化
		KettleEnvironment.init();
	}

	private void environmentInit() {
		log.info("KETTLE HOME:"+KettleConfig.kettleHome);
		Properties properties = System.getProperties();
		properties.put("KETTLE_HOME", KettleConfig.kettleHome);
		if (StringUtil.hasText(KettleConfig.kettlePluginPackages)) {
			log.info("KETTLE_PLUGIN_PACKAGES:"+KettleConfig.kettlePluginPackages);
			properties.put("KETTLE_PLUGIN_PACKAGES", FileUtil.replaceSeparator(KettleConfig.kettlePluginPackages));
		}
		if (StringUtil.hasText(KettleConfig.kettlePluginBaseFolder)) {
			log.info("KETTLE_PLUGIN_BASE_FOLDERS:"+KettleConfig.kettlePluginBaseFolder);
			properties.put("KETTLE_PLUGIN_BASE_FOLDERS", FileUtil.replaceSeparator(KettleConfig.kettlePluginBaseFolder));
			//System.setProperty("KETTLE_PLUGIN_BASE_FOLDERS", KettleConfig.kettlePluginBaseFolder);
		}

	}
}
