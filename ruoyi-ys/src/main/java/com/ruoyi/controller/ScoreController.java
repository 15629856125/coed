package com.ruoyi.controller;

import com.ruoyi.Entity.DrugManagementEntity;
import com.ruoyi.Entity.ScoreEntity;
import com.ruoyi.Entity.VisitTableEntity;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.service.ScoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评价信息Controller
 *
 * @author ruoyi
 * &#064;date  2022-12-24
 */
@RestController
@RequestMapping("/system/score")
public class ScoreController extends BaseController {
    @Autowired
    private ScoreService scoreService;


    /**
     * 新增评价信息
     */
    @Log(title = "新增评价信息", businessType = BusinessType.INSERT)
    @PostMapping("/insertScore")
    public AjaxResult insertScore(@RequestBody ScoreEntity scoreEntity)
    {
        try {
            return toAjax(scoreService.insertScore(scoreEntity));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查询评价列表
     */
    @ApiOperation(value = "查询评价列表",httpMethod = "GET")
    @GetMapping("/selectScoreList")
    public TableDataInfo selectScoreList(ScoreEntity scoreEntity)
    {
        startPage();
        List<ScoreEntity> list = scoreService.selectScoreList(scoreEntity);
        return getDataTable(list);
    }


    /**
     * 修改评价
     */
    @PreAuthorize("@ss.hasRole('admin')")
    @Log(title = "修改评价", businessType = BusinessType.UPDATE)
    @PostMapping("/updateScore")
    public AjaxResult updateScore(@RequestBody ScoreEntity scoreEntity) throws Exception {
        try {
            return toAjax(scoreService.updateScore(scoreEntity));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除评价
     */

    @PreAuthorize("@ss.hasRole('admin')")
    @Log(title = "删除评价", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteScore/{id}")
    public AjaxResult deleteScore(@PathVariable("id") String id)
    {
        return toAjax(scoreService.deleteScore(id));
    }
}
