package com.ruoyi.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * &#064;TableName  disease_table
 */
@TableName(value ="disease_table")
@Data
public class DiseaseTableEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * 体温
     */
    private String temperature;

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
     * 往病史
     */
    private String lastMedical;

    /**
     * 医师诊断
     */
    private String diagnosis;

    /**
     * 医师id
     */
    private String f1;

    /**
     * 医师姓名
     */
    private String f2;

    /**
     * 状态0=待取药，1=已完成
     */
    private String f3;

    /**
     * 是否存在药品用于判断是否拿药0=有 1=无
     */
    private String f4;

    /**
     * 就诊年龄
     */
    private String f5;

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

    private Date createTime;

    private String scoreStatus;

    private Date countTime;
    /**
     * 科室
     */
    private String deptName;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}