package org.kettle.scheduler.system.biz.service;

import org.kettle.scheduler.common.utils.BeanUtil;
import org.kettle.scheduler.system.api.entity.MviewTag;
import org.kettle.scheduler.system.api.request.MviewTagReq;
import org.kettle.scheduler.system.biz.mapper.SysMviewTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @auther leo
 * @create 2019-12-25 17:38
 */

@Service
public class SysMviewTagService {

    @Autowired
    SysMviewTagMapper sysMviewTagMapper;

    public List<MviewTag> findMviewTagList() {
        return sysMviewTagMapper.getMviewListTagList();
    }

    public List<MviewTag> findMviewTagByParentId(String parentId) {
        return sysMviewTagMapper.findMviewTagByParentId(parentId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(MviewTag mviewTag) {
        sysMviewTagMapper.add(mviewTag);
    }

    @Transactional(rollbackFor = Exception.class)
    public int update(MviewTag mviewTag) {
        return sysMviewTagMapper.update(mviewTag);
    }
}
