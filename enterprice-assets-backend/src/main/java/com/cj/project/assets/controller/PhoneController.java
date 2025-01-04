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
import com.cj.project.assets.domain.Phone;
import com.cj.project.assets.service.IPhoneService;
import com.cj.framework.web.controller.BaseController;
import com.cj.framework.web.domain.AjaxResult;
import com.cj.common.utils.poi.ExcelUtil;
import com.cj.framework.web.page.TableDataInfo;

/**
 * 手机号管理与使用Controller
 * 
 * @author c c
 * @date 2025-01-01
 */
@RestController
@RequestMapping("/assets/phone")
public class PhoneController extends BaseController
{
    @Autowired
    private IPhoneService phoneService;

    /**
     * 查询手机号管理与使用列表
     */
    @PreAuthorize("@ss.hasPermi('assets:phone:list')")
    @GetMapping("/list")
    public TableDataInfo list(Phone phone)
    {
        startPage();
        List<Phone> list = phoneService.selectPhoneList(phone);
        return getDataTable(list);
    }

    /**
     * 导出手机号管理与使用列表
     */
    @PreAuthorize("@ss.hasPermi('assets:phone:export')")
    @Log(title = "手机号管理与使用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Phone phone)
    {
        List<Phone> list = phoneService.selectPhoneList(phone);
        ExcelUtil<Phone> util = new ExcelUtil<Phone>(Phone.class);
        util.exportExcel(response, list, "手机号管理与使用数据");
    }

    /**
     * 获取手机号管理与使用详细信息
     */
    @PreAuthorize("@ss.hasPermi('assets:phone:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneService.selectPhoneById(id));
    }

    /**
     * 新增手机号管理与使用
     */
    @PreAuthorize("@ss.hasPermi('assets:phone:add')")
    @Log(title = "手机号管理与使用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Phone phone)
    {

//        phoneService.
        phoneService.save(phone);

        return AjaxResult.success("新增成功");
//        return toAjax(phoneService.insertPhone(phone));
    }

    /**
     * 修改手机号管理与使用
     */
    @PreAuthorize("@ss.hasPermi('assets:phone:edit')")
    @Log(title = "手机号管理与使用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Phone phone)
    {
        phoneService.updateById(phone);
//        return toAjax(phoneService.updatePhone(phone));
        return AjaxResult.success("修改成功");
    }

    /**
     * 删除手机号管理与使用
     */
    @PreAuthorize("@ss.hasPermi('assets:phone:remove')")
    @Log(title = "手机号管理与使用", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneService.deletePhoneByIds(ids));
    }
}
