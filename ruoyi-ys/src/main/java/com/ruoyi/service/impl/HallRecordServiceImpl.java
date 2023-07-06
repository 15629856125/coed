package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.Entity.HallRecordEntity;
import com.ruoyi.Entity.OutpatientAppointmentEntity;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.mapper.HallRecordMapper;
import com.ruoyi.service.HallRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
* @author 15500
* @description 针对表【hall_record(就诊大厅记录表)】的数据库操作Service实现
* @createDate 2023-01-18 19:00:03
*/
@Service
public class HallRecordServiceImpl extends ServiceImpl<HallRecordMapper, HallRecordEntity>
implements HallRecordService {

    @Autowired
    private HallRecordMapper hallRecordMapper;


    /**
     * 叫号新增
     */
    @Override
    @Transactional(rollbackFor={Exception.class,RuntimeException.class, ServiceException.class})
    public int insertHallRecordEntityList(OutpatientAppointmentEntity outpatientAppointment) throws Exception {
        HallRecordEntity hallRecord= hallRecordMapper.selectOne(new QueryWrapper<HallRecordEntity>().eq("record_id",outpatientAppointment.getId()));
        if(hallRecord!=null){
            throw new Exception("该病患已被叫号，请耐心等待病患的到来，如10分种未到请再使用暂跳功能");
        }
        HallRecordEntity hallRecordEntity=new HallRecordEntity();
        hallRecordEntity.setCode(outpatientAppointment.getF1());
        hallRecordEntity.setRecordId(outpatientAppointment.getId());
        hallRecordEntity.setStatue("0");
        hallRecordEntity.setVisitId(String.valueOf(outpatientAppointment.getVisitId()));
        hallRecordEntity.setVisitName(outpatientAppointment.getVisitName());
        hallRecordEntity.setUserId(outpatientAppointment.getUserId());
        hallRecordEntity.setUserName(outpatientAppointment.getUserName());
        hallRecordEntity.setCreateTime(new Date());
        return hallRecordMapper.insert(hallRecordEntity);
    }

    @Override
    public List<HallRecordEntity> hallRecordList(HallRecordEntity hallRecord){
        return hallRecordMapper.hallRecordList(hallRecord);
    }
}
