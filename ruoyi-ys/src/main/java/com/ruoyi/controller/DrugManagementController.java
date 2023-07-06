package com.ruoyi.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.ruoyi.Entity.DrugManagementEntity;
import com.ruoyi.service.IDrugManagementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 药品管理Controller
 * 
 * @author ruoyi
 * @date 2022-12-24
 */
@RestController
@RequestMapping("/system/management")
public class DrugManagementController extends BaseController
{
    @Autowired
    private IDrugManagementService drugManagementEntityService;

    /**
     * 查询药品管理列表
     */
//    @PreAuthorize("@ss.hasPermi('system:management:list')")
    @GetMapping("/list")
    public TableDataInfo list(DrugManagementEntity drugManagementEntity)
    {
        startPage();
        List<DrugManagementEntity> list = drugManagementEntityService.selectDrugManagementEntityList(drugManagementEntity);
        return getDataTable(list);
    }

    /**
     * 导出药品管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:management:export')")
    @Log(title = "药品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DrugManagementEntity drugManagementEntity)
    {
        List<DrugManagementEntity> list = drugManagementEntityService.selectDrugManagementEntityList(drugManagementEntity);
        ExcelUtil<DrugManagementEntity> util = new ExcelUtil<DrugManagementEntity>(DrugManagementEntity.class);
        util.exportExcel(response, list, "药品管理数据");
    }

    /**
     * 获取药品管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:management:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(drugManagementEntityService.selectDrugManagementEntityById(id));
    }

    /**
     * 新增药品管理
     */
    @PreAuthorize("@ss.hasPermi('system:management:add')")
    @Log(title = "药品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DrugManagementEntity drugManagementEntity)
    {
        return toAjax(drugManagementEntityService.insertDrugManagementEntity(drugManagementEntity));
    }

    /**
     * 修改药品管理
     */
    @PreAuthorize("@ss.hasPermi('system:management:edit')")
    @Log(title = "药品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DrugManagementEntity drugManagementEntity)
    {
        return toAjax(drugManagementEntityService.updateDrugManagementEntity(drugManagementEntity));
    }

    /**
     * 删除药品管理
     */
    @PreAuthorize("@ss.hasPermi('system:management:remove')")
    @Log(title = "药品管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(drugManagementEntityService.deleteDrugManagementEntityByIds(ids));
    }
}
