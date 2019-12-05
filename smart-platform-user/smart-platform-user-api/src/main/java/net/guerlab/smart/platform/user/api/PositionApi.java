package net.guerlab.smart.platform.user.api;

import net.guerlab.smart.platform.user.core.domain.PositionDTO;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
import net.guerlab.web.result.ListObject;

import java.util.List;

/**
 * 职位服务接口
 *
 * @author guer
 */
public interface PositionApi {

    /**
     * 根据职位id查询职位
     *
     * @param positionId
     *         职位id
     * @return 职位
     */
    PositionDTO findOne(Long positionId);

    /**
     * 根据搜索参数查询职位列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职位列表
     */
    ListObject<PositionDTO> findList(PositionSearchParams searchParams);

    /**
     * 根据搜索参数查询职位列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职位列表
     */
    List<PositionDTO> findAll(PositionSearchParams searchParams);
}
