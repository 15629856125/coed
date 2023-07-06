package com.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.Dto.DiseaseVo;
import com.ruoyi.Dto.MonthCountDto;
import com.ruoyi.Entity.DiseaseTableEntity;

import java.util.List;

/**
* @author 15500
* @description 针对表【disease_table】的数据库操作Service
* @createDate 2023-01-15 14:41:22
*/
public interface DiseaseTableService extends IService<DiseaseTableEntity> {

    int insertDiseaseTableEntity(DiseaseVo diseaseVo) throws Exception;

    List<DiseaseTableEntity> selectDiseaseTableEntityList(DiseaseTableEntity diseaseTableEntity);

    int updateDiseaseTableEntity(String id) throws Exception;

    List<MonthCountDto> listCount();
}
