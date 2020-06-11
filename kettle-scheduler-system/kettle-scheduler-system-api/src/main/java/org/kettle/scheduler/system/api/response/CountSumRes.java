package org.kettle.scheduler.system.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kettle.scheduler.common.povo.PageOut;


@Data
@ApiModel(value = "数据合计-出参")
public class CountSumRes {
    private static final long serialVersionUID = 1L;
    /**
     * 合计
     */
    @ApiModelProperty(value = "合计")
    private Long total;
    /**
     * 区划
     */
    @ApiModelProperty(value = "区划")
    private String admdivcode;
    /**
     * 时间
     */
    @ApiModelProperty(value = "时间")
    private String time;
    /**
     * 步骤名
     */
    @ApiModelProperty(value = "步骤名")
    private String stepname;
    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Integer categoryId;

    /**
     * 类型
     * 1查看总数
     * 2查看明细
     *
     */
    @ApiModelProperty(value = "类型")
    private Integer type;
    /**
     * 类型
     * 1.今天新增条数
     * 2.历史总条数
     */
    @ApiModelProperty(value = "标识")
    private Integer mark=1;
    /**
     * 明细
     */
    @ApiModelProperty(value = "明细")
    private PageOut transLogPageOut;
}
