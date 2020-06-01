package com.ruoyi.project.hq.data.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.hq.data.domain.FormData;
import com.ruoyi.project.hq.data.service.IFormDataService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 瀚淇订单数据Controller
 * 
 * @author lty
 * @date 2020-03-28
 */
@Controller
@RequestMapping("/hq/data")
public class FormDataController extends BaseController
{
    private String prefix = "hq/data";

    @Autowired
    private IFormDataService formDataService;

    @RequiresPermissions("hq:data:view")
    @GetMapping()
    public String data()
    {
        return prefix + "/data";
    }

    /**
     * 查询瀚淇订单数据列表
     */
    @RequiresPermissions("hq:data:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FormData formData)
    {
        startPage();
        List<FormData> list = formDataService.selectFormDataList(formData);
        return getDataTable(list);
    }

    /**
     * 导出瀚淇订单数据列表
     */
    @RequiresPermissions("hq:data:export")
    @Log(title = "瀚淇订单数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FormData formData)
    {
        List<FormData> list = formDataService.selectFormDataList(formData);
        ExcelUtil<FormData> util = new ExcelUtil<FormData>(FormData.class);
        return util.exportExcel(list, "data");
    }

    /**
     * 新增瀚淇订单数据
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存瀚淇订单数据
     */
    @RequiresPermissions("hq:data:add")
    @Log(title = "瀚淇订单数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FormData formData)
    {
        return toAjax(formDataService.insertFormData(formData));
    }

    /**
     * 修改瀚淇订单数据
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        FormData formData = formDataService.selectFormDataById(id);
        mmap.put("formData", formData);
        return prefix + "/edit";
    }

    /**
     * 修改保存瀚淇订单数据
     */
    @RequiresPermissions("hq:data:edit")
    @Log(title = "瀚淇订单数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FormData formData)
    {
        return toAjax(formDataService.updateFormData(formData));
    }

    /**
     * 删除瀚淇订单数据
     */
    @RequiresPermissions("hq:data:remove")
    @Log(title = "瀚淇订单数据", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(formDataService.deleteFormDataByIds(ids));
    }
}
