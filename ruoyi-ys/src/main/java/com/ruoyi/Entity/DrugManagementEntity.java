package com.ruoyi.Entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 药品管理对象 drug_management
 * 
 * @author ruoyi
 * @date 2022-12-24
 */
@TableName(value ="drug_management")
@Data
public class DrugManagementEntity {
    private static final long serialVersionUID = 1L;

    /** 主键id/药品编号 */
    private String id;

    /** 药品名称 */
    @Excel(name = "药品名称")
    private String drugName;

    /** 药品剂型 */
    @Excel(name = "药品剂型")
    private String drugCompany;

    /** 药品规格 */
    @Excel(name = "药品规格")
    private String drugSpecs;

    /** 规格单位 */
    @Excel(name = "规格单位")
    private String drugGgdw;

    /** 是否限制药 */
    @Excel(name = "是否限制药")
    private String isLimit;

    /** 药品库存 */
    @Excel(name = "药品库存")
    private Long drugStock;

    /** 药品功效 */
    @Excel(name = "药品功效")
    private String drugEffect;
    /** 药品价格 */
    @Excel(name = "药品价格")
    private BigDecimal drugPrice;
    /** 生产厂家 */
    @Excel(name = "生产厂家")
    private String manufacturer;

    /** 状态 0//启用 1//禁用 */
    @Excel(name = "状态 0//启用 1//禁用")
    private String drugStatus;

    /** 生产日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date drugCreatetime;

    /** 有效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date drugEndtime;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String f1;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    private String f2;

    /** 备用字段3 */
    @Excel(name = "备用字段3")
    private String f3;

    /** 备用字段4 */
    @Excel(name = "备用字段4")
    private String f4;

    /** 备用字段5 */
    @Excel(name = "备用字段5")
    private String f5;

    @Excel(name = "创建时间")
    private String createtime;
    /** 创建人 */
    @Excel(name = "创建人")
    private String createuser;

    /** 删除状态 0=正常 1=删除 */
    private Long delFlag;

    /** 最后更新人 */
    @Excel(name = "最后更新人")
    private String lastchangeuser;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastchangetime;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 数据状态 0=正常 1=冻结 */
    @Excel(name = "数据状态 0=正常 1=冻结")
    private Long datastatus;

}
