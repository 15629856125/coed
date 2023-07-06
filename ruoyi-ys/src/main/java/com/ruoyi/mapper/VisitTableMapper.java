package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.Entity.VisitTableEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 15500
* @description 针对表【visit_table(就诊人信息表)】的数据库操作Mapper
* @createDate 2023-01-08 15:26:59
* @Entity generator.domain.VisitTable
*/
@Mapper
public interface VisitTableMapper extends BaseMapper<VisitTableEntity> {
    List<VisitTableEntity> selectVisitTableList(VisitTableEntity visitTableEntity);
}
