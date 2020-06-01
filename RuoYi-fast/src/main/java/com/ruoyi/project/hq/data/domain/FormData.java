package com.ruoyi.project.hq.data.domain;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import com.ruoyi.project.hq.product.domain.Product;

/**
 * 瀚淇订单数据对象 hq_form_data
 * 
 * @author lty
 * @date 2020-03-28
 */
public class FormData extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public FormData() {
	};
	
	public FormData(String orderFormId) {
		this.orderFormId=orderFormId;
	}
	public FormData(Product product, Map<String, String> row,int sort,Boolean isDrug) {
       this.product=product;
       this.productName=product.getProductName();
       this.productId=product.getId();
       this.sort=new Long(sort);
       this.unitPrice=row.get("单价")==null||row.get("单价").length()==0?"0":row.get("单价");
       this.money=row.get("金额")==null||row.get("金额").length()==0?"0":row.get("金额");
       if(isDrug) {
    	   //药品初始化函数
    	   this.cargoCount=row.get("到货数量")==null||row.get("到货数量").length()==0?"0":row.get("到货数量");
           this.presenterCount=row.get("赠送数量")==null||row.get("赠送数量").length()==0?"0":row.get("赠送数量");
           this.purchaseCount=row.get("采购数量")==null||row.get("采购数量").length()==0?"0":row.get("采购数量");
           this.boxCount=row.get("单箱数")==null||row.get("单箱数").length()==0?new Long(0):new Long(row.get("单箱数"));
           this.pieceCount=row.get("件数")==null||row.get("件数").length()==0?0:new Long(row.get("件数"));
           this.cubeCount=row.get("单个立方数")==null||row.get("单个立方数").length()==0?"0":row.get("单个立方数");
           this.totalCubeCount=row.get("总立方数")==null||row.get("总立方数").length()==0?"0":row.get("总立方数");
           this.weight=row.get("单个重量")==null||row.get("单个重量").length()==0?"0":row.get("单个重量");
           this.totalWeight=row.get("总重量")==null||row.get("总重量").length()==0?"0":row.get("总重量");
       }else {
    	   //摩配初始化函数 
           this.purchaseCount=row.get("发货数量")==null||row.get("发货数量").length()==0?"0":row.get("发货数量");
           this.boxCount=row.get("单箱数量")==null||row.get("单箱数量").length()==0?new Long(0):new Long(row.get("单箱数量"));
           this.pieceCount=row.get("发货箱数")==null?null:new Long(row.get("发货箱数"));
           this.cubeCount=row.get("单箱体积(m3)")==null||row.get("单箱体积(m3)").length()==0?"0":row.get("单箱体积(m3)");
           this.totalCubeCount=row.get("库存总体积(m3）")==null||row.get("库存总体积(m3）").length()==0?"0":row.get("库存总体积(m3）");
           this.weight=row.get("单箱重量(kg)")==null||row.get("单箱重量(kg)").length()==0?"0":row.get("单箱重量(kg)");
           this.totalWeight=row.get("库存总重量（kg)")==null||row.get("库存总重量（kg)").length()==0?"0":row.get("库存总重量（kg)");
       }
      
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	private Product product;

	/** 主键id */
	private String id;

	/** 产品名称 */
	@Excel(name = "产品名称")
	private String productName;

	/** 产品id */
	@Excel(name = "产品id")
	private String productId;

	/** 订单id */
	@Excel(name = "订单id")
	private String orderFormId;

	/** 到货数量 */
	@Excel(name = "到货数量")
	private String cargoCount;

	/** 赠送数量 */
	@Excel(name = "赠送数量")
	private String presenterCount;

	/** 采购数量 */
	@Excel(name = "采购数量")
	private String purchaseCount;

	/** 单箱数 */
	@Excel(name = "单箱数")
	private Long boxCount;

	/** 件数 */
	@Excel(name = "件数")
	private Long pieceCount;

	/** 立方数 */
	@Excel(name = "立方数")
	private String cubeCount;

	/** 总立方数 */
	@Excel(name = "总立方数")
	private String totalCubeCount;

	/** 重量 */
	@Excel(name = "重量")
	private String weight;

	/** 总重量 */
	@Excel(name = "总重量")
	private String totalWeight;

	/** 单价 */
	@Excel(name = "单价")
	private String unitPrice;

	/** 金额 */
	@Excel(name = "金额")
	private String money;

	/** 排序 */
	@Excel(name = "排序")
	private Long sort;

	/** 备用字段01 */
	@Excel(name = "美元报关单价")
	private String standby01;

	/** 备用字段02 */
	@Excel(name = "总金额")
	private String standby02;

	/** 备用字段03 */
	@Excel(name = "摩配运费")
	private String standby03;
	
	/** 净重*/
	@Excel(name = "净重")
	private String suttle;
	
	

	public String getSuttle() {
		return suttle;
	}

	public void setSuttle(String suttle) {
		this.suttle = suttle;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductId() {
		return productId;
	}

	public void setOrderFormId(String orderFormId) {
		this.orderFormId = orderFormId;
	}

	public String getOrderFormId() {
		return orderFormId;
	}

	public void setCargoCount(String cargoCount) {
		this.cargoCount = cargoCount;
	}

	public String getCargoCount() {
		return cargoCount;
	}

	public void setPresenterCount(String presenterCount) {
		this.presenterCount = presenterCount;
	}

	public String getPresenterCount() {
		return presenterCount;
	}

	public void setPurchaseCount(String purchaseCount) {
		this.purchaseCount = purchaseCount;
	}

	public String getPurchaseCount() {
		return purchaseCount;
	}

	public void setBoxCount(Long boxCount) {
		this.boxCount = boxCount;
	}

	public Long getBoxCount() {
		return boxCount;
	}

	public void setPieceCount(Long pieceCount) {
		this.pieceCount = pieceCount;
	}

	public Long getPieceCount() {
		return pieceCount;
	}

	public void setCubeCount(String cubeCount) {
		this.cubeCount = cubeCount;
	}

	public String getCubeCount() {
		return cubeCount;
	}

	public void setTotalCubeCount(String totalCubeCount) {
		this.totalCubeCount = totalCubeCount;
	}

	public String getTotalCubeCount() {
		return totalCubeCount;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWeight() {
		return weight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getMoney() {
		return money;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public Long getSort() {
		return sort;
	}

	public void setStandby01(String standby01) {
		this.standby01 = standby01;
	}

	public String getStandby01() {
		return standby01;
	}

	public void setStandby02(String standby02) {
		
		this.standby02 = standby02;
	}

	public String getStandby02() {
		return standby02;
	}

	public void setStandby03(String standby03) {
		this.standby03 = standby03;
	}

	public String getStandby03() {
		return standby03;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("productName", getProductName()).append("productId", getProductId())
				.append("orderFormId", getOrderFormId()).append("cargoCount", getCargoCount())
				.append("presenterCount", getPresenterCount()).append("purchaseCount", getPurchaseCount())
				.append("boxCount", getBoxCount()).append("pieceCount", getPieceCount())
				.append("cubeCount", getCubeCount()).append("totalCubeCount", getTotalCubeCount())
				.append("weight", getWeight()).append("totalWeight", getTotalWeight())
				.append("unitPrice", getUnitPrice()).append("money", getMoney()).append("sort", getSort())
				.append("standby01", getStandby01()).append("standby02", getStandby02())
				.append("standby03", getStandby03()).toString();
	}
}
