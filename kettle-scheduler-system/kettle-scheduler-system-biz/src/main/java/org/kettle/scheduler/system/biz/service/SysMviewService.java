package org.kettle.scheduler.system.biz.service;

import com.sun.org.apache.bcel.internal.generic.NEW;
import oracle.sql.CLOB;
import org.eclipse.swt.internal.C;
import org.kettle.scheduler.system.api.entity.Mview;
import org.kettle.scheduler.system.biz.mapper.SysMviewMapper;
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

    public List<Mview> findMviewByTagId(String tagId) {
        return sysMviewMapper.findMviewByTagId(tagId);
    }

    public List<Mview> findMviewList() {
        return sysMviewMapper.findMviewList();
    }

    public boolean refreshMview(String keyword,String result) {
        Object o = sysMviewMapper.refreshMview(keyword,null);

        System.out.println(o.toString());
        /**
         * 1、物化视图刷新完成
         * 2、解析返回值
         * 3、根据返回值，同步对应的视图列表的更新时间的值
         * 4、调用清除数据中心缓存接口
         */
        return true;
    }
}
