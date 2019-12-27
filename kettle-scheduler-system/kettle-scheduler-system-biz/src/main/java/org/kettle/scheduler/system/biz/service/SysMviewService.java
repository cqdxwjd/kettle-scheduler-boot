package org.kettle.scheduler.system.biz.service;

import org.kettle.scheduler.system.api.entity.Mview;
import org.kettle.scheduler.system.biz.mapper.SysMviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    List<Mview> findMviewByTagId(String tagId) {
        return sysMviewMapper.findMviewByTagId(tagId);
    }

    public List<Mview> findMviewList() {
        return sysMviewMapper.findMviewList();
    }
}
