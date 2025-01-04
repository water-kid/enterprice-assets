package com.cj.project.assets.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.cj.framework.security.LoginUser;
import com.cj.project.assets.service.AssetsLoansService;
import com.cj.project.system.domain.SysUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.cj.project.assets.domain.AssetsLoans;
import com.cj.framework.web.controller.BaseController;
import com.cj.framework.web.domain.AjaxResult;
import com.cj.common.utils.poi.ExcelUtil;
import com.cj.framework.web.page.TableDataInfo;

/**
 * 外借信息Controller
 *
 * @author cc
 * @date 2025-01-02
 */
@RestController
@RequestMapping("/assets/loans")
public class AssetsLoansController extends BaseController
{
    @Autowired
    private AssetsLoansService assetsLoansService;

    /**
     * 查询外借信息列表
     */
    @PreAuthorize("@ss.hasPermi('assets:loans:list')")
    @GetMapping("/list")
    public TableDataInfo list(AssetsLoans assetsLoans)
    {
        startPage();
        List<AssetsLoans> list = assetsLoansService.selectAssetsLoansList(assetsLoans);
        return getDataTable(list);
    }

    /**
     * 导出外借信息列表
     */
    @PreAuthorize("@ss.hasPermi('assets:loans:export')")
    @Log(title = "外借信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AssetsLoans assetsLoans)
    {
        List<AssetsLoans> list = assetsLoansService.selectAssetsLoansList(assetsLoans);
        ExcelUtil<AssetsLoans> util = new ExcelUtil<AssetsLoans>(AssetsLoans.class);
        util.exportExcel(response, list, "外借信息数据");
    }

    /**
     * 获取外借信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('assets:loans:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(assetsLoansService.selectAssetsLoansById(id));
    }

    /**
     * 新增外借信息
     */
    @PreAuthorize("@ss.hasPermi('assets:loans:add')")
    @Log(title = "外借信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AssetsLoans assetsLoans)
    {
        return toAjax(assetsLoansService.insertAssetsLoans(assetsLoans));
    }

    /**
     * 修改外借信息
     */
    @PreAuthorize("@ss.hasPermi('assets:loans:edit')")
    @Log(title = "外借信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AssetsLoans assetsLoans)
    {

//        AssetsLoans newAssetsLoans = new AssetsLoans();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = loginUser.getUser();
        assetsLoans.setUpdateBy(user.getUserId()+"-"+user.getUserName());
        return toAjax(assetsLoansService.updateAssetsLoans(assetsLoans));
    }

    /**
     * 删除外借信息
     */
    @PreAuthorize("@ss.hasPermi('assets:loans:remove')")
    @Log(title = "外借信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(assetsLoansService.deleteAssetsLoansByIds(ids));
    }
}
