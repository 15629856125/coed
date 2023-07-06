package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.Entity.DrugManagementEntity;
import com.ruoyi.Entity.OutpatientAppointmentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 预约信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-01-10
 */
@Mapper
public interface OutpatientAppointmentMapper extends BaseMapper<OutpatientAppointmentEntity> {
    /**
     * 查询药品管理列表
     *
     * @param outpatientAppointment 药品管理
     * @return 药品管理集合
     */
    List<OutpatientAppointmentEntity> selectEntityList(OutpatientAppointmentEntity outpatientAppointment);
}
