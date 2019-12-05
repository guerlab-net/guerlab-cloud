package net.guerlab.smart.platform.commons.searchparams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.spring.searchparams.OrderByIndex;
import net.guerlab.spring.searchparams.OrderByType;

import javax.persistence.Column;

/**
 * 可排序搜索参数
 *
 * @author guer
 */
@Getter
@Setter
public class OrderSearchParams extends AbstractSearchParams {

    /**
     * 排序值排序方式
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    @Column(name = "orderNum")
    @OrderByIndex(-999)
    private OrderByType orderNumOrderByType = OrderByType.DESC;
}
