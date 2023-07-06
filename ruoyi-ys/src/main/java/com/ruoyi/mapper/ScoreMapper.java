package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.Entity.ScoreEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 15500
* @description 针对表【score】的数据库操作Mapper
* @createDate 2023-01-24 17:47:38
* @Entity generator.domain.Score
*/
@Mapper
public interface ScoreMapper extends BaseMapper<ScoreEntity> {
    /**
     * 获取评价信息列表
     * @param scoreEntity
     * @return
     */
    List<ScoreEntity> selectScoreList(ScoreEntity scoreEntity);
}
