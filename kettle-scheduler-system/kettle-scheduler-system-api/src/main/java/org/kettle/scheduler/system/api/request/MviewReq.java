package org.kettle.scheduler.system.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kettle.scheduler.system.api.basic.BasicVO;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "物化视图-入参")
public class MviewReq extends BasicVO implements Serializable {
    
    @ApiModelProperty(value = "视图所有者")
    private String OWNER;
    @ApiModelProperty(value = "物化视图名称")
    private String MVIEW_NAME;
    @ApiModelProperty(value = "刷新方式")
    private String REFRESH_MODE;
    @ApiModelProperty(value = "刷新类型")
    private String REFRESH_METHOD;
    @ApiModelProperty(value = "是否可刷新")
    private String FAST_REFRESHABLE;
    @ApiModelProperty(value = "最近一次刷新类型")
    private String LAST_REFRESH_TYPE;
    @ApiModelProperty(value = "最近一次刷新时间")
    private Date LAST_REFRESH_DATE;
    @ApiModelProperty(value = "数据是否过时")
    private String STALENESS;
}
