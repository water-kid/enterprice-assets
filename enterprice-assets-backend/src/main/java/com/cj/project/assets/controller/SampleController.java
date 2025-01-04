package com.cj.project.assets.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.cj.framework.aspectj.lang.annotation.Log;
import com.cj.framework.aspectj.lang.enums.BusinessType;
import com.cj.project.assets.domain.Sample;
import com.cj.project.assets.service.ISampleService;
import com.cj.framework.web.controller.BaseController;
import com.cj.framework.web.domain.AjaxResult;
import com.cj.common.utils.poi.ExcelUtil;
import com.cj.framework.web.page.TableDataInfo;

/**
 * 样品库存管理Controller
 * 
 * @author cc
 * @date 2025-01-01
 */
@RestController
@RequestMapping("/assets/sample")
public class SampleController extends BaseController
{
    @Autowired
    private ISampleService sampleService;

    /**
     * 查询样品库存管理列表
     */
    @PreAuthorize("@ss.hasPermi('assets:sample:list')")
    @GetMapping("/list")
    public TableDataInfo list(Sample sample)
    {
        startPage();
        List<Sample> list = sampleService.selectSampleList(sample);
        return getDataTable(list);
    }

    /**
     * 导出样品库存管理列表
     */
    @PreAuthorize("@ss.hasPermi('assets:sample:export')")
    @Log(title = "样品库存管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Sample sample)
    {
        List<Sample> list = sampleService.selectSampleList(sample);
        ExcelUtil<Sample> util = new ExcelUtil<Sample>(Sample.class);
        util.exportExcel(response, list, "样品库存管理数据");
    }

    /**
     * 获取样品库存管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('assets:sample:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sampleService.selectSampleById(id));
    }

    /**
     * 新增样品库存管理
     */
    @PreAuthorize("@ss.hasPermi('assets:sample:add')")
    @Log(title = "样品库存管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Sample sample)
    {
        sampleService.save(sample);
//        return toAjax(sampleService.insertSample(sample));
        return AjaxResult.success("新增成功");
    }

    /**
     * 修改样品库存管理
     */
    @PreAuthorize("@ss.hasPermi('assets:sample:edit')")
    @Log(title = "样品库存管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Sample sample)
    {

        sampleService.updateById(sample);
        return AjaxResult.success("修改成功");
//        return toAjax(sampleService.updateSample(sample));
    }

    /**
     * 删除样品库存管理
     */
    @PreAuthorize("@ss.hasPermi('assets:sample:remove')")
    @Log(title = "样品库存管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sampleService.deleteSampleByIds(ids));
    }
}
