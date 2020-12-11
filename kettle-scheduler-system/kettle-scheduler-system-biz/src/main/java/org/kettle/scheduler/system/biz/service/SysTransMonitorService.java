package org.kettle.scheduler.system.biz.service;

import org.kettle.scheduler.common.exceptions.MyMessageException;
import org.kettle.scheduler.common.povo.PageHelper;
import org.kettle.scheduler.common.povo.PageOut;
import org.kettle.scheduler.common.utils.BeanUtil;
import org.kettle.scheduler.common.utils.StringUtil;
import org.kettle.scheduler.system.api.request.CountSumReq;
import org.kettle.scheduler.system.api.request.MonitorQueryReq;
import org.kettle.scheduler.system.api.request.TransRecordReq;
import org.kettle.scheduler.system.api.response.CountSumRes;
import org.kettle.scheduler.system.api.response.TaskCountRes;
import org.kettle.scheduler.system.api.response.TransMonitorRes;
import org.kettle.scheduler.system.api.response.TransRecordRes;
import org.kettle.scheduler.system.biz.component.EntityManagerUtil;
import org.kettle.scheduler.system.biz.entity.Trans;
import org.kettle.scheduler.system.biz.entity.TransLog;
import org.kettle.scheduler.system.biz.entity.TransMonitor;
import org.kettle.scheduler.system.biz.entity.TransRecord;
import org.kettle.scheduler.system.biz.entity.bo.*;
import org.kettle.scheduler.system.biz.enums.CountMark;
import org.kettle.scheduler.system.biz.enums.CountType;
import org.kettle.scheduler.system.biz.repository.TransLogRepository;
import org.kettle.scheduler.system.biz.repository.TransMonitorRepository;
import org.kettle.scheduler.system.biz.repository.TransRecordRepository;
import org.kettle.scheduler.system.biz.repository.TransRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 转换监控业务逻辑层
 *
 * @author lyf
 */
@Service
public class SysTransMonitorService {
    private final TransRepository transRepository;
    private final TransMonitorRepository monitorRepository;
    private final TransRecordRepository recordRepository;
    private final EntityManagerUtil entityManagerUtil;
    private final TransLogRepository transLogRepository;

    public SysTransMonitorService(TransRepository transRepository, TransMonitorRepository monitorRepository,
                                  TransRecordRepository recordRepository, EntityManagerUtil entityManagerUtil, TransLogRepository transLogRepository) {
        this.transRepository = transRepository;
        this.monitorRepository = monitorRepository;
        this.recordRepository = recordRepository;
        this.entityManagerUtil = entityManagerUtil;
        this.transLogRepository = transLogRepository;
    }

    public PageOut<TransMonitorRes> findTransMonitorListByPage(MonitorQueryReq query, Pageable pageable) {
        String selectSql = "SELECT a.*,b.trans_name,c.category_name,b.trans_description ";
        // 动态拼接from部分的sql
        StringBuilder fromSql = new StringBuilder(" FROM k_trans_monitor a ");
        fromSql.append("INNER JOIN k_trans b ON a.monitor_trans_id=b.id ");
        fromSql.append("LEFT JOIN k_category c ON b.category_id=c.id ");
        if (query != null) {
            fromSql.append("WHERE 1=1 ");
            if (!StringUtil.isEmpty(query.getScriptName())) {
                fromSql.append("AND b.trans_name like '%").append(query.getScriptName()).append("%'").append(" ");
            }
            if (query.getMonitorStatus() != null) {
                fromSql.append("AND a.monitor_status = ").append(query.getMonitorStatus()).append(" ");
            }
            if (query.getCategoryId() != null) {
                fromSql.append("AND b.category_id = ").append(query.getCategoryId()).append(" ");
            }
        }
        // order by 部分的sql
        String orderSql = "order by a.add_time desc ";
        // 初始化sql语句
        NativeQueryResultBO resultBo = entityManagerUtil.executeNativeQueryForList(selectSql, fromSql.toString(), orderSql, pageable, TransMonitorBO.class);
        List<TransMonitorRes> list = new ArrayList<>();
        for (Object o : resultBo.getResultList()) {
            list.add(BeanUtil.copyProperties(o, TransMonitorRes.class));
        }
        // 封装数据
        return new PageOut<>(list, pageable.getPageNumber(), pageable.getPageSize(), resultBo.getTotal());
    }

    public PageOut<TransRecordRes> findTransRecordList(Integer transId, PageHelper pageHelper) {
        // 默认排序
        Pageable pageable = pageHelper.getPageable(Sort.by(Sort.Direction.DESC, "addTime"));
        // 查询trans信息
        Optional<Trans> transOptional = transRepository.findById(transId);
        String transName = "";
        String transDescription = "";
        if (transOptional.isPresent()) {
            //transName = transOptional.get().getTransName();
            transDescription = transOptional.get().getTransDescription();
        }
        // 分页查询执行记录
        Page<TransRecord> page = recordRepository.findByRecordTransId(transId, pageable);
        // 封装数据
        String finalTransName = transDescription;

        List<TransRecordRes> collect = page.get().map(t -> {
            TransRecordRes res = BeanUtil.copyProperties(t, TransRecordRes.class);
            res.setTransName(finalTransName);
            return res;
        }).collect(Collectors.toList());
        return new PageOut<>(collect, page.getNumber(), page.getSize(), page.getTotalElements());
    }

    public PageOut<TransRecordRes> findTransRecordListByerror(MonitorQueryReq query, Pageable pageable) {
        String selectSql = "SELECT a.*,b.trans_name,c.category_name,b.trans_description ";
        // 动态拼接from部分的sql
        StringBuilder fromSql = new StringBuilder(" FROM K_TRANS_RECORD a ");
        fromSql.append("INNER JOIN k_trans b ON a.RECORD_TRANS_ID=b.id ");
        fromSql.append("LEFT JOIN k_category c ON b.category_id=c.id WHERE 1=1 AND A.RECORD_STATUS=0");
        if (query != null) {
            if (query.getCategoryId() != null) {
                fromSql.append("AND b.category_id = ").append(query.getCategoryId()).append(" ");
            }
        }
        // order by 部分的sql
        String orderSql = "order by a.stop_time desc ";
        // 初始化sql语句
        NativeQueryResultBO resultBo = entityManagerUtil.executeNativeQueryForList(selectSql, fromSql.toString(), orderSql, pageable, TransRecordBO.class);
        List<TransRecordRes> list = new ArrayList<>();
        for (Object o : resultBo.getResultList()) {
            list.add(BeanUtil.copyProperties(o, TransRecordRes.class));
        }
        // 封装数据
        return new PageOut<>(list, pageable.getPageNumber(), pageable.getPageSize(), resultBo.getTotal());
    }

    public TransRecord getTransRecord(Integer transRecordId) {
        Optional<TransRecord> optional = recordRepository.findById(transRecordId);
        return optional.orElse(null);
    }

    public void updateMonitor(TransMonitor transMonitor, boolean success) {
        TransMonitor monitor = monitorRepository.findByMonitorTransId(transMonitor.getMonitorTransId());
        if (monitor == null) {
            throw new MyMessageException("当前转换对应的监控对象不存在");
        } else {
            BeanUtil.copyProperties(transMonitor, monitor);
            if (success) {
                monitor.setMonitorSuccess(monitor.getMonitorSuccess() + 1);
            } else {
                monitor.setMonitorFail(monitor.getMonitorFail() + 1);
            }
            monitorRepository.save(monitor);
        }
    }

    public void addTransRecord(TransRecord transRecord) {
        recordRepository.save(transRecord);
    }

    public TaskCountRes countTrans() {
        String sql = "SELECT count(1) total, IFNULL(sum(monitor_success),0) success, IFNULL(sum(monitor_fail),0) fail FROM k_trans_monitor";
        TaskCountBO result = entityManagerUtil.executeNativeQueryForOne(sql, TaskCountBO.class);
        return BeanUtil.copyProperties(result, TaskCountRes.class);
    }

    public TaskCountRes countTransByToday(Integer categoryid) {
        String sql = "SELECT count(1) total，IFNULL(sum(case when RECORD_STATUS = 1 then 1 else 0 end),0) success,IFNULL(sum(case when RECORD_STATUS = 0 then 1 else 0 end),0) fail from K_TRANS_RECORD WHERE TO_CHAR(stop_time, 'YYYYMMDD' ) = TO_CHAR(SYSDATE, 'YYYYMMDD') and category_id=" + categoryid;
        TaskCountBO result = entityManagerUtil.executeNativeQueryForOne(sql, TaskCountBO.class);
        return BeanUtil.copyProperties(result, TaskCountRes.class);
    }

    public void addTransRecord(String logText, Trans trans, Map<String, String> param) {
        String[] arr = logText.split("\r\n");
        List<String> list = Arrays.asList(arr);
        List<TransLog> list1 = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (String s : list) {
            if (s.indexOf("完成处理") != -1) {
                try {
                    TransLog transLog = new TransLog();
                    transLog.setId(UUID.randomUUID().toString().replace("-", ""));
                    transLog.setTransname(trans.getTransName());
                    transLog.setAdmdivcode(param.get("region"));
                    if(param.get("gz")!=null){
                        transLog.setAdmdivcode(param.get("region")+param.get("gz"));
                    }
                    String[] arr2 = s.split("-");
                    transLog.setTime(sdf.parse(arr2[0].trim()));
                    transLog.setStepname(arr2[1].trim());
                    if (s.indexOf("读取") != -1) {
                        transLog.setType(1);
                    } else if (s.indexOf("写入") != -1) {
                        transLog.setType(2);
                    } else {
                        transLog.setType(3);
                    }
                    String quStr = s.substring(s.indexOf("(") + 1, s.indexOf(")")).trim();
                    String[] arr3 = quStr.split(",");
                    if (trans.getCategoryId() != null) {
                        transLog.setCategoryId(trans.getCategoryId());
                    }
                    transLog.setI(Integer.valueOf(arr3[0].substring(2).trim()));
                    transLog.setO(Integer.valueOf(arr3[1].substring(3).trim()));
                    transLog.setR(Integer.valueOf(arr3[2].substring(3).trim()));
                    transLog.setW(Integer.valueOf(arr3[3].substring(3).trim()));
                    transLog.setU(Integer.valueOf(arr3[4].substring(3).trim()));
                    transLog.setE(Integer.valueOf(arr3[5].substring(3).trim()));
                    list1.add(transLog);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        transLogRepository.saveAll(list1);

    }

    public CountSumRes count(CountSumReq countSumReq, Pageable pageable) {

        if (countSumReq.getMark() != null && countSumReq.getMark() == CountMark.TODAY.getKey()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, -24);
            countSumReq.setTime(dateFormat.format(calendar.getTime()));
            CountSumRes yestCount = historySum(countSumReq, pageable);
            countSumReq.setTime(dateFormat.format(new Date()));
            CountSumRes todayCount = historySum(countSumReq, pageable);
            todayCount.setTotal(todayCount.getTotal() - yestCount.getTotal());
            return todayCount;
        }
        CountSumRes history = historySum(countSumReq, pageable);
        return history;
    }

    public CountSumRes historySum(CountSumReq countSumReq, Pageable pageable) {
        CountSumRes countSumRes = new CountSumRes();
        String selectSql = "select IFNULL(sum(w),0) as sum from(";
        String selectSql1 = "SELECT a.* ";
        StringBuffer fromsql = new StringBuffer(" FROM k_log a INNER join ( SELECT admdivcode||stepname names, max(time) maxtime FROM  k_log ");
        if (countSumReq.getMark() == CountMark.TODAY.getKey()) {
        fromsql.append(" where TO_CHAR(time, 'YYYY-MM-DD' ) = '" + countSumReq.getTime() + "'");
        }
        fromsql.append("GROUP BY admdivcode || stepname )b ON a.admdivcode || stepname = b.names AND a.time = b.maxtime where a.type = 2");
        if (countSumReq.getAdmdivcode() != null) {
            fromsql.append(" and a.admdivcode='" + countSumReq.getAdmdivcode()+"'");
            countSumRes.setAdmdivcode(countSumReq.getAdmdivcode());
        }
        if (countSumReq.getCategoryId() != null) {
            fromsql.append(" and a.category_Id =" + countSumReq.getCategoryId());
            countSumRes.setCategoryId(countSumReq.getCategoryId());
        }
        if (countSumReq.getStepname() != null) {
            fromsql.append(" and a.stepname like '" + countSumReq.getStepname() + "%'");
            countSumRes.setStepname(countSumReq.getStepname());
        }
        if (countSumReq.getMark() != null) {
            countSumRes.setMark(countSumReq.getMark());
        }
        if (countSumReq.getType() != null) {
            countSumRes.setType(countSumReq.getType());
        }
        String ordersql = " order by a.time desc";
        String end = ")";
        CountSumBo result = entityManagerUtil.executeNativeQueryForOne(selectSql.concat(selectSql1).concat(fromsql.toString()).concat(ordersql).concat(end), CountSumBo.class);
        countSumRes.setTotal(result.getSum());
        if (countSumReq.getType() == CountType.DETAIL.getKey()) {
            NativeQueryResultBO resultBo = entityManagerUtil.executeNativeQueryForList(selectSql1, fromsql.toString(), ordersql, pageable, TransLog.class);
            countSumRes.setTransLogPageOut(new PageOut<>(resultBo.getResultList(), pageable.getPageNumber(), pageable.getPageSize(), resultBo.getTotal()));
            return countSumRes;
        }
        return countSumRes;
    }

}
