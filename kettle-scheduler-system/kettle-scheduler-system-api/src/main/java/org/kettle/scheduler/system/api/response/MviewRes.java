package org.kettle.scheduler.system.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-26 18:03
 */
@Data
@ApiModel(value = "物化视图表-出参")
public class MviewRes implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所有者")
    private String owner;

    @ApiModelProperty(value = "视图名称")
    private String mviewName;

    @ApiModelProperty(value = "最后刷新时间")
    private Date lastRefreshDate;

    @ApiModelProperty(value = "刷新方法")
    private String refreshMethod;

    @ApiModelProperty(value = "是否失效")
    private String invalid;

    @ApiModelProperty(value = "物化视图标签ID")
    private String mviewTagId;

    @ApiModelProperty(value = "物化视图查询语句")
    private String query;

    @ApiModelProperty(value = "是否启用：1启用、0停用")
    private String status;
}
