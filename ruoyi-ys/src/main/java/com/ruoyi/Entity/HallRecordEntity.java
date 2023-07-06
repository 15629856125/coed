package com.ruoyi.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 就诊大厅记录表
 * @TableName hall_record
 */
@TableName(value ="hall_record")
@Data
public class HallRecordEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * 区号
     */
    private String code;

    /**
     * 预约信息的id
     */
    private String recordId;

    /**
     * 就诊人id
     */
    private String visitId;

    /**
     * 就诊人姓名
     */
    private String visitName;

    /**
     * 医师id
     */
    private String userId;

    /**
     * 医师名称
     */
    private String userName;

    /**
     * 状态0=进行中1=已完成
     */
    private String statue;

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

    private Date createTime;
    /**
     * 
     */
    private String f5;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}