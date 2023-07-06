package com.ruoyi.controller;

import com.ruoyi.Entity.OutpatientAppointmentEntity;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.service.OutpatientAppointmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/appointment")
public class OutpatientAppointmentController extends BaseController {
    @Autowired
    private OutpatientAppointmentService outpatientAppointmentService;

    @ApiOperation(value = "新增/编辑",httpMethod = "POST")
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody OutpatientAppointmentEntity outpatientAppointment)
    {
        return success(outpatientAppointmentService.insert(outpatientAppointment));
    }

    /**
     * 查询列表
     */
    @ApiOperation(value = "查询列表",httpMethod = "GET")
    @GetMapping("/selectList")
    public TableDataInfo selectList(OutpatientAppointmentEntity outpatientAppointment)
    {
        startPage();
        List<OutpatientAppointmentEntity> list = outpatientAppointmentService.selectList(outpatientAppointment);
        return getDataTable(list);
    }

    /**
     * 获取详细信息
     */
    @ApiOperation(value = "查询详情",httpMethod = "GET")
    @GetMapping("/selectById")
    public AjaxResult selectById(@RequestBody String id)
    {
        return success(outpatientAppointmentService.selectById(id));
    }
}
