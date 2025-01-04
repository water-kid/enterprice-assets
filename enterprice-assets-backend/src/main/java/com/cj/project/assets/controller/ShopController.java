package com.cj.project.assets.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.cj.project.assets.domain.Shop;
import com.cj.project.assets.service.IShopService;
import com.cj.framework.web.controller.BaseController;
import com.cj.framework.web.domain.AjaxResult;
import com.cj.common.utils.poi.ExcelUtil;
import com.cj.framework.web.page.TableDataInfo;

/**
 * 店铺详情统计Controller
 * 
 * @author cc
 * @date 2025-01-01
 */
@RestController
@RequestMapping("/assets/shop")
@Validated
public class ShopController extends BaseController
{
    @Autowired
    private IShopService shopService;

    /**
     * 查询店铺详情统计列表
     */
    @PreAuthorize("@ss.hasPermi('assets:shop:list')")
    @GetMapping("/list")
    public TableDataInfo list(Shop shop)
    {
        startPage();
        List<Shop> list = shopService.selectShopList(shop);
        return getDataTable(list);
    }

    /**
     * 导出店铺详情统计列表
     */
    @PreAuthorize("@ss.hasPermi('assets:shop:export')")
    @Log(title = "店铺详情统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Shop shop)
    {
        List<Shop> list = shopService.selectShopList(shop);
        ExcelUtil<Shop> util = new ExcelUtil<Shop>(Shop.class);
        util.exportExcel(response, list, "店铺详情统计数据");
    }

    /**
     * 获取店铺详情统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('assets:shop:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shopService.selectShopById(id));
    }

    /**
     * 新增店铺详情统计
     */
    @PreAuthorize("@ss.hasPermi('assets:shop:add')")
    @Log(title = "店铺详情统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody Shop shop)
    {

        shopService.save(shop);
        return AjaxResult.success("修改成功");
//        return toAjax(shopService.insertShop(shop));
    }

    /**
     * 修改店铺详情统计
     */
    @PreAuthorize("@ss.hasPermi('assets:shop:edit')")
    @Log(title = "店铺详情统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody Shop shop)
    {

        shopService.updateById(shop);
        return AjaxResult.success("修改成功");
//        return toAjax(shopService.updateShop(shop));
    }

    /**
     * 删除店铺详情统计
     */
    @PreAuthorize("@ss.hasPermi('assets:shop:remove')")
    @Log(title = "店铺详情统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shopService.deleteShopByIds(ids));
    }
}
