package com.ruoyi.service;

import java.util.List;

import com.ruoyi.Entity.DrugManagementEntity;

/**
 * 药品管理Service接口
 * 
 * @author ruoyi
 * @date 2022-12-24
 */
public interface IDrugManagementService
{
    /**
     * 查询药品管理
     * 
     * @param id 药品管理主键
     * @return 药品管理
     */
    DrugManagementEntity selectDrugManagementEntityById(String id);

    /**
     * 查询药品管理列表
     * 
     * @param drugManagementEntity 药品管理
     * @return 药品管理集合
     */
    List<DrugManagementEntity> selectDrugManagementEntityList(DrugManagementEntity drugManagementEntity);

    /**
     * 新增药品管理
     * 
     * @param drugManagementEntity 药品管理
     * @return 结果
     */
    int insertDrugManagementEntity(DrugManagementEntity drugManagementEntity);

    /**
     * 修改药品管理
     * 
     * @param drugManagementEntity 药品管理
     * @return 结果
     */
    int updateDrugManagementEntity(DrugManagementEntity drugManagementEntity);

    /**
     * 批量删除药品管理
     * 
     * @param ids 需要删除的药品管理主键集合
     * @return 结果
     */
    int deleteDrugManagementEntityByIds(String[] ids);

    /**
     * 删除药品管理信息
     * 
     * @param id 药品管理主键
     * @return 结果
     */
    int deleteDrugManagementEntityById(String id);
}
