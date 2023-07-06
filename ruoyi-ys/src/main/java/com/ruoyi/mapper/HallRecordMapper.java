package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.Entity.DiseaseTableEntity;
import com.ruoyi.Entity.HallRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 15500
* @description 针对表【hall_record(就诊大厅记录表)】的数据库操作Mapper
* @createDate 2023-01-18 19:00:03
* @Entity generator.domain.HallRecord
*/
@Mapper
public interface HallRecordMapper extends BaseMapper<HallRecordEntity> {

    /**
     * 查询就诊大厅记录
     * @param hallRecord
     */
    List<HallRecordEntity> hallRecordList (HallRecordEntity hallRecord);
}
