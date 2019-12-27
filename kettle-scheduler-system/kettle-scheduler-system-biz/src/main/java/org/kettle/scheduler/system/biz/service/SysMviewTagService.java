package org.kettle.scheduler.system.biz.service;

import com.ctjsoft.util.StringUtil;
import org.kettle.scheduler.common.utils.BeanUtil;
import org.kettle.scheduler.system.api.request.MviewTagReq;
import org.kettle.scheduler.system.api.request.QuartzReq;
import org.kettle.scheduler.system.biz.entity.Mview;
import org.kettle.scheduler.system.biz.entity.MviewTag;
import org.kettle.scheduler.system.biz.entity.Quartz;
import org.kettle.scheduler.system.biz.mapper.SysMviewTagMapper;
import org.kettle.scheduler.system.biz.repository.MviewTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description
 * @auther leo
 * @create 2019-12-25 17:38
 */

@Service
public class SysMviewTagService {

    @Autowired
    SysMviewTagMapper sysMviewTagMapper;

    private final MviewTagRepository mviewTagRepository;

    public SysMviewTagService(MviewTagRepository mviewTagRepository) {
        this.mviewTagRepository = mviewTagRepository;
    }

    public List<MviewTag> findMviewTagList() {
        return sysMviewTagMapper.getMviewListTagList();
    }

    public List<MviewTagReq> findMviewTagByParentId(String parentId) {
        List<MviewTag> list = mviewTagRepository.findByparentid(parentId);
        return list.stream().map(t -> BeanUtil.copyProperties(t, MviewTagReq.class)).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(MviewTagReq req) {
        MviewTag mviewTag = BeanUtil.copyProperties(req, MviewTag.class);
        mviewTag.setId(StringUtil.getUUID());
        if (mviewTag.getParentid() == null) mviewTag.setParentid("0");
        mviewTagRepository.save(mviewTag);
    }

   /* @Transactional(rollbackFor = Exception.class)
    public void update(MviewTagReq req) {
        Optional<MviewTag> optional = mviewTagRepository.findByMviewTagCode(req.getMviewTagCode());
        if (optional.isPresent()) {
            MviewTag mviewTag = optional.get();
            BeanUtil.copyProperties(req, mviewTag);
            mviewTagRepository.save(mviewTag);
        }
    }*/
}
