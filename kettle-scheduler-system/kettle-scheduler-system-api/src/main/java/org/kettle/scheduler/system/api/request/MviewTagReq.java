package org.kettle.scheduler.system.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-27 13:31
 */
@Data
@ApiModel(value = "物化视图标签-入参")
public class MviewTagReq implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "标签编码")
    private String mview_Tag_Code;

    @ApiModelProperty(value = "标签名称")
    private String mview_Tag_Name;

    @ApiModelProperty(value = "父级ID")
    private String parent_Id;
}
