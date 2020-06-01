package com.ruoyi.project.hq.product.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.hq.form.controller.OrderFormController;
import com.ruoyi.project.hq.form.domain.OrderForm;
import com.ruoyi.project.hq.product.domain.Product;
import com.ruoyi.project.hq.product.service.IProductService;
import com.ruoyi.project.system.dict.domain.DictData;
import com.ruoyi.project.system.dict.service.IDictTypeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 瀚淇产品Controller
 * 
 * @author lty
 * @date 2020-03-24
 */
@Controller
@RequestMapping("/hq/product")
public class ProductController extends BaseController
{
    private String prefix = "hq/product";

    @Autowired
    private IProductService productService;

    @RequiresPermissions("hq:product:view")
    @GetMapping()
    public String product()
    {
        return prefix + "/product";
    }
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    /**
     * 查询瀚淇产品列表
     */
    @RequiresPermissions("hq:product:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Product product)
    {
        startPage();
        List<Product> list = productService.selectProductList(product);
        return getDataTable(list);
    }
    
    private final static String path="D:\\hqFile\\产品模板.xlsx";
    
    /**
         * 模板下载
     */
    @GetMapping("/download/template")
    public void invoiceDownload( HttpServletRequest request, HttpServletResponse response)
    		throws Exception
    {
    	response.setCharacterEncoding("utf-8");
    	response.setContentType("multipart/form-data");
    	response.setHeader("Content-Disposition","attachment;fileName=" + FileUtils.setFileDownloadHeader(request, "产品模板"));
    	FileUtils.writeBytes(path, response.getOutputStream());
    }
    
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file) throws Exception
    {
    	ExcelUtil<Product> util = new ExcelUtil<Product>(Product.class);
    	List<Product> productList = util.importExcel(file.getInputStream());
    	long userId = ShiroUtils.getSysUser().getUserId();
    	String ip=ShiroUtils.getIp();
    	 //产品类型
       // List<DictData> productTypeList= dictTypeService.selectDictDataByType("product_type");
        //退税率
        List<DictData> taxRefundRateList= dictTypeService.selectDictDataByType("hq_tax_refund_rate");     
        List<Integer> insertList=new ArrayList<Integer>();
        List<Integer> updateList=new ArrayList<Integer>();
        Map<Integer,String> error=new HashMap<Integer,String>();
		for(int i=0;i<productList.size();i++) {
			Product product=productList.get(i);
			product.setLastUpdateDate(new Date());
			product.setLastUpdateId(userId+"");
			product.setIp(ip);
			try {
				//插入
				if(product.getId()==null||product.getId().trim().length()==0) {
		        	if(filtration(product,taxRefundRateList)) {
		        	  product.setCreateId(userId+"");
		        	  product.setCreateDate(new Date());
		        	  int modificationCount=productService.insertProduct(product);
		        	  if(modificationCount>0) {
		        		  insertList.add(i+1);		        	  
		        	  }
		        	}	
				}else {
					//修改 
					if(filtration(product,taxRefundRateList)) {
						  int modificationCount=productService.updateProduct(product);
			        	  if(modificationCount>0) {
			        		  updateList.add(i+1);		        		 
			        	  }
					}
				}	
			} catch (Exception e) {
				log.error(i+1+"行解析出现异常！:",e);
				error.put(i+1, e.getMessage());
			}
			
		}
    	return AjaxResult.success("一共有"+productList.size()+"条数据 <br>修改:"+updateList.size()+"条 <br>修改行标: <br>"+JSON.toJSONString(updateList)+"<br>插入:"+insertList.size()+"条 <br>插入行标 <br>"+JSON.toJSONString(insertList)+"<br>异常信息:"+JSON.toJSONString(error));
    }
    
    public Boolean filtration(Product product,List<DictData> taxRefundRateList) {
    	int count=0;	
    	//退税率
    	for(DictData dict:taxRefundRateList) {
    		if(dict.getDictLabel().equals(product.getTaxRefundRate().trim())) {
    			product.setTaxRefundRate(dict.getDictValue()); 
    			count++;
    			break;
    		}
    	}
    	String declareElements=product.getDeclareElements();
    	if(declareElements!=null) {
    		String []array=declareElements.split("#");
    		if(array.length>1) {
    			StringBuilder sbd=new StringBuilder();
    			for(String val:array) {
    				sbd.append(val+"\r\n");
    			}
    			product.setDeclareElements(sbd.toString());
    		}
    	}
    	return count==1;
    }


    @Autowired
    private IDictTypeService dictTypeService;
    
    /**
     * 导出瀚淇产品列表
     */
    @RequiresPermissions("hq:product:export")
    @Log(title = "瀚淇产品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Product product)
    {
        List<Product> list = productService.selectProductList(product);
        //产品类型
        List<DictData> productTypeList= dictTypeService.selectDictDataByType("product_type");
        //退税率
        List<DictData> taxRefundRateList= dictTypeService.selectDictDataByType("hq_tax_refund_rate");
        for(Product p:list) {
        	for(DictData dict:productTypeList) {
        		if(dict.getDictValue().equals(p.getProductType())) {
        			p.setProductType(dict.getDictLabel());
        			break;
        		}
        	}
        	for(DictData dict:taxRefundRateList) {
        		if(dict.getDictValue().equals(p.getTaxRefundRate())) {
        			p.setTaxRefundRate(dict.getDictLabel());
        			break;
        		}
        	}
        }
        ExcelUtil<Product> util = new ExcelUtil<Product>(Product.class);
        return util.exportExcel(list, "product");
    }

    /**
     * 新增瀚淇产品
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存瀚淇产品
     */
    @RequiresPermissions("hq:product:add")
    @Log(title = "瀚淇产品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Product product)
    {
        return toAjax(productService.insertProduct(product));
    }

    /**
     * 修改瀚淇产品
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap mmap)
    {
        Product product = productService.selectProductById(id);
        mmap.put("product", product);
        return prefix + "/edit";
    }

    /**
     * 修改保存瀚淇产品
     */
    @RequiresPermissions("hq:product:edit")
    @Log(title = "瀚淇产品", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Product product)
    {
        return toAjax(productService.updateProduct(product));
    }

    /**
     * 删除瀚淇产品
     */
    @RequiresPermissions("hq:product:remove")
    @Log(title = "瀚淇产品", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(productService.deleteProductByIds(ids));
    }
}
