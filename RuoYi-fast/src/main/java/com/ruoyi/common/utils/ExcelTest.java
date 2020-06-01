package com.ruoyi.common.utils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.hq.data.domain.FormData;
import com.ruoyi.project.hq.form.domain.OrderForm;
import com.ruoyi.project.system.dict.service.IDictTypeService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelTest {

	  public static void main(String[] args) throws Exception {	 
		  String x="123#123#";
		  String [] xx=x.split("#");
		  for(int i=0;i<xx.length;i++) {
			  System.out.println(i+"#"+xx[i]);
		  }
		
		/*
		 * File file = new
		 * File("C:\\Users\\Administrator.WIN-AVI0HHBELTV\\Desktop\\发票模板.xls");
		 * FileInputStream fis;
		 * 
		 * try { fis = new FileInputStream(file); Workbook wb = getWorkbok(file); Sheet
		 * sheet = wb.getSheet("申报要素"); //蓝色 CellStyle
		 * blue=sheet.getRow(1).getCell(0).getCellStyle(); //加粗 CellStyle style
		 * =sheet.getRow(2).getRowStyle(); Row a=sheet.createRow(5); Cell
		 * cell=a.createCell(0); cell.setCellValue(changeString(1));
		 * cell.setCellStyle(blue);
		 * 
		 * FileOutputStream fos = new FileOutputStream(
		 * "C:\\Users\\Administrator.WIN-AVI0HHBELTV\\Desktop\\发票模板test.xls");
		 * wb.write(fos); fis.close(); fos.close(); wb.close(); } catch (IOException e)
		 * { e.printStackTrace(); } System.out.println("结束");
		 */
		  OrderForm a=new OrderForm();
		  a.setExcelFilePath("C:\\Users\\Administrator.WIN-AVI0HHBELTV\\Desktop\\摩配 配柜清单test.xls");
		  a.setFormId("19M");
		  a.setFormType("2");
		  readExcel(a);
	    }
	  
	    @Autowired
	    private IDictTypeService dictTypeService;
	  
	  //模板路径
	  private static final String filePath="D:\\hqFile\\发票模板.xls";
	  
	  
	 
	  
	  private static final String [] monthName=new String [] {"Jan.","Feb.","Mar.","Apr.","May.","Jun.","Jul.","Aug.","Sept.","Oct.","Nov.","Dec."};
	  
	  private static final String [] monthArray=new String [] {"01","02","03","04","05","06","07","08","09","10","11","12"};
	  
	  private static final String [] chineseNumbers=new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
	  
	  private static final String [] chineseNumbersUnit=new String[] {"十","百","千"};
	  
	  private static final Logger log = LoggerFactory.getLogger(ExcelTest.class);
	  
	     /**
	      * int  正整数字转中文 因业务量过小只编写到千级别
	      * @param lty
	      * @return
	      */
	     private static String changeString(int lty) {// 数字转换成汉字表示	      
	         if(lty<10) {
	        	 return chineseNumbers[lty];
	         }    
	         if(lty<100) {
	        	return lty/10==1?chineseNumbersUnit[0]+(lty%10!=0?changeString(lty%10):""):chineseNumbers[lty/10]+chineseNumbersUnit[0]+(lty%10==0?"":chineseNumbers[lty%10]);
	         }
	         if(lty<1000){
	        	return chineseNumbers[lty/100]+chineseNumbersUnit[1]+(lty%100!=0?(lty%100<20?(lty%100<10?chineseNumbers[0]+chineseNumbers[lty%10]:chineseNumbers[1]+chineseNumbersUnit[0]+(lty%10==0?"":chineseNumbers[lty%10])):changeString(lty%100)):"");
	         }	     
	         return lty+"";
	     }
	  /**
	   * 克隆一个sheet 返回克隆完成的sheet对象
	   * @param wb
	   * @param cloneSheet
	   * @param newSheetName
	   * @return
	   */
	  public static Sheet cloneSheet(Workbook wb,Sheet cloneSheet,String newSheetName) {
		  Sheet lty = wb.cloneSheet(wb.getSheetIndex(cloneSheet));   
		  wb.setSheetName(wb.getSheetIndex(lty),newSheetName);
		  return lty;
	  }
	  
	/*
	 * if(!isDrug) { for(int del=10;del<keyList.size();) { keyList.remove(del); }
	 * List<String> towKeyList=getRowData(sheetAt.getRow(7)); for(int
	 * del=0;del<towKeyList.size();del++) { if(towKeyList.get(del).length()==0) {
	 * towKeyList.remove(del); del--; } } keyList.addAll(towKeyList); r=8; }
	 */
	    /**
	     * 解析报关清单excel
	     * @param path
	     * @return
	     * @throws IOException 
	     */
	    public static Map<String,Object> readExcel(OrderForm orderForm) throws IOException {
	    String sheetName=orderForm.getFormId();
	    String path=orderForm.getExcelFilePath();
		File file = new File(path);
		FileInputStream fis = null;
		Workbook workBook = null;
		Map<String,Object> map=null;		
		if (file.exists()) {
		    try {
			fis = new FileInputStream(file);
			workBook = WorkbookFactory.create(fis);
			int numberOfSheets = workBook.getNumberOfSheets();
			for (int s = 0; s < numberOfSheets; s++) {
			    Sheet sheetAt = workBook.getSheetAt(s);
			    if(!sheetName.equals(sheetAt.getSheetName())) {
			    	continue;
			    }
			    map=new HashMap<String,Object>();
			    List<String> keyList=null;
			    int rowsOfSheet = sheetAt.getPhysicalNumberOfRows();
			    List<Map<String,String>> data=new ArrayList<Map<String,String>>();
			    //是否是药品
			    //Boolean isDrug=orderForm.getFormType().equals("1");
			    int r = 1;
			    for (; r < rowsOfSheet; r++) {
			        Row row = sheetAt.getRow(r);
					if (row == null||r==rowsOfSheet-1) {
					    continue;
				        }				
					  if(r==1) {
						keyList=getRowData(row);			
					    log.info("keyList:{}",JSON.toJSONString(keyList));
					  }else {
						Map<String,String> dataMap=new HashMap<String,String>();
						List<String> dataList=getRowData(row);
						for(int i=0;i<keyList.size();i++) {
							try {
								 if(dataList.get(0).length()==0) {
									 break;
								 }
								dataMap.put(keyList.get(i), dataList.get(i));
							} catch (Exception e) {
								log.error(r+1+"行"+keyList.get(i)+"列解析异常",e);
								throw new BusinessException("Excel解析异常！"+(r+1)+"行"+keyList.get(i)+"列解析异常");
							}			
						}
						if(dataMap.size()==0) {
							continue;
						}
						log.info("{}:{}",r,JSON.toJSONString(dataMap));
						data.add(dataMap);
					  }
			     }
			    map.put("dataList", data);
			    map.put("dataCount",data.size());  
			    }	       
		        if (fis != null) {
			    fis.close();
		        }
		    } catch (Exception e) {
		    	log.error("解析异常:",e);
		        e.printStackTrace();
		    }
	        } else {
	        	log.error("文件不存在:"+path);
	        	throw new BusinessException("文件不存在"+path);
	        }
		  return map;
	    }
	   
	    
	    public static List<String> getRowData(Row row){
	    	List<String> list=new ArrayList<String>();
	    	int length=row.getPhysicalNumberOfCells();
	    	   for (int i = 0; i < length; i++) {
				    Cell cell=row.getCell(i);
				    if(cell==null) {
				    	list.add("");
				    	continue;
				    }
				    cell.setCellType(CellType.STRING);
				    list.add(cell.getStringCellValue().trim());     
			   }			 
	    	return list;
	    }
	    
	    public static void setRow(Boolean is,Row row,Long sort,String name,String count,String unit,String price,String totalPrice) {
	    	  if(is) {
	    		  Cell cell=row.getCell(0);
	              cell.setCellValue("");
	    	  }
	    	 //序号
              Cell cell=row.getCell(1);
              cell.setCellValue(sort);
              //产品名称
              cell=row.getCell(2);
              cell.setCellValue(name);
              //数量
              cell=row.getCell(4);
              BigDecimal cell4 = new BigDecimal(count);
              cell.setCellValue(cell4.doubleValue());
              //单位
              cell=row.getCell(5);
              cell.setCellValue(unit);
              BigDecimal cell6 = new BigDecimal(price);
              //单价
              cell=row.getCell(6);
              cell.setCellValue(cell6.doubleValue());
              //总金额
              cell=row.getCell(7);
              BigDecimal cell7 = new BigDecimal(totalPrice);
              cell.setCellValue(cell7.doubleValue());
	    }
	    
	    public static void setPackingRow(Row row,Long sort,FormData form,Boolean isDrug) {    
	    	 //序号 
            Cell cell=row.createCell(1, CellType.NUMERIC);
            cell.setCellValue(sort);
            //产品名称
            cell=row.getCell(2);
            cell.setCellValue(isDrug?form.getProduct().getCustomsDeclarationName():form.getProduct().getEnglishName());
            //件数
            cell=row.getCell(3);
            cell.setCellValue(new BigDecimal(form.getPieceCount()).doubleValue());
            //包装单位
            cell=row.getCell(4);
            cell.setCellValue(form.getProduct().getStandby01());            
            //采购数量
            cell=row.getCell(5);
            cell.setCellValue(new BigDecimal(form.getPurchaseCount()).doubleValue());
            //单位
            cell=row.getCell(6);
            cell.setCellValue(form.getProduct().getUnit());
            //净重
            cell=row.getCell(7);
            cell.setCellValue(new BigDecimal(form.getTotalWeight()).subtract(new BigDecimal(5)).doubleValue());
            //毛重
            cell=row.getCell(9);
            cell.setCellValue(new BigDecimal(form.getTotalWeight()).doubleValue());
	    }
	    
	    public static void setClearanceGoodsRow(Row row,Row row1,Long sort,FormData form,String destination) {
	    	  //设置序号
	    	  Cell cell=row.getCell(0)==null?row.createCell(0, CellType.STRING):row.getCell(0);
	          cell.setCellValue(sort);
	          //设置商品编号
	          cell=row.getCell(1)==null?row.createCell(1, CellType.STRING):row.getCell(1);
	          cell.setCellValue(form.getProduct().getCustomsCode());
	          //设置商品英文名称
	          cell=row.getCell(4)==null?row.createCell(4, CellType.STRING):row.getCell(4);
	          cell.setCellValue(form.getProduct().getEnglishName());
	          //设置数量与单位
	          cell=row.getCell(6)==null?row.createCell(6, CellType.STRING):row.getCell(6);
	          cell.setCellValue(form.getPurchaseCount()+form.getProduct().getUnit());
	          //设置单价/总价/币制
	          cell=row.getCell(7)==null?row.createCell(7, CellType.STRING):row.getCell(7);  
	          cell.setCellValue("USD "+new BigDecimal(form.getStandby01()).setScale(4,BigDecimal.ROUND_HALF_UP).toString()+"     USD "+new BigDecimal(form.getStandby02()).setScale(2,BigDecimal.ROUND_HALF_UP).toString()+" 美元");
	          //原产地
	          cell=row.getCell(10)==null?row.createCell(10, CellType.STRING):row.getCell(10);
	          cell.setCellValue(form.getProduct().getPlace());
	          //目的国
	          cell=row.getCell(11)==null?row.createCell(11, CellType.STRING):row.getCell(11);
	          cell.setCellValue(destination);
	          //境内货源地
	          
	          cell=row.getCell(13)==null?row.createCell(13, CellType.STRING):row.getCell(13);
	          cell.setCellValue("上海");
	          
	          //设置报关名字
	          Cell cell1=row1.getCell(3)==null?row1.createCell(3, CellType.STRING):row1.getCell(3);
	          cell1.setCellValue(form.getProduct().getCustomsDeclarationName());     
	    }
	    
	    public static void rowTest(Row row) {
	    	int length=row.getPhysicalNumberOfCells();
	    	System.out.println("第"+row.getRowNum()+"行");
	    	for(int i=0;i<length;i++) {
	    		Cell cell=row.getCell(i);	
	    		System.out.println(i+":"+getCellValue(cell));
	    	}
	    }
	/*
	 * dataMap.put("formDataList",formList);
	 *  dataMap.put("orderForm",orderForm);
	 * dataMap.put("packages",packages);
	 *  dataMap.put("totalMoney",totalMoney);
	 * dataMap.put("totalWeight",totalWeight);
	 */
	    
	    public static void writeExcelInvoice(String newFilePath,Map<String,Object> dataMap) throws Exception{
	        OutputStream out = null;
	        OrderForm form=(OrderForm) dataMap.get("orderForm");
	        List<FormData> formDataList=(List<FormData>) dataMap.get("formDataList"); 
	        String [] dateNow=DateUtils.getDate().split("-");
			String dateStr=null;
			for(int i=0;i<monthArray.length;i++) {
				if(monthArray[i].equals(dateNow[1])) {
					dateStr=monthName[i]+" "+dateNow[2]+","+dateNow[0];
					break;
				}
			}
			
	        try {	          	        	
	            // 读取Excel文档
	            File finalXlsxFile = new File(newFilePath);
	            FileUtils.copyFile(new File(filePath),finalXlsxFile);
	            Workbook workBook = getWorkbok(finalXlsxFile);
	            int numberOfSheets = workBook.getNumberOfSheets();          
	            BigDecimal totalWeight=new BigDecimal(0);
	            BigDecimal totalNetWeight=new BigDecimal(0);           
	            //净重量计算系数
	            String weightCoefficient=dataMap.get("weightCoefficient").toString().trim();
	            Boolean isState=weightCoefficient.indexOf("%")==-1;
	            BigDecimal coefficient=null;
	            if(isState) {
	            	coefficient=new BigDecimal(weightCoefficient);
	            }else {
	            	coefficient=new BigDecimal("0."+weightCoefficient.replace("%","").replace(".",""));
	            }
	            for (int index=0;index<formDataList.size();index++) {   
	            	FormData formData=formDataList.get(index);
	            	//毛重
	            	BigDecimal weightBig=new BigDecimal(formData.getTotalWeight());
	            	//净重 保留一位小数四舍五入
	            	BigDecimal suttle=isState?weightBig.subtract(coefficient).setScale(1,BigDecimal.ROUND_HALF_UP):weightBig.multiply(coefficient).setScale(1,BigDecimal.ROUND_HALF_UP);
	            	totalWeight=totalWeight.add(weightBig);
	            	totalNetWeight=totalNetWeight.add(suttle);
	            }			            
	            for(int i=0;i<numberOfSheets;i++) {
	            	Sheet sheeti=workBook.getSheetAt(i);
	            	String sheetName=sheeti.getSheetName();
	            	if(sheetName.equals("发票")) {
	            		invoice(sheeti,form,formDataList,dataMap,dateStr);
	            	}else if(sheetName.equals("装箱单")) {
	            		packing(sheeti,form,formDataList,dataMap,dateStr,totalWeight,totalNetWeight);
	            	}else if(sheetName.equals("报关单1")) {
	            		clearanceGoods(workBook,sheeti,form,formDataList,dataMap,dateStr,totalWeight,totalNetWeight);
	            	}else if(sheetName.equals("申报要素")) {
	            		declare(workBook,sheeti,form,formDataList,dataMap);
	            	}
	            } 
	            // 创建文件输出流，准备输出电子表格
	            out =  new FileOutputStream(newFilePath);
	            workBook.write(out);
	        } catch (Exception e) {
	            log.error("生成excel出现异常"+form.getFormId(),e);
	            throw new Exception(e.getMessage(), e);
	        } finally{
	            try {
	                if(out != null){
	                    out.flush();
	                    out.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        log.info("数据导出成功");
	    }
	    
	    /**
	     * 生成申报要素
	     * @param sheet
	     * @param form
	     * @param formDataList
	     * @param dataMap
	     */
	    public static void declare(Workbook workBook,Sheet sheet,OrderForm form,List<FormData> formDataList,Map<String,Object> dataMap) {
	    	//表单编号
	    	Row row=sheet.getRow(0);
	    	Cell cell=row.getCell(1);
	    	cell.setCellValue(form.getFormId());
	    	//蓝色阴影 中文标号
	    	CellStyle blueShade=sheet.getRow(1).getCell(0).getCellStyle();
	    	//蓝色居中 数字标号
	    	CellStyle blueCenter=sheet.getRow(1).getCell(1).getCellStyle();
	    	//品名样式
	    	CellStyle nameStyle=sheet.getRow(1).getCell(2).getCellStyle();
	    	//编号样式
	    	CellStyle hsStyle=sheet.getRow(1).getCell(6).getCellStyle();
	    	//关税信息样式
	    	CellStyle customsStyle=sheet.getRow(2).getCell(2).getCellStyle();
	    	//普通序号样式
	    	CellStyle numberStyle=sheet.getRow(3).getCell(1).getCellStyle();
	    	
	    	for(int i=0,rowIndex=1;i<formDataList.size();i++,rowIndex+=2) {
	    		FormData formData=formDataList.get(i);
	    		row=sheet.createRow(rowIndex);
	    		//蓝色阴影 中文标号
	    		cell=row.createCell(0);
	    		cell.setCellStyle(blueShade);
	    		cell.setCellValue(changeString(i+1));
	    		//蓝色居中 数字标号
	    		cell=row.createCell(1);
	    		cell.setCellStyle(blueCenter);
	    		cell.setCellValue(1);   		
	    		//品名
	    		cell=row.createCell(2);
	    		cell.setCellStyle(nameStyle);
	    		cell.setCellValue("品名: "+formData.getProduct().getCustomsDeclarationName());
	    		//编号
	    		cell=row.createCell(6);
	    		cell.setCellStyle(hsStyle);
	    		cell.setCellValue("H.S.编号: "+formData.getProduct().getCustomsCode());
	    		if(formData.getProduct().getCustomsInformation()!=null&&formData.getProduct().getCustomsInformation().length()!=0) {
	    			row=sheet.createRow(++rowIndex);
	    			cell=row.createCell(2);
	    			cell.setCellStyle(customsStyle);
	    			cell.setCellValue(formData.getProduct().getCustomsInformation());
	    		}
	    		String [] attribute=formData.getProduct().getDeclareElements().split("\\r\\n");
	    		for(int y=0,attributeIndex=2;y<attribute.length;y++,attributeIndex++) {
	    			row=sheet.createRow(++rowIndex);
	    			cell=row.createCell(1);
	    			cell.setCellStyle(numberStyle);
	    			cell.setCellValue(attributeIndex);
	    			cell=row.createCell(2);
	    			cell.setCellValue(attribute[y]);
	    		}	
	    	}
	    }
	    
	    /**
	     * 生成报关单
	     * @param sheet
	     * @param form
	     * @param formDataList
	     * @param dataMap
	     * @param dateStr
	     */
	    public static void clearanceGoods(Workbook workBook,Sheet sheet,OrderForm form,List<FormData> formDataList,Map<String,Object> dataMap,String dateStr, BigDecimal totalWeight, BigDecimal totalNetWeight) {
	    	//设置境内发货人
            Row invoiceNumber = sheet.getRow(2);
            Cell  invoiceCell=invoiceNumber.getCell(1);
            invoiceCell.setCellValue(form.getConsigner());          
            //设置境内收货人
            Row row = sheet.getRow(5);
            Cell rowCell=row.getCell(0);
            rowCell.setCellValue(form.getReceivingCompany());
            //设置运输方式
            rowCell=row.getCell(1);
            rowCell.setCellValue(form.getTransportation());
            //设置订单号
            row = sheet.getRow(9);
            rowCell=row.getCell(0);
            rowCell.setCellValue(form.getFormId());
            //设置贸易国
            rowCell=row.getCell(4);
            rowCell.setCellValue(form.getDestination());
            //设置配送地区
            rowCell=row.getCell(6);
            rowCell.setCellValue(form.getShippingAddress());
            //设置指运港
            rowCell=row.getCell(10);
            rowCell.setCellValue(form.getPortOfDestination());
            
            //设置离境港口
            rowCell=row.getCell(13);
            rowCell.setCellValue(form.getPortOfDestination());
            
            //设置包装种类
            row = sheet.getRow(11);
            rowCell=row.getCell(0);
            rowCell.setCellValue(form.getKindOfPackage());
            //设置总件数
            rowCell=row.getCell(4);
            rowCell.setCellValue(dataMap.get("packages").toString());
            //设置毛重
            rowCell=row.getCell(5);
            rowCell.setCellValue(totalWeight.toString());
            //设置净重
            rowCell=row.getCell(6);
            rowCell.setCellValue(totalNetWeight.toString());
            //设置成交方式
            rowCell=row.getCell(8);
            rowCell.setCellValue(form.getTermsOfDelivery());      
            
            if(form.getStandby03()!=null&&form.getStandby03().length()>0) {
            	  //设置运费
                rowCell=row.getCell(10);
                rowCell.setCellValue("$"+form.getStandby03());
            }
          
            
            int count=formDataList.size()/6;
            if(formDataList.size()%6!=0) {
            	count++;
            }
            // cloneSheet
            Sheet [] sheetArray=new Sheet[count];   
            sheetArray[0]=sheet;   
            for(int i=1,nameIndex=2;i<count;i++,nameIndex++) {
            	sheetArray[i]=cloneSheet(workBook,sheet,"报关单"+nameIndex);
            }     
            int i=17;      
            int isIndex=0;
            int sheetIndex=0;
            for(int index=0;index<formDataList.size();index++,i+=2) {
               if(isIndex!=sheetIndex) {
            	   i=17;
            	   isIndex=sheetIndex;
               }
               Sheet dataSheet=sheetArray[sheetIndex]; 
               setClearanceGoodsRow(dataSheet.getRow(i),dataSheet.getRow(i+1),new Long(index+1),formDataList.get(index),form.getDestination());
               sheetIndex=(index+1)/6;
            }     
	    }
	    
	    /**
	     * 装箱单生成渲染
	     * @param sheet
	     * @param form
	     * @param formDataList
	     * @param dataMap
	     */
	    public static void packing(Sheet sheet,OrderForm form,List<FormData> formDataList,Map<String,Object> dataMap,String dateStr, BigDecimal totalWeight, BigDecimal totalNetWeight) {
	    	  //设置发票编号
            Row invoiceNumber = sheet.getRow(5);
            Cell  invoiceCell=invoiceNumber.getCell(9);
            invoiceCell.setCellType(CellType.STRING);
            invoiceCell.setCellValue("发票编号: "+form.getFormId());
            //日期
            Row dateRow = sheet.getRow(8);
            Cell  dateCell=dateRow.getCell(9);
            dateCell.setCellType(CellType.STRING);
            dateCell.setCellValue("日期: "+dateStr);
            //收货公司
            Row receivingCompany = sheet.getRow(6);
            Cell  receivingCompanyCell=receivingCompany.getCell(0);
            receivingCompanyCell.setCellType(CellType.STRING);
            receivingCompanyCell.setCellValue("  "+form.getReceivingCompany());
            //地址介绍 离地港口到指运港 portOfDeparture portOfDestination
            Row portOfDepartureRow = sheet.getRow(13);
            Cell  portOfDepartureRowCell=portOfDepartureRow.getCell(2);
            portOfDepartureRowCell.setCellType(CellType.STRING);
            portOfDepartureRowCell.setCellValue("FROM "+form.getPortOfDeparture()+" TO "+form.getPortOfDestination()+" PORT "+form.getTransportation());  
            int i = 15;        
            Boolean isDrug=form.getFormType().equals("1");
            for (int index=0;index<formDataList.size(); i++,index++) {   
            	FormData formData=formDataList.get(index);
            	if(i==15) {       	
            		setPackingRow(sheet.getRow(i),new Long(index+1),formData,isDrug);
            	}else {				
				    sheet.shiftRows(i, sheet.getLastRowNum(), 1,true,false); 
				    sheet.createRow(i);			
            		copyRows(i-1,i,i,sheet);
            		setPackingRow(sheet.getRow(i),new Long(index+1),formData,isDrug);
            	}	            	
            }				 
		    sheet.removeRow(sheet.getRow(i));	  
		    //统计
		    Row packages=sheet.getRow(i+1);
            Cell  packagesCell=packages.getCell(3);
            packagesCell.setCellType(CellType.STRING);
            packagesCell.setCellValue(dataMap.get("packages").toString());	
            packagesCell=packages.getCell(7);
            packagesCell.setCellValue(totalNetWeight.doubleValue());
            packagesCell=packages.getCell(9);
            packagesCell.setCellValue(totalWeight.doubleValue());
            packagesCell=packages.getCell(11);
            packagesCell.setCellValue(form.getStandby01().indexOf("CBM")!=-1?form.getStandby01():form.getStandby01()+"CBM");
           
	    }
	    
	    /**
	     * 发票生成渲染
	     * @param sheet
	     * @param form
	     * @param formDataList
	     * @param dataMap
	     */
	    public static void invoice(Sheet sheet,OrderForm form,List<FormData> formDataList,Map<String,Object> dataMap,String dateStr) {
	    	 // 第一行从0开始算
            //设置发票编号
            Row invoiceNumber = sheet.getRow(3);
            Cell  invoiceCell=invoiceNumber.getCell(7);
            invoiceCell.setCellType(CellType.STRING);
            invoiceCell.setCellValue("发票编号: "+form.getFormId());
            //收货公司与日期
            Row receivingCompany = sheet.getRow(6);
            Cell  receivingCompanyCell=receivingCompany.getCell(0);
            receivingCompanyCell.setCellType(CellType.STRING);
            receivingCompanyCell.setCellValue("  "+form.getReceivingCompany());
            //日期
            Cell  dateCell=receivingCompany.getCell(7);
            dateCell.setCellType(CellType.STRING);
            dateCell.setCellValue("日期: "+dateStr);
            //单价标识
            Row row=sheet.getRow(14);
            Cell cell=row.getCell(6);
            cell.setCellValue(form.getTermsOfDelivery()+" "+form.getPortOfDeparture());
            
            //地址介绍 离地港口到指运港 portOfDeparture portOfDestination
            Row portOfDepartureRow = sheet.getRow(9);
            Cell  portOfDepartureRowCell=portOfDepartureRow.getCell(1);
            portOfDepartureRowCell.setCellType(CellType.STRING);
            portOfDepartureRowCell.setCellValue("FROM "+form.getPortOfDeparture()+" TO "+form.getPortOfDestination()); 
            Boolean isDrug=form.getFormType().equals("1");
            int i = 16;        
            for (int index=0;index<formDataList.size(); i++,index++) {   
            	FormData formData=formDataList.get(index);
            	if(i==16) {       	               
            		setRow(false,sheet.getRow(i),new Long(index+1),isDrug?formData.getProduct().getCustomsDeclarationName():formData.getProduct().getEnglishName(),formData.getPurchaseCount(),formData.getProduct().getUnit(),formData.getStandby01(),formData.getStandby02());
            	}else {				
				    sheet.shiftRows(i, sheet.getLastRowNum(), 1,true,false); 
				    sheet.createRow(i);			
            		copyRows(i-1,i,i,sheet);
            		setRow(true,sheet.getRow(i),new Long(index+1),isDrug?formData.getProduct().getCustomsDeclarationName():formData.getProduct().getEnglishName(),formData.getPurchaseCount(),formData.getProduct().getUnit(),formData.getStandby01(),formData.getStandby02());
            	}	            	
            }				
            if(sheet.getRow(i)!=null) {
            	  sheet.removeRow(sheet.getRow(i));	
            }	  
		    //总金额与交易方式
		    Row totalMoney=sheet.getRow(i+1)==null?sheet.createRow(i+1):sheet.getRow(i+1);
            Cell totalMoneyCell=totalMoney.getCell(7);    
            double totalMoneyDouble=((BigDecimal)dataMap.get("totalMoney")).doubleValue();
            totalMoneyCell.setCellValue(totalMoneyDouble);
            Cell termsOfDelivery=totalMoney.getCell(6);    
            termsOfDelivery.setCellValue(form.getTermsOfDelivery()+" TOTAL:");
            
		    //总件数
		    Row packages=sheet.getRow(i+3);
            Cell  packagesCell=packages.getCell(3);
            packagesCell.setCellType(CellType.STRING);
            packagesCell.setCellValue("  TOTAL PACKGED IN:"+dataMap.get("packages")+" PKGS");	
	    }
	    
	    
	 
	    
	    
	    /**
	     * 判断Excel的版本,获取Workbook
	     * @param in
	     * @param filename
	     * @return
	     * @throws IOException
	     */
	    public static Workbook getWorkbok(File file) throws Exception{
	        Workbook wb = null;
	        FileInputStream in = new FileInputStream(file);
	        if(file.getName().endsWith("xls")){     //Excel&nbsp;2003
	            wb = new HSSFWorkbook(in);
	        }else if(file.getName().endsWith("xlsx")){    // Excel 2007/2010
	            wb = new XSSFWorkbook(in);
	        }
	        return wb;
	    }
	    
	    private static Map<Class<?>, CellType[]> validateMap = new HashMap<>();
	    
	    static {
	        validateMap.put(String[].class, new CellType[]{CellType.STRING});
	        validateMap.put(Double[].class, new CellType[]{CellType.NUMERIC});
	        validateMap.put(String.class, new CellType[]{CellType.STRING});
	        validateMap.put(Double.class, new CellType[]{CellType.NUMERIC});
	        validateMap.put(Date.class, new CellType[]{CellType.NUMERIC, CellType.STRING});
	        validateMap.put(Integer.class, new CellType[]{CellType.NUMERIC});
	        validateMap.put(Float.class, new CellType[]{CellType.NUMERIC});
	        validateMap.put(Long.class, new CellType[]{CellType.NUMERIC});
	        validateMap.put(Boolean.class, new CellType[]{CellType.BOOLEAN});
	    }
	 
	    /**
	     * 获取cell类型的文字描述
	     *
	     * @param cellType <pre>
	     *                 CellType.BLANK
	     *                 CellType.BOOLEAN
	     *                 CellType.ERROR
	     *                 CellType.FORMULA
	     *                 CellType.NUMERIC
	     *                 CellType.STRING
	     *                 </pre>
	     * @return
	     */
	    private static String getCellTypeByInt(CellType cellType) {
	        if(cellType == CellType.BLANK) {
	            return "Null type";
	        } else if(cellType == CellType.BOOLEAN) {
	            return "Boolean type";
	        } else if(cellType == CellType.ERROR) {
	            return "Error type";
	        } else if(cellType == CellType.FORMULA) {
	            return "Formula type";
	        } else if(cellType == CellType.NUMERIC) {
	            return "Numeric type";
	        } else if(cellType == CellType.STRING) {
	            return "String type";
	        } else {
	            return "Unknown type";
	        }
	    }
	 
	    /**
	     * 获取单元格值
	     *
	     * @param cell
	     * @return
	     */
	    public static Object getCellValue(Cell cell) {
	        if (cell == null|| (cell.getCellTypeEnum() == CellType.STRING && StringUtils.isBlank(cell .getStringCellValue()))) {
	            return null;
	        }
	        CellType cellType = cell.getCellTypeEnum();
	        if(cellType == CellType.BLANK) {
	            return null;
	        } else if(cellType == CellType.BOOLEAN) {	        
	            return cell.getBooleanCellValue();
	        } else if(cellType == CellType.ERROR) {	        	
	            return cell.getErrorCellValue();
	        } else if(cellType == CellType.FORMULA) {
	            try {
	                if (HSSFDateUtil.isCellDateFormatted(cell)) {               	
	                    return cell.getDateCellValue();
	                } else {                	
	                    return cell.getNumericCellValue();
	                }
	            } catch (IllegalStateException e) {	            	
	                return cell.getRichStringCellValue();
	            }
	        }
	        else if(cellType == CellType.NUMERIC){
	            if (DateUtil.isCellDateFormatted(cell)) {            
	                return cell.getDateCellValue();
	            } else {            	
	                return cell.getNumericCellValue();
	            }
	        }
	        else if(cellType == CellType.STRING) {	        	
	            return cell.getStringCellValue();
	        } else {
	            return null;
	        }
	    }
	 
	    /**
		 * 复制行
		 * @param startRow 开始行
		 * @param endRow 结束行
		 * @param pPosition 目标行
		 * @param sheet 工作表对象
		 */
		public static void copyRows(int startRow,int endRow,int pPosition,Sheet sheet){
			int pStartRow=startRow;
			int pEndRow=endRow;
			int targetRowFrom;
			int targetRowTo;
			int columnCount;
			CellRangeAddress region=null;
			int i;
			int j;
			if(pStartRow == -1 || pEndRow == -1) {
				return;
			}
			// 拷贝合并的单元格
			for(i=0;i<sheet.getNumMergedRegions();i++){
				region=sheet.getMergedRegion(i);
				if((region.getFirstRow() >= pStartRow) && (region.getLastRow() <= pEndRow)) {
					targetRowFrom=region.getFirstRow()-pStartRow+pPosition;
					targetRowTo=region.getLastRow()-pStartRow+pPosition;
					CellRangeAddress newRegion=region.copy();
					newRegion.setFirstRow(targetRowFrom);
					newRegion.setFirstColumn(region.getFirstColumn());
					newRegion.setLastRow(targetRowTo);
					newRegion.setLastColumn(region.getLastColumn());
					sheet.addMergedRegion(newRegion);
				}
			}
			// 设置列宽
			for(i=pStartRow;i<=pEndRow;i++){
				Row sourceRow=sheet.getRow(i);
				columnCount=sourceRow.getLastCellNum();
				if(sourceRow != null){
					Row newRow=sheet.createRow(pPosition - pStartRow + i);
					newRow.setHeight(sourceRow.getHeight());
					for(j=0;j<columnCount;j++){
						Cell templateCell=sourceRow.getCell(j);
						if(templateCell != null){
							Cell newCell=newRow.createCell(j);
							copyCell(templateCell,newCell);
						}
					}
				}
			}
		}
		/**
		 * 复制单元格
		 * @param srcCell 原始单元格
		 * @param distCell 目标单元格
		 */
		public static void copyCell(Cell srcCell,Cell distCell){
			distCell.setCellStyle(srcCell.getCellStyle());
			if(srcCell.getCellComment() != null){
				distCell.setCellComment(srcCell.getCellComment());
			}
			int srcCellType=srcCell.getCellType();
			distCell.setCellType(srcCellType);
			if(srcCellType==XSSFCell.CELL_TYPE_NUMERIC){
				if(HSSFDateUtil.isCellDateFormatted(srcCell)){
					distCell.setCellValue(srcCell.getDateCellValue());
				}
				else{
					distCell.setCellValue(srcCell.getNumericCellValue());
				}
			}
			else if(srcCellType==XSSFCell.CELL_TYPE_STRING){
				distCell.setCellValue(srcCell.getRichStringCellValue());
			}
			else if(srcCellType==XSSFCell.CELL_TYPE_BLANK){
				// nothing21
			}
			else if(srcCellType==XSSFCell.CELL_TYPE_BOOLEAN){
				distCell.setCellValue(srcCell.getBooleanCellValue());
			}
			else if(srcCellType==XSSFCell.CELL_TYPE_ERROR){
				distCell.setCellErrorValue(srcCell.getErrorCellValue());
			}
			else if(srcCellType==XSSFCell.CELL_TYPE_FORMULA){
				distCell.setCellFormula(srcCell.getCellFormula());
			}
			else{ // nothing29
	 
			}
		}
		/**
		 * 表格中指定位置插入行
		 * @param sheet 工作表对象
		 * @param rowIndex 指定的行数
		 * @return 当前行对象
		 */
		public static XSSFRow insertRow(XSSFSheet sheet,int rowIndex) {
			XSSFRow row=null;
			if(sheet.getRow(rowIndex) != null) {
				int lastRowNo=sheet.getLastRowNum();
				sheet.shiftRows(rowIndex,lastRowNo,1);
			}
			row=sheet.createRow(rowIndex);
			return row;
		}

}
