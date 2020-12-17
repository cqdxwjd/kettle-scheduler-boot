package org.kettle.scheduler.system.biz.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.util.StringUtils;
import org.kettle.scheduler.common.enums.GlobalStatusEnum;
import org.kettle.scheduler.common.exceptions.MyMessageException;
import org.kettle.scheduler.common.groups.Insert;
import org.kettle.scheduler.common.groups.Update;
import org.kettle.scheduler.common.povo.PageOut;
import org.kettle.scheduler.common.povo.QueryHelper;
import org.kettle.scheduler.common.povo.Result;
import org.kettle.scheduler.common.utils.FileUtil;
import org.kettle.scheduler.common.utils.StringUtil;
import org.kettle.scheduler.common.utils.ValidatorUtil;
import org.kettle.scheduler.core.enums.RepTypeEnum;
import org.kettle.scheduler.system.api.api.SysTransApi;
import org.kettle.scheduler.system.api.enums.RunTypeEnum;
import org.kettle.scheduler.system.api.request.TransReq;
import org.kettle.scheduler.system.api.response.TransRes;
import org.kettle.scheduler.core.constant.KettleConfig;
import org.kettle.scheduler.system.biz.entity.Trans;
import org.kettle.scheduler.system.biz.service.SysRepositoryService;
import org.kettle.scheduler.system.biz.service.SysTransService;
import org.kettle.scheduler.system.biz.utils.TransPreviewProgress;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.repository.AbstractRepository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.TransPreviewFactory;
import org.pentaho.di.trans.step.StepMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.groups.Default;
import java.awt.*;
import java.util.List;

/**
 * 转换管理API
 *
 * @author lyf
 */
@RestController
public class SysTransApiController implements SysTransApi {

    private final SysTransService transService;
    @Autowired
    private SysRepositoryService repositoryService;

    public SysTransApiController(SysTransService transService) {
        this.transService = transService;
    }

    private void validatedParam(TransReq req) {
        switch (RunTypeEnum.getEnum(req.getTransType())) {
            case FILE:
                String result1 = ValidatorUtil.validateWithString(req, TransReq.File.class);
                if (!StringUtil.isEmpty(result1)) {
                    throw new MyMessageException(GlobalStatusEnum.ERROR_PARAM, result1);
                }
                break;
            case REP:
                String result2 = ValidatorUtil.validateWithString(req, TransReq.Rep.class);
                if (!StringUtil.isEmpty(result2)) {
                    throw new MyMessageException(GlobalStatusEnum.ERROR_PARAM, result2);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + RepTypeEnum.getEnum(req.getTransType()));
        }
    }

    /**
     * 添加转换
     *
     * @param req {@link TransReq}
     * @return {@link Result}
     */
    @Override
    public Result add(@Validated({Insert.class, Default.class}) TransReq req, MultipartFile transFile) {
        // 参数验证
        validatedParam(req);
        // 保存上传文件
        if (RunTypeEnum.FILE.getCode().equals(req.getTransType())) {
            if (transFile == null || transFile.isEmpty()) {
                throw new MyMessageException(GlobalStatusEnum.ERROR_PARAM, "上传文件不能为空");
            }
            req.setTransPath(FileUtil.uploadFile(transFile, KettleConfig.uploadPath));
        }
        transService.add(req);
        return Result.ok();
    }

    /**
     * 通过id删除转换
     *
     * @param id 要删除的数据的id
     * @return {@link Result}
     */
    @Override
    public Result delete(Integer id) {
        transService.delete(id);
        return Result.ok();
    }

    /**
     * 批量删除转换
     *
     * @param ids 要删除数据的{@link List}集
     * @return {@link Result}
     */
    @Override
    public Result deleteBatch(List<Integer> ids) {
        transService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 更新转换
     *
     * @param req {@link TransReq}
     * @return {@link Result}
     */
    @Override
    public Result update(@Validated({Update.class, Default.class}) TransReq req) {
        transService.update(req);
        return Result.ok();
    }

    /**
     * 根据条件查询转换列表
     *
     * @param req {@link QueryHelper}
     * @return {@link Result}
     */
    @Override
    public Result<PageOut<TransRes>> findTransListByPage(QueryHelper<TransReq> req) {
        return Result.ok(transService.findTransListByPage(req.getQuery(), req.getPage().getPageable()));
    }

    /**
     * 查询转换明细
     *
     * @param id 根据id查询
     * @return {@link Result}
     */
    @Override
    public Result<TransRes> getTransDetail(Integer id) {
        return Result.ok(transService.getTransDetail(id));
    }

    /**
     * 全部启动
     *
     * @return {@link Result}
     */
    @Override
    public Result startAllTrans() {
        transService.startAllTrans();
        return Result.ok();
    }

    /**
     * 单个启动
     *
     * @param id 根据id查询
     * @return {@link Result}
     */
    @Override
    public Result startTrans(Integer id) {
        transService.startTrans(id);
        return Result.ok();
    }

    /**
     * 全部停止
     *
     * @return {@link Result}
     */
    @Override
    public Result stopAllTrans() {
        transService.stopAllTrans();
        return Result.ok();
    }

    /**
     * 单个停止
     *
     * @param id 根据id查询
     * @return {@link Result}
     */
    @Override
    public Result stopTrans(Integer id) {
        transService.stopTrans(id);
        return Result.ok();
    }

    /**
     * 验证名称是否存在
     *
     * @param transName 转换名
     * @return 只能返回true或false
     */
    @Override
    public String transNameExist(String transName) {
        if (StringUtil.isEmpty(transName)) {
            return "true";
        } else {
            Trans trans = transService.getByTransName(transName);
            if (trans != null) {
                return "false";
            } else {
                return "true";
            }
        }
    }


    /**
     * 预览数据
     *
     * @param transRepositoryId
     * @param scriptPath
     * @param stepName          步骤名称
     * @param rowLimit          预览条数
     * @return
     * @throws Exception
     */
    @Override
    public Result<JSONObject> previewData(String transRepositoryId, String scriptPath, String scriptName, String stepName, int rowLimit) throws Exception {
        AbstractRepository abstractRepository = repositoryService.getAbstractRepository(Integer.valueOf(transRepositoryId));
        RepositoryDirectoryInterface rdi = abstractRepository.loadRepositoryDirectoryTree().findDirectory(FileUtil.getParentPath(scriptPath));
        // 在指定资源库的目录下找到要执行的转换
        TransMeta transMeta = abstractRepository.loadTransformation(scriptName, rdi, new ProgressNullMonitorListener(), true, null);
        StepMeta stepMeta = getStep(transMeta, stepName);
        TransMeta previewMeta = TransPreviewFactory.generatePreviewTransformation(transMeta, stepMeta.getStepMetaInterface(), stepName);
        TransPreviewProgress progresser = new TransPreviewProgress(previewMeta, new String[]{stepName}, new int[]{rowLimit});

        RowMetaInterface rowMeta = progresser.getPreviewRowsMeta(stepName);
        List<Object[]> rowsData = progresser.getPreviewRows(stepName);

        Font f = new Font("Arial", Font.PLAIN, 12);
        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(f);
        JSONObject jsonObject = new JSONObject();
        if (rowMeta != null) {
            List<ValueMetaInterface> valueMetas = rowMeta.getValueMetaList();

            int width = 0;
            JSONArray columns = new JSONArray();
            JSONObject metaData = new JSONObject();
            JSONArray fields = new JSONArray();
            for (int i = 0; i < valueMetas.size(); i++) {
                ValueMetaInterface valueMeta = rowMeta.getValueMeta(i);
                fields.add(valueMeta.getName());
                String header = valueMeta.getComments() == null ? valueMeta.getName() : valueMeta.getComments();

                int hWidth = fm.stringWidth(header) + 10;
                width += hWidth;
                JSONObject column = new JSONObject();
                column.put("dataIndex", valueMeta.getName());
                column.put("header", header);
                column.put("width", hWidth);
                columns.add(column);
            }
            metaData.put("fields", fields);
            metaData.put("root", "firstRecords");

            JSONArray firstRecords = new JSONArray();
            for (int rowNr = 0; rowNr < rowsData.size(); rowNr++) {
                Object[] rowData = rowsData.get(rowNr);
                JSONObject row = new JSONObject();
                for (int colNr = 0; colNr < rowMeta.size(); colNr++) {
                    String string = null;
                    ValueMetaInterface valueMetaInterface;
                    try {
                        valueMetaInterface = rowMeta.getValueMeta(colNr);
                        if (valueMetaInterface.isStorageBinaryString()) {
                            Object nativeType = valueMetaInterface.convertBinaryStringToNativeType((byte[]) rowData[colNr]);
                            string = valueMetaInterface.getStorageMetadata().getString(nativeType);
                        } else {
                            string = rowMeta.getString(rowData, colNr);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!StringUtils.hasText(string))
                        string = "&lt;null&gt;";

                    ValueMetaInterface valueMeta = rowMeta.getValueMeta(colNr);
                    row.put(valueMeta.getName(), string);
                }
                if (firstRecords.size() <= rowLimit) {
                    firstRecords.add(row);
                }
            }

            jsonObject.put("metaData", metaData);
            jsonObject.put("columns", columns);
            jsonObject.put("firstRecords", firstRecords);
            jsonObject.put("width", width < 1000 ? width : 1000);

        } else {

        }
        return Result.ok(jsonObject);
    }

    public StepMeta getStep(TransMeta transMeta, String label) {
        List<StepMeta> list = transMeta.getSteps();
        for (int i = 0; i < list.size(); i++) {
            StepMeta step = list.get(i);
            if (label.equals(step.getName()))
                return step;
        }
        return null;
    }
}
