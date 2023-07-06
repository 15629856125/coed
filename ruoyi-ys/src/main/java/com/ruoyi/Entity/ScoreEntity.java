package com.ruoyi.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName score
 */
@TableName(value ="score")
@Data
public class ScoreEntity implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * 病情单id
     */
    private String diseaseId;

    /**
     * 评价分数
     */
    private Integer fraction;

    /**
     * 评价理由
     */
    private String memo;

    /**
     * 医师id
     */
    private String userId;

    /**
     * 评价姓名/就诊人姓名
     */
    private String visitName;

    /**
     * 评价人id/就诊人id
     */
    private String visitId;

    /**
     * 是否匿名
     */
    private String isShow;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 医师姓名
     */
    private String userName;

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