package org.kettle.scheduler.system.biz.service;

import com.sun.org.apache.bcel.internal.generic.NEW;
import oracle.sql.CLOB;
import org.eclipse.swt.internal.C;
import org.kettle.scheduler.system.api.entity.Mview;
import org.kettle.scheduler.system.biz.mapper.SysMviewMapper;
import org.kettle.scheduler.system.biz.mapper.SysMviewTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @auther leo
 * @create 2019-12-25 17:38
 */

@Service
public class SysMviewService {

    @Autowired
    SysMviewMapper sysMviewMapper;

    private static final Logger logger = LoggerFactory.getLogger(SysMviewService.class);

    public List<Mview> findMviewByTagId(String tagId) {
        return sysMviewMapper.findMviewByTagId(tagId);
    }

    public List<Mview> findMviewList() {
        return sysMviewMapper.findMviewList();
    }

    public void refreshMview(String keyword, String result) {
        logger.info("刷新物化视图，关键词：" + keyword);
        List<Mview> mviewByNameOrTag = sysMviewMapper.findMviewByNameOrTag(keyword);
        mviewByNameOrTag.forEach(
                mview -> {
                    String mviewNmae = mview.getMviewName();
                    sysMviewMapper.refreshMview(mviewNmae);
                    //调用清理缓存接口
                    logger.info("物化视图：" + mviewNmae + "刷新完成，清理页面缓存。");
                    sysMviewMapper.syncMview(mviewNmae).forEach(mview1 -> sysMviewMapper.updateMview(mview1));
                });
    }
}
