package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.entity.BaseOrderEntity;

import java.time.LocalDateTime;

/**
 * 职务
 *
 * @author guer
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("职务")
public class DutyDTO extends BaseOrderEntity<DutyDTO> {

    /**
     * 职务ID
     */
    @ApiModelProperty("职务ID")
    private Long dutyId;

    /**
     * 职务名称
     */
    @ApiModelProperty("职务名称")
    private String dutyName;

    /**
     * 说明
     */
    @ApiModelProperty("说明")
    private String remark;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
