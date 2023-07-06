package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.Dto.DiseaseVo;
import com.ruoyi.Dto.MonthCountDto;
import com.ruoyi.Entity.*;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.mapper.*;
import com.ruoyi.service.DiseaseTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
* @author 15500
* @description 针对表【disease_table】的数据库操作Service实现
* @createDate 2023-01-15 14:41:22
*/
@Service
public class DiseaseTableServiceImpl extends ServiceImpl<DiseaseTableMapper, DiseaseTableEntity>
implements DiseaseTableService {
    @Autowired
    private DiseaseTableMapper diseaseTableMapper;
    @Autowired
    private LabelTableMapper labelTableMapper;
    @Autowired
    private OutpatientAppointmentMapper outpatientAppointmentMapper;
    @Autowired
    private DrugManagementMapper drugManagementMapper;
    @Autowired
    private HallRecordMapper hallRecordMapper;
    /**
     * 新增病情单管理
     *
     * @param diseaseVo
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor={Exception.class,RuntimeException.class, ServiceException.class})
    public int insertDiseaseTableEntity(DiseaseVo diseaseVo) throws Exception {
        try{
            String uId = IdUtils.simpleUUID();
            Date time=new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            String format = sdf.format(time);
            int month = Integer.parseInt(format);
            System.out.println(month);
            Date firstDayOfMonth = getFirstDayOfMonth(month);
            System.out.println(firstDayOfMonth);
            DiseaseTableEntity diseaseTableEntity=new DiseaseTableEntity();
            diseaseTableEntity.setId(uId);
            diseaseTableEntity.setTemperature(diseaseVo.getTemperature());
            diseaseTableEntity.setBloodPressure(diseaseVo.getBloodPressure());
            diseaseTableEntity.setHeartRate(diseaseVo.getHeartRate());
            diseaseTableEntity.setMainStatement(diseaseVo.getMainStatement());
            diseaseTableEntity.setLastMedical(diseaseVo.getLastMedical());
            diseaseTableEntity.setDiagnosis(diseaseVo.getDiagnosis());
            diseaseTableEntity.setF1(diseaseVo.getF1());
            diseaseTableEntity.setF2(diseaseVo.getF2());
            diseaseTableEntity.setVisitName(diseaseVo.getVisitName());
            diseaseTableEntity.setVisitSex(diseaseVo.getVisitSex());
            diseaseTableEntity.setVisitId(diseaseVo.getVisitId());
            diseaseTableEntity.setDeptId(diseaseVo.getDeptId());
            diseaseTableEntity.setDeptName(diseaseVo.getDeptName());
            diseaseTableEntity.setCreateTime(time);
            diseaseTableEntity.setF3("0");
            diseaseTableEntity.setScoreStatus("0");
            diseaseTableEntity.setCountTime(firstDayOfMonth);
            diseaseTableEntity.setF5(diseaseVo.getVisitAge());
            if(diseaseVo.getLabelList().size()==0){
                diseaseTableEntity.setF4("1");
            }else {
                diseaseTableEntity.setF4("0");
            }
            for (int i=0;i<diseaseVo.getLabelList().size();i++){
                diseaseVo.getLabelList().get(i).setDiseaseId(uId);
                int rult=labelTableMapper.insert(diseaseVo.getLabelList().get(i));
            }
            OutpatientAppointmentEntity outpatientAppointment=outpatientAppointmentMapper.selectById(diseaseVo.getF3());
            outpatientAppointment.setStatus("3");
            outpatientAppointmentMapper.updateById(outpatientAppointment);
            HallRecordEntity hallRecordEntity=hallRecordMapper.selectOne(new QueryWrapper<HallRecordEntity>().eq("record_id",diseaseVo.getF3()));
            if(hallRecordEntity==null){
                throw new Exception("请叫号后等待病患到来,再提交信息");
            }
            hallRecordEntity.setStatue("1");
            hallRecordMapper.updateById(hallRecordEntity);
            return diseaseTableMapper.insert(diseaseTableEntity);
        }catch (Exception e){
            if(e!=null){
                throw new Exception(e.getMessage());
            }
            throw new Exception("出错了,请联系管理人员");
        }
    }

    /**
     * 查询病单列表
     *
     * @param diseaseTableEntity 病单管理
     * @return
     */
    @Override
    public List<DiseaseTableEntity> selectDiseaseTableEntityList(DiseaseTableEntity diseaseTableEntity)
    {
        return diseaseTableMapper.selectList(diseaseTableEntity);
    }

    /**
     * 修改病单
     *
     * @param id 病单管理
     * @return
     */
    @Override
    @Transactional(rollbackFor={Exception.class,RuntimeException.class, ServiceException.class})
    public int updateDiseaseTableEntity(String id) throws Exception {
        DiseaseTableEntity diseaseTableEntity= diseaseTableMapper.selectById(id);
        diseaseTableEntity.setF3("1");
        List<LabelTableEntity> labelTableList=labelTableMapper.selectList(new QueryWrapper<LabelTableEntity>().eq("disease_id",diseaseTableEntity.getId()));
        for (int i=0;i<labelTableList.size();i++){
            DrugManagementEntity drugManagementEntity=drugManagementMapper.selectById(labelTableList.get(i).getDrugId());
            long number= drugManagementEntity.getDrugStock()-labelTableList.get(i).getDrugNumber();
            if (number<0){
                throw new Exception("药品库存不足");
            }
            drugManagementEntity.setDrugStock(number);
            drugManagementMapper.updateById(drugManagementEntity);
        }
        return diseaseTableMapper.updateById(diseaseTableEntity);
    }
    /**
     * 获取一年以内的就诊次数
     */
    @Override
    public List<MonthCountDto> listCount() {
        List<MonthCountDto> list = diseaseTableMapper.listCount();
        return list;
    }


    /**
     * 获取当前月第一天
     *
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        // 设置月份
        calendar.set(Calendar.MONTH, month - 1);
        // 获取某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date firstDayDate = sdf.format(calendar.getTime());
        return calendar.getTime();
    }
}
