package org.kettle.scheduler.system.biz.service;

import org.kettle.scheduler.system.biz.mapper.SysSystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 * ‘
 *
 * @author leo
 * @create 2019-12-30 12:52
 */
@Service
public class SysSystemService {

    @Autowired
    SysSystemMapper sysSystemMapper;

    public List<System> getSystemList() {
        return sysSystemMapper.getSystemList();
    }

    public System getSystemById(String id) {
        return sysSystemMapper.getSystemById(id);
    }

    public int addSystem(System system) {
        return sysSystemMapper.addSystem(system);
    }

    public int addSystemList(List<System> systemList) {
        return sysSystemMapper.addSystemList(systemList);
    }

    public int updateSystem(System system) {
        return sysSystemMapper.updateSystem(system);
    }

    public int deleteSystem(String id) {
        return sysSystemMapper.deleteSystem(id);
    }

    public List<System> searchSystem(String keyword) {
        return sysSystemMapper.searchSystem(keyword);
    }
}
