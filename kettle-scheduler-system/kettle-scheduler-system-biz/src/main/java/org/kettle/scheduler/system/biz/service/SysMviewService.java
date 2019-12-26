package org.kettle.scheduler.system.biz.service;

import org.kettle.scheduler.common.utils.BeanUtil;
import org.kettle.scheduler.system.api.response.MviewRes;
import org.kettle.scheduler.system.biz.entity.Mview;
import org.kettle.scheduler.system.biz.repository.MviewRepository;
import org.kettle.scheduler.system.biz.repository.QuartzRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @auther leo
 * @create 2019-12-25 17:38
 */

@Service
public class SysMviewService {

    private final MviewRepository mviewRepository;

    public SysMviewService(MviewRepository mviewRepository) {
        this.mviewRepository = mviewRepository;
    }

    public List<MviewRes> findMviewList() {
        List<Mview> list = mviewRepository.findAll();
        return list.stream().map(t -> BeanUtil.copyProperties(t, MviewRes.class)).collect(Collectors.toList());
    }
}
