package com.ruoyi.Dto;

import com.ruoyi.Entity.LabelTableEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DiseaseVo {
    /**
     * 体温
     */
    private String temperature;

    /**
     * 医师id
     */
    private String f1;
    /**
     * 医师名称
     */
    private String f2;
    /**
     * 预约信息id
     */
    private String f3;
    /**
     * 血压
     */
    private String bloodPressure;

    /**
     * 心率
     */
    private String heartRate;

    /**
     * 主述
     */
    private String mainStatement;

    /**
     * 往病史/过敏信息
     */
    private String lastMedical;

    /**
     * 医师诊断
     */
    private String diagnosis;

    /**
     * 就诊姓名
     */
    private String visitName;

    /**
     * 就诊性别
     */
    private String visitSex;

    /**
     * 就诊id
     */
    private Integer visitId;

    /**
     * 科室id
     */
    private Integer deptId;

    /**
     * 科室
     */
    private String deptName;

    private List<LabelTableEntity> labelList;
    /**
     * 年龄
     */
    private String visitAge;

}
