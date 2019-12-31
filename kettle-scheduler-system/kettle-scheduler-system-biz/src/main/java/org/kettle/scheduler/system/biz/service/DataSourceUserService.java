package org.kettle.scheduler.system.biz.service;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.kettle.scheduler.system.api.entity.DatasourceUser;
import org.kettle.scheduler.system.biz.mapper.DatasourceUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-30 20:22
 */

@Service
public class DataSourceUserService {

    @Autowired
    DatasourceUserMapper datasourceUserMapper;

    public List<DatasourceUser> getDatasourceUserList() {
        return datasourceUserMapper.getDatasourceUserList();
    }

    public DatasourceUser getDatasourceUserById(String id) {
        return datasourceUserMapper.getDatasourceUserById(id);
    }

    public DatasourceUser getDatasourceUserByAdmdivcode(String admdivcode){
        return datasourceUserMapper.getDatasourceUserByAdmdivcode(admdivcode);
    }

    public int addDatasourceUser(DatasourceUser datasourceUser) {
        return datasourceUserMapper.addDatasourceUser(datasourceUser);
    }

    public int addDatasourceUserList(List<DatasourceUser> datasourceUserList) {
        return datasourceUserMapper.addDatasourceUserList(datasourceUserList);
    }

    public int updateDatasourceUser(DatasourceUser datasourceUser) {
        return datasourceUserMapper.updateDatasourceUser(datasourceUser);
    }

    public int deleteDatasourceUser(String id) {
        return datasourceUserMapper.deleteDatasourceUser(id);
    }

}
