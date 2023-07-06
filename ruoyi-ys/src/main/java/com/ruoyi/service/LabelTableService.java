package com.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.Entity.DiseaseTableEntity;
import com.ruoyi.Entity.LabelTableEntity;

import java.util.List;

/**
* @author 15500
* @description 针对表【label_table】的数据库操作Service
* @createDate 2023-01-16 20:13:13
*/
public interface LabelTableService extends IService<LabelTableEntity> {

    List<LabelTableEntity> selectLabelTableEntityList(DiseaseTableEntity DiseaseTableEntity);
}
