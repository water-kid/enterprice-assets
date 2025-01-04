package com.cj.project.assets.controller;

import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.beust.ah.A;
import com.cj.common.exception.ServiceException;
import com.cj.project.assets.domain.AssetsLoans;
import com.cj.project.assets.domain.enums.BorrowStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.cj.project.assets.domain.Assets;
import com.cj.project.assets.service.IAssetsService;
import com.cj.framework.web.controller.BaseController;
import com.cj.framework.web.domain.AjaxResult;
import com.cj.common.utils.poi.ExcelUtil;
import com.cj.framework.web.page.TableDataInfo;

/**
 * 固定资产盘点明细Controller
 * 
 * @author cc
 * @date 2025-01-01
 */
@RestController
@RequestMapping("/assets/assets")
@Validated
public class AssetsController extends BaseController
{
    @Autowired
    private IAssetsService assetsService;

    /**
     * 查询固定资产盘点明细列表
     */
    @PreAuthorize("@ss.hasPermi('assets:assets:list')")
    @GetMapping("/list")
    public TableDataInfo list(Assets assets)
    {
        startPage();
        List<Assets> list = assetsService.selectAssetsList(assets);
        return getDataTable(list);
    }

    /**
     * 导出固定资产盘点明细列表
     */
    @PreAuthorize("@ss.hasPermi('assets:assets:export')")
    @Log(title = "固定资产盘点明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Assets assets)
    {
        List<Assets> list = assetsService.selectAssetsList(assets);
        ExcelUtil<Assets> util = new ExcelUtil<Assets>(Assets.class);
        util.exportExcel(response, list, "固定资产盘点明细数据");
    }

    /**
     * 获取固定资产盘点明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('assets:assets:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(assetsService.selectAssetsById(id));
    }

    /**
     * 新增固定资产盘点明细
     */
    @PreAuthorize("@ss.hasPermi('assets:assets:add')")
    @Log(title = "固定资产盘点明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody Assets assets)
    {

        assetsService.save(assets);
//        return toAjax(assetsService.insertAssets(assets));
        return AjaxResult.success("新增成功");
    }

    /**
     * 修改固定资产盘点明细
     */
    @PreAuthorize("@ss.hasPermi('assets:assets:edit')")
    @Log(title = "固定资产盘点明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Assets assets)
    {
        assetsService.updateById(assets);
//        return toAjax(assetsService.updateAssets(assets));
        return AjaxResult.success("修改成功");
    }

    /**
     * 删除固定资产盘点明细
     */
    @PreAuthorize("@ss.hasPermi('assets:assets:remove')")
    @Log(title = "固定资产盘点明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {


        return toAjax(assetsService.deleteAssetsByIds(ids));
    }




    @PostMapping("/borrow")
    public AjaxResult borrowAssets(@Valid @RequestBody AssetsLoans assetsLoans){

        assetsService.borrowAssets(assetsLoans, BorrowStatus.BORROWING);


        return AjaxResult.success("借出成功");

    }


    @PostMapping("/consume")
    public AjaxResult consumeAssets(@Valid @RequestBody AssetsLoans assetsLoans){

        assetsService.borrowAssets(assetsLoans, BorrowStatus.CONSUMED);


        return AjaxResult.success("消耗成功");

    }



    @PostMapping("/back/{id}")
    public AjaxResult returnAssets(@PathVariable("id") Long id){

        assetsService.backAssets(id);


        return AjaxResult.success("归还成功");

    }



    @GetMapping("/statistic")
    public AjaxResult getTop5Assets(){
        List<Assets> list = assetsService.list(new LambdaQueryWrapper<Assets>().orderByDesc(Assets::getTotalCount).last("limit 10"));
        ArrayList<Object> result = new ArrayList<>();
        List<String> title = Arrays.asList("类别", "全部", "剩余");
        result.add(title);

        if (list.isEmpty()){
            return AjaxResult.success(result);
        }


        list.forEach(assets -> {
            List<Object> temp = new ArrayList<>();
            temp.add(assets.getName());
            temp.add(assets.getTotalCount());
            temp.add(assets.getRemainingCount());

            result.add(temp);
        });

        return AjaxResult.success(result);
    }



}
