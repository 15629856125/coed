package com.ruoyi.controller;

import com.ruoyi.Dto.DiseaseVo;
import com.ruoyi.Dto.MonthCountDto;
import com.ruoyi.Entity.DiseaseTableEntity;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.service.DiseaseTableService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 病情单管理Controller
 *
 * @author ruoyi
 * &#064;date  2022-12-24
 */
@RestController
@RequestMapping("/system/disease")
public class DiseaseTableController extends BaseController {
    @Autowired
    private DiseaseTableService diseaseTableService;

    /**
     * 新增病情单管理
     */
    @Log(title = "新增病情单管理", businessType = BusinessType.INSERT)
    @PostMapping("/insertDiseaseTableEntity")
    public AjaxResult insertDiseaseTableEntity(@RequestBody DiseaseVo diseaseVo)
    {
        if(diseaseVo.getF3()==null){
            return error("请点击就诊后再进行提交");
        }
        try {
            return toAjax(diseaseTableService.insertDiseaseTableEntity(diseaseVo));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询列表
     */
    @ApiOperation(value = "查询列表",httpMethod = "GET")
    @GetMapping("/selectDiseaseTableEntityList")
    public TableDataInfo selectDiseaseTableEntityList(DiseaseTableEntity diseaseTableEntity)
    {
        startPage();
        List<DiseaseTableEntity> list = diseaseTableService.selectDiseaseTableEntityList(diseaseTableEntity);
        return getDataTable(list);
    }

    /**
     * 查询统计
     */
    @ApiOperation(value = "查询统计",httpMethod = "GET")
    @GetMapping("/listCount")
    public TableDataInfo listCount()
    {
        startPage();
        List<MonthCountDto> list = diseaseTableService.listCount();
        return getDataTable(list);
    }
    /**
     * 编辑病情单管理
     */
    @Log(title = "编辑病情单管理", businessType = BusinessType.INSERT)
    @GetMapping("/updateDiseaseTableEntity")
    public AjaxResult updateDiseaseTableEntity(String id)
    {
        try {
            return toAjax(diseaseTableService.updateDiseaseTableEntity(id));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
