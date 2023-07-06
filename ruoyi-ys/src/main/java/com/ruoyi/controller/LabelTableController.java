package com.ruoyi.controller;

import com.ruoyi.Entity.DiseaseTableEntity;
import com.ruoyi.Entity.LabelTableEntity;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.service.LabelTableService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/label")
public class LabelTableController extends BaseController {
    @Autowired
    private LabelTableService labelTableService;

    /**
     * 查询列表
     */
    @ApiOperation(value = "查询列表",httpMethod = "GET")
    @GetMapping("/selectLabelTableEntityList")
    public TableDataInfo selectLabelTableEntityList(DiseaseTableEntity diseaseTableEntity)
    {
        startPage();
        List<LabelTableEntity> list = labelTableService.selectLabelTableEntityList(diseaseTableEntity);
        return getDataTable(list);
    }
}
