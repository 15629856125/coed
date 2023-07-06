package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.Entity.OutpatientAppointmentEntity;
import com.ruoyi.mapper.OutpatientAppointmentMapper;
import com.ruoyi.service.OutpatientAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
* @author 小丁
* @description 针对表【outpatient_appointment(预约信息表)】的数据库操作Service实现
* @createDate 2023-01-10 20:05:08
*/
@Service
public class OutpatientAppointmentServiceImpl extends ServiceImpl<OutpatientAppointmentMapper, OutpatientAppointmentEntity>
implements OutpatientAppointmentService {
    @Autowired
    private OutpatientAppointmentMapper outpatientAppointmentMapper;


    /**
     * 新增预约信息/编辑
     * @return
     */
    @Override
    public int insert(OutpatientAppointmentEntity outpatientAppointment){
        if(Objects.equals(outpatientAppointment.getId(), null)){
            outpatientAppointment.setStatus("1");
            String s="ABCD";
            char[] chars=s.toCharArray();
            Random random=new Random();
            outpatientAppointment.setF1(String.valueOf(chars[random.nextInt(chars.length)]));
            return outpatientAppointmentMapper.insert(outpatientAppointment);
        }
        else {
            return outpatientAppointmentMapper.updateById(outpatientAppointment);
        }
    }
    /**
     * 查询列表
     */
    @Override
    public List<OutpatientAppointmentEntity> selectList(OutpatientAppointmentEntity outpatientAppointment)
    {
        return outpatientAppointmentMapper.selectEntityList(outpatientAppointment);
    }
    /**
     * 查询详情
     * @param id 主键
     */
    @Override
    public OutpatientAppointmentEntity selectById(String id){
        return outpatientAppointmentMapper.selectById(id);
    }
}
