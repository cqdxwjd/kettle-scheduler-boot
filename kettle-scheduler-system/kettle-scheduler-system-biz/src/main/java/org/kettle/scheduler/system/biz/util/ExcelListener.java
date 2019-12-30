package org.kettle.scheduler.system.biz.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.formula.functions.T;
import org.kettle.scheduler.system.api.entity.System;
import org.kettle.scheduler.system.biz.service.SysSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 17:28
 */
public class ExcelListener extends AnalysisEventListener<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 500;
    List<Object> list = new ArrayList<Object>();

    private SysSystemService sysSystemService;

    public ExcelListener() {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
        //demoDAO = new DemoDAO();
    }

    public ExcelListener(Object objectService) {
        if (objectService instanceof SysSystemService) {
            this.sysSystemService = (SysSystemService) objectService;
        } else {

        }
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(Object data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(data)), data.getClass());
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        if(sysSystemService!=null){
            List<System> systemList = new ArrayList<>();
            list.forEach(obj->{
                systemList.add((System) obj);
            });
            sysSystemService.addSystemList(systemList);
        }
        //demoDAO.save(list);

        LOGGER.info("存储数据库成功！");
    }
}