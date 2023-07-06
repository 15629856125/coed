package com.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.Entity.ScoreEntity;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 15500
* @description 针对表【score】的数据库操作Service
* @createDate 2023-01-24 17:47:38
*/
public interface ScoreService extends IService<ScoreEntity> {

    int insertScore(ScoreEntity scoreEntity) throws Exception;

    @Transactional(rollbackFor={Exception.class,RuntimeException.class, ServiceException.class})
    int updateScore(ScoreEntity scoreEntity) throws Exception;

    List<ScoreEntity> selectScoreList(ScoreEntity scoreEntity);

    int deleteScore(String id);
}
