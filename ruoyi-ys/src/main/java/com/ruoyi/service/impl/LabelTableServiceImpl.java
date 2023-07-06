package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.Entity.DiseaseTableEntity;
import com.ruoyi.Entity.LabelTableEntity;
import com.ruoyi.mapper.LabelTableMapper;
import com.ruoyi.service.LabelTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15500
* @description 针对表【label_table】的数据库操作Service实现
* @createDate 2023-01-16 20:13:13
*/
@Service
public class LabelTableServiceImpl extends ServiceImpl<LabelTableMapper, LabelTableEntity>
implements LabelTableService {
    @Autowired
    private LabelTableMapper labelTableMapper;

    /**
     * 查询列表
     */
    @Override
    public List<LabelTableEntity> selectLabelTableEntityList(DiseaseTableEntity DiseaseTableEntity)
    {
        return labelTableMapper.selectList(new QueryWrapper<LabelTableEntity>().eq("disease_id",DiseaseTableEntity.getId()));
    }
}
