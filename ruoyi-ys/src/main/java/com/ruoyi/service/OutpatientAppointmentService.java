package com.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.Entity.OutpatientAppointmentEntity;

import java.util.List;

/**
* @author 15500
* @description 针对表【outpatient_appointment(预约信息表)】的数据库操作Service
* @createDate 2023-01-10 20:05:08
*/
public interface OutpatientAppointmentService extends IService<OutpatientAppointmentEntity> {

    int insert(OutpatientAppointmentEntity outpatientAppointment);

    List<OutpatientAppointmentEntity> selectList(OutpatientAppointmentEntity outpatientAppointment);

    OutpatientAppointmentEntity selectById(String id);
}
