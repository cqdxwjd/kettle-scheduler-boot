package org.kettle.scheduler.system.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel(value = "合计-入参")
public class CountSumReq implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 区划编码
     */
    @ApiModelProperty(value = "区划编码")
    private String admdivcode;
    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Integer categoryId;
    /**
     * 步骤名
     */
    @ApiModelProperty(value = "步骤名")
    private String stepname;
    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private String time;
    /**
     * 类型
     * 1.只要合计
     * 2.查看详细
     */
    @ApiModelProperty(value = "类型",example = "1.只要合计2.查看详细")
    private Integer type=1;
    /**
     * 标识
     * 1.今天新增条数
     * 2.历史总条数
     */
    @ApiModelProperty(value = "标识",example = "1.今天新增条数 2.历史总条数")
    private Integer mark=1;

}
