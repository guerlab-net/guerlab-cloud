package net.guerlab.smart.platform.user.api;

import net.guerlab.smart.platform.user.core.domain.DutyDTO;
import net.guerlab.smart.platform.user.core.searchparams.DutySearchParams;
import net.guerlab.web.result.ListObject;

import java.util.List;

/**
 * 职务服务接口
 *
 * @author guer
 */
public interface DutyApi {

    /**
     * 根据职务id查询职务
     *
     * @param dutyId
     *         职务id
     * @return 职务
     */
    DutyDTO findOne(Long dutyId);

    /**
     * 根据搜索参数查询职务列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职务列表
     */
    ListObject<DutyDTO> findList(DutySearchParams searchParams);

    /**
     * 根据搜索参数查询职务列表
     *
     * @param searchParams
     *         搜索参数
     * @return 职务列表
     */
    List<DutyDTO> findAll(DutySearchParams searchParams);
}
