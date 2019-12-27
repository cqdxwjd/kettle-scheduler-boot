package org.kettle.scheduler.system.biz.service;

import org.kettle.scheduler.common.utils.BeanUtil;
import org.kettle.scheduler.system.api.request.MviewTagReq;
import org.kettle.scheduler.system.api.request.QuartzReq;
import org.kettle.scheduler.system.biz.entity.Mview;
import org.kettle.scheduler.system.biz.entity.MviewTag;
import org.kettle.scheduler.system.biz.entity.Quartz;
import org.kettle.scheduler.system.biz.repository.MviewTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @auther leo
 * @create 2019-12-25 17:38
 */

@Service
public class SysMviewTagService {

    private final MviewTagRepository mviewTagRepository;

    public SysMviewTagService(MviewTagRepository mviewTagRepository) {
        this.mviewTagRepository = mviewTagRepository;
    }

    public List<MviewTagReq> findMviewTagList() {
        List<MviewTag> list = mviewTagRepository.findAll();
        return list.stream().map(t -> BeanUtil.copyProperties(t, MviewTagReq.class)).collect(Collectors.toList());
    }
    public List<MviewTagReq> findMviewTagByParentId(String parentId){
        List<MviewTag> list = mviewTagRepository.findByparentId(parentId);
        return list.stream().map(t -> BeanUtil.copyProperties(t, MviewTagReq.class)).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(MviewTagReq req) {
        MviewTag mviewTag = BeanUtil.copyProperties(req, MviewTag.class);
        mviewTagRepository.save(mviewTag);
    }
}
