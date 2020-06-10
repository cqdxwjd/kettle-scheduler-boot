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
    private Integer total;
    /**
     * 明细
     */
    @ApiModelProperty(value = "明细")
    private PageOut transLogPageOut;
}
