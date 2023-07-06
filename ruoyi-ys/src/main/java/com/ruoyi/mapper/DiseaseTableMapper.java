package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.Dto.MonthCountDto;
import com.ruoyi.Entity.DiseaseTableEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 15500
* @description 针对表【disease_table】的数据库操作Mapper
* @createDate 2023-01-15 14:41:22
* @Entity generator.domain.DiseaseTable
*/
@Mapper
public interface DiseaseTableMapper extends BaseMapper<DiseaseTableEntity> {

    /**
     * 查询病单列表
     *
     * @param diseaseTableEntity 病单管理
     * @return 病单集合
     */
    List<DiseaseTableEntity> selectList(DiseaseTableEntity diseaseTableEntity);

    List<MonthCountDto> listCount();
}
