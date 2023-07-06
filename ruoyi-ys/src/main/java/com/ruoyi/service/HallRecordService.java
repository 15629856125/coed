package com.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.Entity.HallRecordEntity;
import com.ruoyi.Entity.OutpatientAppointmentEntity;

import java.util.List;

/**
* @author 15500
* @description 针对表【hall_record(就诊大厅记录表)】的数据库操作Service
* @createDate 2023-01-18 19:00:03
*/
public interface HallRecordService extends IService<HallRecordEntity> {

    int insertHallRecordEntityList(OutpatientAppointmentEntity outpatientAppointment) throws Exception;

    List<HallRecordEntity> hallRecordList(HallRecordEntity hallRecord);
}
