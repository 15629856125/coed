package com.ruoyi.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 就诊人信息表
 * &#064;TableName  visit_table
 * @author 15500
 */
@TableName(value ="visit_table")
@Data
public class VisitTableEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private int id;

    /**
     * 就诊姓名
     */
    private String visitName;

    /**
     * 就诊性别
     */
    private String visitSex;

    /**
     * 用户人id
     */
    private Long userId;

    /**
     * 备注
     */
    private String visitMemo;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 身份证号
     */
    private String visitIdcard;

    /**
     * 就诊年龄
     */
    private String visitAge;

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