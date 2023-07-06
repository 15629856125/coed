package com.ruoyi.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName label_table
 */
@TableName(value ="label_table")
@Data
public class LabelTableEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * 病单id
     */
    private String diseaseId;

    /**
     * 药品id
     */
    private String drugId;

    /**
     * 药品名称
     */
    private String drugName;

    /**
     * 药品规格
     */
    private String drugSpecs;

    /**
     * 药品数量
     */
    private Integer drugNumber;

    /**
     * 用法用量
     */
    private String drugConsumption;

    /**
     * 
     */
    private String f1;

    /**
     * 
     */
    private String f2;

    /**
     * 
     */
    private String f3;

    /**
     * 
     */
    private String f4;

    /**
     * 
     */
    private String f5;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}