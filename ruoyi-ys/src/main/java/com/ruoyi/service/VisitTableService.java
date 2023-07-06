package com.ruoyi.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.Entity.VisitTableEntity;

import java.util.List;


/**
* @author 15500
* @description 针对表【visit_table(就诊人信息表)】的数据库操作Service
* @createDate 2023-01-08 15:26:59
*/
public interface VisitTableService extends IService<VisitTableEntity> {

    VisitTableEntity insertvisit(VisitTableEntity visitTableEntity);

    int removevisit(String id);

    List<VisitTableEntity> selectvisitList(VisitTableEntity visitTableEntity);

    VisitTableEntity selectByid(String id);
}
