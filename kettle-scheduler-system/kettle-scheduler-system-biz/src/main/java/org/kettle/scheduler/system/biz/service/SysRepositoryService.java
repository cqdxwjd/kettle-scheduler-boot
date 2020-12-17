package org.kettle.scheduler.system.biz.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.kettle.scheduler.common.enums.GlobalStatusEnum;
import org.kettle.scheduler.common.exceptions.MyMessageException;
import org.kettle.scheduler.common.povo.PageHelper;
import org.kettle.scheduler.common.povo.PageOut;
import org.kettle.scheduler.common.povo.TreeDTO;
import org.kettle.scheduler.common.utils.BeanUtil;
import org.kettle.scheduler.common.utils.SpringContextUtil;
import org.kettle.scheduler.common.utils.StringUtil;
import org.kettle.scheduler.core.dto.RepositoryDTO;
import org.kettle.scheduler.core.enums.RepTypeEnum;
import org.kettle.scheduler.core.repository.RepositoryUtil;
import org.kettle.scheduler.system.api.request.RepositoryReq;
import org.kettle.scheduler.system.api.request.TransReq;
import org.kettle.scheduler.system.api.response.RepositoryRes;
import org.kettle.scheduler.system.api.response.TransRes;
import org.kettle.scheduler.system.biz.entity.Repository;
import org.kettle.scheduler.system.biz.entity.bo.NativeQueryResultBO;
import org.kettle.scheduler.system.biz.entity.bo.TransBO;
import org.kettle.scheduler.system.biz.repository.RepositoryRepository;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.AbstractRepository;
import org.pentaho.di.repository.RepositoryObjectType;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 资源库管理业务逻辑层
 *
 * @author lyf
 */
@Service
public class SysRepositoryService {
    private final RepositoryRepository repRepository;

    public SysRepositoryService(RepositoryRepository repRepository) {
        this.repRepository = repRepository;
    }

    public void add(RepositoryReq req) {
        Repository rep = BeanUtil.copyProperties(req, Repository.class);
        repRepository.save(rep);
    }

    public void delete(Integer id) {
        repRepository.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        List<Repository> repositories = repRepository.findAllById(ids);
        repRepository.deleteInBatch(repositories);
    }

    public void update(RepositoryReq req) {
        Optional<Repository> optional = repRepository.findById(req.getId());
        if (optional.isPresent()) {
            Repository rep = optional.get();
            BeanUtil.copyProperties(req, rep);
            repRepository.save(rep);
        }
    }

    public PageOut<RepositoryRes> findRepListByPage(RepositoryReq query, PageHelper page) {
        // 排序
        Sort sort = page.getSorts().isEmpty() ? Sort.by(Sort.Direction.DESC, "addTime") : page.getSorts();
        // 查询
        Page<Repository> pageList = null;
        if (query != null) {
            Repository rep = BeanUtil.copyProperties(query, Repository.class);
            Example<Repository> example = Example.of(rep, ExampleMatcher.matchingAll().withIgnoreCase());
            pageList = repRepository.findAll(example, PageRequest.of(page.getNumber(), page.getSize(), sort));
        } else {
            pageList = repRepository.findAll(PageRequest.of(page.getNumber(), page.getSize(), sort));
        }
        // 封装数据
        List<RepositoryRes> collect = pageList.get().map(t -> {
            RepositoryRes res = BeanUtil.copyProperties(t, RepositoryRes.class);
            res.setRepTypeStr(RepTypeEnum.getEnumDesc(res.getRepType()));
            return res;
        }).collect(Collectors.toList());
        return new PageOut<>(collect, pageList.getNumber(), pageList.getSize(), pageList.getTotalElements());
    }

    public RepositoryRes getRepositoryDetail(Integer id) {
        Optional<Repository> optional = repRepository.findById(id);
        return optional.map(repository -> BeanUtil.copyProperties(repository, RepositoryRes.class)).orElse(null);
    }

    public List<RepositoryRes> findRepList() {
        List<Repository> list = repRepository.findAll();
        return list.stream().map(rep -> BeanUtil.copyProperties(rep, RepositoryRes.class)).collect(Collectors.toList());
    }

    public List<TreeDTO<String>> findRepTreeById(Integer id, RepositoryObjectType objectType) {
        Optional<Repository> optional = repRepository.findById(id);
        if (optional.isPresent()) {
            Repository rep = optional.get();
            RepositoryDTO repDto = BeanUtil.copyProperties(rep, RepositoryDTO.class);
            // 连接资源库
            AbstractRepository repository = RepositoryUtil.connection(repDto);
            // 遍历获取资源库信息
            List<TreeDTO<String>> repositoryTreeList = RepositoryUtil.getRepositoryTreeList(repository, "/", objectType);
            return repositoryTreeList;
        } else {
            return null;
        }
    }

    public List<TreeDTO<String>> findTransRepTreegridById(Integer id, RepositoryObjectType objectType) {
        Optional<Repository> optional = repRepository.findById(id);
        if (optional.isPresent()) {
            Repository rep = optional.get();
            RepositoryDTO repDto = BeanUtil.copyProperties(rep, RepositoryDTO.class);
            // 连接资源库
            AbstractRepository repository = RepositoryUtil.connection(repDto);
            // 遍历获取资源库信息
            List<TreeDTO<String>> repositoryTreeList = RepositoryUtil.getRepositoryTreeList(repository, "/", objectType);
            return dataFormat(repositoryTreeList);
            //return repositoryTreeList;
        } else {
            return null;
        }
    }

    /**
     * 数据格式转化
     *
     * @param repositoryTreeList
     */
    private List<TreeDTO<String>> dataFormat(List<TreeDTO<String>> repositoryTreeList) {
        List<TreeDTO<String>> resultList = new ArrayList<>();
        repositoryTreeList.forEach(treeDTO -> {
            String id = treeDTO.getId();
            List<TreeDTO<String>> children = treeDTO.getChildren();
            if (children != null) {
                children.forEach(childrenTree -> {
                    String id1 = childrenTree.getId();
                    if (childrenTree.getChildren() != null) {
                        //末级脚本
                        childrenTree.getChildren().forEach(childrens -> {
                            childrens.setPid(id1);
                            childrens.setChildren(null);
                            resultList.add(childrens);
                        });
                    }
                    childrenTree.setChildren(null);
                    resultList.add(childrenTree);
                    childrenTree.setPid(id);
                });
            }
            treeDTO.setChildren(null);
            resultList.add(treeDTO);
        });
        return resultList;
    }


    public void testConnection(RepositoryReq req) {
        RepositoryDTO repDto = BeanUtil.copyProperties(req, RepositoryDTO.class);
        // 连接资源库
        AbstractRepository repository = RepositoryUtil.connection(repDto);
        // 判断是否链接成功
        if (repository == null || !repository.isConnected()) {
            throw new MyMessageException(GlobalStatusEnum.KETTLE_ERROR, "链接资源库失败");
        }
    }

    public Repository getByRepName(String repName) {
        return repRepository.getByRepName(repName);
    }

    /**
     * 根据资源库获取脚本信息
     *
     * @param transRepositoryId 资源库ID
     * @param scriptPath  脚本路径
     * @param scriptName  脚本名称
     * @param type        脚本类型 trans:job
     * @returnloadTransformation
     * @throws KettleException
     */
    public String getScriptByRepository(String transRepositoryId, String scriptPath, String scriptName, String type) throws KettleException {
        return RepositoryUtil.getScriptByRepository(getAbstractRepository(Integer.valueOf(transRepositoryId)), scriptPath, scriptName, null, type).toString();
    }


    /**
     * 获取资源库
     *
     * @param transRepositoryId 资源库id
     * @return {@link AbstractRepository}
     */
    public AbstractRepository getAbstractRepository(Integer transRepositoryId) {
        AbstractRepository repository = RepositoryUtil.getRepository(transRepositoryId);
        if (repository == null) {
            RepositoryRepository repRepository = SpringContextUtil.getBean(RepositoryRepository.class);
            Optional<Repository> optionalRepository = repRepository.findById(transRepositoryId);
            if (!optionalRepository.isPresent()) {
                throw new MyMessageException("资源库不存在");
            }
            Repository rep = optionalRepository.get();
            RepositoryDTO repDto = BeanUtil.copyProperties(rep, RepositoryDTO.class);
            // 连接资源库
            repository = RepositoryUtil.connection(repDto);
        }
        return repository;
    }
}
