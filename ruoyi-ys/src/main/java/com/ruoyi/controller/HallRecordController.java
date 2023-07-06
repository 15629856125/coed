package com.ruoyi.controller;

import com.ruoyi.Dto.DiseaseVo;
import com.ruoyi.Entity.DiseaseTableEntity;
import com.ruoyi.Entity.HallRecordEntity;
import com.ruoyi.Entity.LabelTableEntity;
import com.ruoyi.Entity.OutpatientAppointmentEntity;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.service.HallRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/hallRecord")
public class HallRecordController extends BaseController {
    @Autowired
    private HallRecordService hallRecordService;

    /**
     * 新增就诊大厅数据
     */
    @Log(title = "新增就诊大厅数据", businessType = BusinessType.INSERT)
    @PostMapping("/insertHallRecordEntityList")
    public AjaxResult insertHallRecordEntityList(@RequestBody OutpatientAppointmentEntity outpatientAppointment)
    {
        try {
            return toAjax(hallRecordService.insertHallRecordEntityList(outpatientAppointment));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询列表
     */
    @ApiOperation(value = "查询列表",httpMethod = "GET")
    @GetMapping("/hallRecordList")
    public TableDataInfo hallRecordList(HallRecordEntity hallRecordEntity)
    {
        startPage();
        List<HallRecordEntity> list = hallRecordService.hallRecordList(hallRecordEntity);
        return getDataTable(list);
    }
}
