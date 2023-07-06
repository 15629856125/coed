package com.ruoyi.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 预约信息对象 outpatient_appointment
 *
 * @author 小丁
 * &#064;date  2023-01-10
 */
@TableName(value ="outpatient_appointment")
@Data
public class OutpatientAppointmentEntity {
    /** 主键id */
    private String id;

    /** 医师id */

    private String userId;

    private String userName;

    private String visitName;


    private String visitSex;


    private Long visitId;


    private Long deptId;


    private String deptName;


    private String status;


    private String f1;


    private String f2;


    private String f3;


    private String f4;


    private String f5;

    private Date createTime;
}
