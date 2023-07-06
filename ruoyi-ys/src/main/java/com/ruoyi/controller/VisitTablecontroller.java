package com.ruoyi.controller;
import com.ruoyi.Entity.VisitTableEntity;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.service.VisitTableService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 就诊人管理Controller
 *
 * @author ruoyi
 * &#064;date  2022-12-24
 */
@RestController
@RequestMapping("/system/visit")
public class VisitTablecontroller extends BaseController {
    @Autowired
    private VisitTableService visitTableService;

    @ApiOperation(value = "删除",httpMethod = "GET")
    @GetMapping("/removevisit")
    public AjaxResult removevisit(@RequestParam String id)
    {
        return toAjax(visitTableService.removevisit(id));
    }

    @ApiOperation(value = "新增",httpMethod = "POST")
    @PostMapping("/insertvisit")
    public AjaxResult insertvisit(@RequestBody VisitTableEntity visitTableEntity)
    {
        return success(visitTableService.insertvisit(visitTableEntity));
    }
    /**
     * 查询就诊人管理列表
     */
    @ApiOperation(value = "查询列表",httpMethod = "GET")
    @GetMapping("/list")
    public TableDataInfo list(VisitTableEntity visitTableEntity)
    {
        startPage();
        List<VisitTableEntity> list = visitTableService.selectvisitList(visitTableEntity);
        return getDataTable(list);
    }

    /**
     * 获取就诊人详细信息
     */
    @ApiOperation(value = "查询详情",httpMethod = "GET")
    @GetMapping("/selectByid")
    public AjaxResult selectById(@RequestParam String id)
    {
        return success(visitTableService.selectByid(id));
    }
}
