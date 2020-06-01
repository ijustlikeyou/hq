package com.ruoyi.project.hq.form.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.hq.data.domain.FormData;
import com.ruoyi.project.hq.data.service.IFormDataService;
import com.ruoyi.project.hq.form.domain.OrderForm;
import com.ruoyi.project.hq.form.service.IOrderFormService;
import com.ruoyi.project.hq.info.domain.SysFileInfo;
import com.ruoyi.project.hq.info.service.ISysFileInfoService;
import com.ruoyi.project.hq.product.domain.Product;
import com.ruoyi.project.hq.product.service.IProductService;
import com.ruoyi.project.system.dict.domain.DictData;
import com.ruoyi.project.system.dict.service.IDictDataService;
import com.ruoyi.project.system.dict.service.IDictTypeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ExcelTest;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 瀚淇订单Controller
 * 
 * @author lty
 * @date 2020-03-24
 */
@Controller
@RequestMapping("/hq/form")
public class OrderFormController extends BaseController
{
    private String prefix = "hq/form";

    private static final Logger log = LoggerFactory.getLogger(OrderFormController.class);

   
    @Autowired
    private IOrderFormService orderFormService;
    @Autowired
    private ISysFileInfoService fileInfoService;
    @Autowired
    private IFormDataService formDataService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IDictTypeService dictTypeService;
    
    @RequiresPermissions("hq:form:view")
    @GetMapping()
    public String form()
    {
        return prefix + "/form";
    }
    //存储路径
    private static final String newFilePath=RuoYiConfig.getProfile();
   
	@PostMapping("/addFile")
	@ResponseBody
	public AjaxResult addSave(@RequestParam("file") MultipartFile file, SysFileInfo fileInfo) throws IOException
	{
		// 上传文件路径
		String filePath = RuoYiConfig.getUploadPath();
		// 上传并返回新文件名称
		String fileName = FileUploadUtils.upload(filePath, file);	
		fileInfo.setFilePath(fileName);	
		AjaxResult r=toAjax(fileInfoService.insertSysFileInfo(fileInfo));
		if(Integer.valueOf(r.get("code").toString())==0) {
			r.put("filePath",fileName.replace("/profile/upload",filePath));
		}
		return r;
	}


    /**
     * 查询瀚淇订单列表
     */
    @RequiresPermissions("hq:form:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OrderForm orderForm)
    {
        startPage();
        List<OrderForm> list = orderFormService.selectOrderFormList(orderForm);
        return getDataTable(list);
    }

    /**
     * 导出瀚淇订单列表
     */
    @RequiresPermissions("hq:form:export")
    @Log(title = "瀚淇订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderForm orderForm)
    {
        List<OrderForm> list = orderFormService.selectOrderFormList(orderForm);
        ExcelUtil<OrderForm> util = new ExcelUtil<OrderForm>(OrderForm.class);
        return util.exportExcel(list, "form");
    }
    
    /**
     * 发票下载
     */
    @GetMapping("/common/download/invoice")
    public void invoiceDownload(String key, HttpServletRequest request, HttpServletResponse response)
    		throws Exception
    {
    	String downloadPath=request.getSession().getAttribute(key).toString();
    	// 下载名称
    	String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
    	response.setCharacterEncoding("utf-8");
    	response.setContentType("multipart/form-data");
    	response.setHeader("Content-Disposition","attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
    	FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }

    
    /**
     * 生成瀚淇订单发票
     * @throws Exception 
     */
    @RequiresPermissions("hq:form:export")
    @Log(title = "瀚淇订单发票", businessType = BusinessType.EXPORT)
    @PostMapping("/exportInvoice")
    @ResponseBody
    public AjaxResult exportInvoice(String id,HttpServletRequest request) throws Exception
    {
    	 //美元系数 
    	 BigDecimal dollarCoefficient=new BigDecimal(dictTypeService.selectDictDataByType("dollarCoefficient").get(0).getDictValue().toString());
    	 OrderForm orderForm = orderFormService.selectOrderFormById(id);
         List<FormData> formList=formDataService.selectFormDataList(new FormData(orderForm.getId()));
         Map<String,Object> dataMap=new HashMap<String,Object>();
        //总件数
  		 Long packages=0L;
  		 //总金额
  		 BigDecimal totalMoney=new BigDecimal(0);
    	//总重量
  		 BigDecimal totalWeight=new BigDecimal(0);
  		//总数量
  		 BigDecimal totalCount=new BigDecimal(0);		 
        for(int i=0;i<formList.size();i++) {
   		 FormData nowData=formList.get(i);	
   		 nowData.setProduct(productService.selectProductById(nowData.getProductId()));  		
   		 if(nowData.getPieceCount()==null&&i!=0) {
   			nowData.setPieceCount(formList.get(i-1).getPieceCount()); 
   		}
   		 String yId=nowData.getProductId();
   		 List<FormData> list=new ArrayList<FormData>(); 		
   		 for(int y=i+1;y<formList.size();y++) {
   			 if(yId.equals(formList.get(y).getProductId())) {
   				list.add(formList.get(y));
   			 }
   		 } 		  
   		 FormData form=mergeData(formList.get(i),list);
   		 packages+=form.getPieceCount();
   		 //退税系数 
   		 BigDecimal taxRebatesCoefficient=form.getProduct().getTaxRebatesCoefficient()==null||form.getProduct().getTaxRebatesCoefficient().trim().length()==0?dollarCoefficient:new BigDecimal(form.getProduct().getTaxRebatesCoefficient());
   		 //单价
   		 form.setStandby01(new BigDecimal(form.getUnitPrice()).multiply(taxRebatesCoefficient).multiply(new BigDecimal(form.getProduct().getTaxRefundRate())).divide(new BigDecimal(orderForm.getDollarCurrencyRate()),BigDecimal.ROUND_HALF_DOWN).toString());
   		 //总价
   		 form.setStandby02(new BigDecimal(form.getStandby01()).multiply(new BigDecimal(form.getPurchaseCount())).toString());
   		 totalMoney=totalMoney.add(new BigDecimal(form.getStandby02()));
   		 totalWeight=totalWeight.add(new BigDecimal(form.getTotalWeight()));
   		 totalCount=totalCount.add(new BigDecimal(form.getPurchaseCount()));
   		 formList.set(i, form);
   		 formList.removeAll(list);
   	   }    	
        dataMap.put("formDataList",formList);
        dataMap.put("orderForm",orderForm);
        dataMap.put("packages",packages);
        dataMap.put("totalMoney",totalMoney);
        dataMap.put("totalWeight",totalWeight);
        //瀚淇重量计算系数
        dataMap.put("weightCoefficient",dictTypeService.selectDictDataByType("hq_weight_coefficient").get(0).getDictValue().toString());
        orderForm.setTotalCount(packages);
        orderForm.setTotalMoney(totalMoney.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
        orderForm.setTotalWeight(totalWeight.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
        orderForm.setTotalQuantity(totalCount.setScale(2, BigDecimal.ROUND_HALF_DOWN).longValue());
        orderFormService.updateOrderForm(orderForm);
        String time=DateUtils.dateTimeNow();
        String filePath=newFilePath+orderForm.getFormId()+"_"+time+".xls";
        ExcelTest.writeExcelInvoice(filePath,dataMap);
        request.getSession().setAttribute(time, filePath);  
        return AjaxResult.success(time);
    }

    
  
    /**
     * 新增瀚淇订单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存瀚淇订单
     * @throws Exception 
     */
    @RequiresPermissions("hq:form:add")
    @Log(title = "瀚淇订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OrderForm orderForm) throws Exception
    {
    	 orderForm.setCreateDate(new Date());
    	 orderForm.setCreateId(ShiroUtils.getUserId().toString());
    	 orderForm.setIp(ShiroUtils.getIp());
    	 Map<String, Object> excelDataMap=ExcelTest.readExcel(orderForm);
    	 List<Map<String,String>> list=(List<Map<String, String>>)excelDataMap.get("dataList");
    	 Boolean isDrug=orderForm.getFormType().equals("1");
    	 List<FormData> excelFormList=new ArrayList<FormData>();
    	 for(int i=0;i<list.size();i++){
    		 Map<String,String> row=list.get(i);
    		 try { 
    			 Product product=null;
    			  if(isDrug) {
    				  String productName=row.get("品名");
        			  if(productName==null||productName.length()==0) {
        				  log.error(i+"行药品品名为{}没有数据！",productName);
        				  continue;
        			  }
        			  product=productService.selectProductByProductName(productName);
        			  if(product==null) { 
        				  log.error(i+"行品名为{},无法在产品库中匹配到数据！",productName);
        				  continue;
        			  }
    			  }else {
    				  product=productService.selectProductByProductParameter(row.get("企业编码"), row.get("名称"), row.get("型号"));
    				  if(product==null) {
    					  log.error(i+"行摩配品没有数据！:",JSON.toJSONString(row));
        				  continue;
    				  }
    			  }		 
    			  excelFormList.add(new FormData(product,row,i+1,isDrug));
			  }catch (Exception e){
				 log.error(i+"行数据解析异常:"+JSON.toJSONString(row),e);
			 }
    	 }
    	 String id= orderFormService.insertOrderForm(orderForm);
    	 if(id==null) {
    		 log.error("订单插入失败!"+JSON.toJSONString(orderForm));
    		 throw new Exception("订单插入失败!"+JSON.toJSONString(orderForm));
    	 }
    	 for(FormData form:excelFormList) {
    		 form.setOrderFormId(id);
    		 formDataService.insertFormData(form);
    	 }
        return toAjax(1);
    }

	
	 
	 /**
	  * 合并订单数据
	  * @param form
	  * @param formList
	  * @return
	  */
    public static FormData mergeData(FormData form,List<FormData> formList) {
    	if(formList.size()==0) {
    		return form;
    	}
    	for(FormData formData:formList) {
    		form.setPieceCount(form.getPieceCount()+formData.getPieceCount());
    		form.setPurchaseCount(new BigDecimal(form.getPurchaseCount()).add(new BigDecimal(formData.getPurchaseCount())).toString()); 		
    		form.setTotalCubeCount(new BigDecimal(form.getTotalCubeCount()).add(new BigDecimal(formData.getTotalCubeCount())).toString());
    		form.setTotalWeight(new BigDecimal(form.getTotalWeight()).add(new BigDecimal(formData.getTotalWeight())).toString());
    	}
    	return form;
    }

    /**
     * 修改瀚淇订单
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        OrderForm orderForm = orderFormService.selectOrderFormById(id);
        mmap.put("orderForm", orderForm);
        return prefix + "/edit";
    }

    /**
     * 修改保存瀚淇订单
     */
    @RequiresPermissions("hq:form:edit")
    @Log(title = "瀚淇订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OrderForm orderForm)
    {
    	orderForm.setLastUpdateDate(new Date());
    	orderForm.setLastUpdateId(ShiroUtils.getUserId().toString());
    	orderForm.setIp(ShiroUtils.getIp());
        return toAjax(orderFormService.updateOrderForm(orderForm));
    }

    /**
     * 删除瀚淇订单
     */
    @RequiresPermissions("hq:form:remove")
    @Log(title = "瀚淇订单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(orderFormService.deleteOrderFormByIds(ids));
    }
}
