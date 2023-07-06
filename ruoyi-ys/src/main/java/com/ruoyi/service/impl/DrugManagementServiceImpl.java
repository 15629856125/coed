package com.ruoyi.service.impl;
import java.util.List;
import com.ruoyi.Entity.DrugManagementEntity;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.mapper.DrugManagementMapper;
import com.ruoyi.service.IDrugManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 药品管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-24
 */
@Service
public class DrugManagementServiceImpl implements IDrugManagementService
{
    @Autowired
    private DrugManagementMapper drugManagementEntityMapper;
    /**
     * 查询药品管理
     * 
     * @param id 药品管理主键
     * @return 药品管理
     */
    @Override
    public DrugManagementEntity selectDrugManagementEntityById(String id){
        return drugManagementEntityMapper.selectDrugManagementEntityById(id);
    }

    /**
     * 查询药品管理列表
     * 
     * @param drugManagementEntity 药品管理
     * @return 药品管理
     */
    @Override
    public List<DrugManagementEntity> selectDrugManagementEntityList(DrugManagementEntity drugManagementEntity)
    {
        return drugManagementEntityMapper.selectDrugManagementEntityList(drugManagementEntity);
    }

    /**
     * 新增药品管理
     * 
     * @param drugManagementEntity 药品管理
     * @return 结果
     */
    @Override
    public int insertDrugManagementEntity(DrugManagementEntity drugManagementEntity){
        String uId = IdUtils.simpleUUID();
        drugManagementEntity.setId(uId);
        return drugManagementEntityMapper.insert(drugManagementEntity);
    }

    /**
     * 修改药品管理
     * 
     * @param drugManagementEntity 药品管理
     * @return 结果
     */
    @Override
    public int updateDrugManagementEntity(DrugManagementEntity drugManagementEntity)
    {
        return drugManagementEntityMapper.updateDrugManagementEntity(drugManagementEntity);
    }

    /**
     * 批量删除药品管理
     * 
     * @param ids 需要删除的药品管理主键
     * @return 结果
     */
    @Override
    public int deleteDrugManagementEntityByIds(String[] ids)
    {
        return drugManagementEntityMapper.deleteDrugManagementEntityByIds(ids);
    }

    /**
     * 删除药品管理信息
     * 
     * @param id 药品管理主键
     * @return 结果
     */
    @Override
    public int deleteDrugManagementEntityById(String id)
    {
        return drugManagementEntityMapper.deleteDrugManagementEntityById(id);
    }
}
