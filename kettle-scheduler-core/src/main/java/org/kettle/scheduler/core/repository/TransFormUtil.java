package org.kettle.scheduler.core.repository;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.kettle.scheduler.core.dto.SetpMetaDTO;
import org.kettle.scheduler.core.dto.TransSetpDTO;
import org.kettle.scheduler.core.dto.common.DatabaseMetaDTO;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Trans工具类，主要用于数据格式转换等
 *
 * @author chenzhao
 */
@Log4j2
public class TransFormUtil {

    /**
     * 数据转换入口类
     *
     * @param type      类型，Job:Trans
     * @param transMeta 转换属性
     * @return
     */
    public static JSONObject dataTransform(String type, TransMeta transMeta) {
        JSONObject jsonObject = new JSONObject();
        if (type.equals("job")) {

        } else if (type.equals("trans")) {
            List<TransSetpDTO> transSetpDTOS = transStepsTransform(transMeta.getSteps());
            jsonObject.put("transName", transMeta.getName());
            jsonObject.put("setpList",transSetpDTOS);
        }
        return jsonObject;
    }


    /**
     * 转换步骤
     *
     * @param stepMetaList
     * @return
     */
    public static List<TransSetpDTO> transStepsTransform(List<StepMeta> stepMetaList) {
        List<TransSetpDTO> setpsList = new ArrayList();
        int i=1;
        if (stepMetaList.size() > 0) {
            stepMetaList.forEach((stepMeta) -> {
                TransSetpDTO transSetpDTO = new TransSetpDTO();
                transSetpDTO.setSetpId(stepMeta.getStepID());
                transSetpDTO.setSetpChangedDate(stepMeta.getChangedDate());
                transSetpDTO.setSetpName(stepMeta.getName());
                //步骤属性替换
                SetpMetaDTO setpMetaDTO = setpTransform(stepMeta.getStepMetaInterface());
                transSetpDTO.setSetpMeta(setpMetaDTO);
                transSetpDTO.setSetpType(stepMeta.getStepID());
                setpsList.add(transSetpDTO);
            });
        }
        return setpsList;
    }

    /**
     * 步骤属性转换
     * @param stepMetaInterface
     * @return
     */
    public static SetpMetaDTO setpTransform(StepMetaInterface stepMetaInterface) {
        SetpMetaDTO setpMetaDTO = new SetpMetaDTO();
        if (stepMetaInterface instanceof TableInputMeta) {
            TableInputMeta tableInputMeta = (TableInputMeta) stepMetaInterface;
            setpMetaDTO.setSql(tableInputMeta.getSQL());
            setpMetaDTO.setDatabaseMeta(databaseTransform(tableInputMeta.getDatabaseMeta()));
            //setpMetaDTO.setDatabaseMetaList(tableInputMeta.getUsedDatabaseConnections());
        }else if(stepMetaInterface instanceof TableOutputMeta){
            log.info("表输出");
        }
        return setpMetaDTO;
    }

    /**
     * 数据源连接转换
     * @param databaseMeta
     * @return
     */
    public static DatabaseMetaDTO databaseTransform(DatabaseMeta databaseMeta) {
        DatabaseMetaDTO databaseMetaDTO = new DatabaseMetaDTO();
        databaseMetaDTO.setDatabaseName(databaseMeta.getDatabaseName());
        databaseMetaDTO.setDatabasePortNumberString(databaseMeta.getDatabasePortNumberString());
        databaseMetaDTO.setPluginId(databaseMeta.getPluginId());
        databaseMetaDTO.setDisplayName(databaseMeta.getDisplayName());
        databaseMetaDTO.setDriverClass(databaseMeta.getDriverClass());
        databaseMetaDTO.setHostname(databaseMeta.getHostname());
        databaseMetaDTO.setName(databaseMeta.getName());
        databaseMetaDTO.setObjectId(databaseMeta.getObjectId().getId());
        databaseMetaDTO.setPassword(databaseMeta.getPassword());
        databaseMetaDTO.setUsername(databaseMeta.getUsername());
        return databaseMetaDTO;
    }
}
