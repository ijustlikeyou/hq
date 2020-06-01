package com.ruoyi.project.hq.product.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 瀚淇产品对象 hq_product
 * 
 * @author lty
 * @date 2020-03-24
 */
public class Product extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public Product() {};
    /**
         *  自定义赋值
     * @param value
     * @param type
     */
    public Product(String value,int type) {
    	switch (type) {
		case 1:
			this.productId=value;
			break;
		default:
			this.id=value;
			break;
		}
    }
    /** 主键id */
    @Excel(name = "产品id",width = 50)
    private String id;

    /** 海关编码 */
    @Excel(name = "海关编码")
    private String customsCode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 英文名称 */
    @Excel(name = "英文名称")
    private String englishName;

    /** 报关名称 */
    @Excel(name = "报关名称")
    private String customsDeclarationName;

    /** 关税信息 */
    @Excel(name = "关税信息")
    private String customsInformation;

    /** 产品编号 */
    @Excel(name = "产品编号")
    private String productId;

    /** 产品类型 */
    @Excel(name = "产品类型",readConverterExp = "1=药品,2=摩配")
    private String productType;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String unit;

    /** 退税率 */
    @Excel(name = "退税率")
    private String taxRefundRate;

    /** 退税系数 */
    @Excel(name = "退税系数")
    private String taxRebatesCoefficient;

    /** 产品型号 */
    @Excel(name = "产品型号")
    private String productModel;

    /** 原产地 */
    @Excel(name = "原产地")
    private String place;

    /** 申报要素 */
    @Excel(name = "申报要素")
    private String declareElements;

    /** 包装单位 */
    @Excel(name = "备用字段1 包装单位")
    private String standby01;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    private String standby02;

    /** 备用字段3 */
    @Excel(name = "备用字段3")
    private String standby03;

    /** 创造人id */
    @Excel(name = "创造人id")
    private String createId;

    /** 创造时间 */
    @Excel(name = "创造时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 最后修改人 */
    @Excel(name = "最后修改人")
    private String lastUpdateId;

    /** 最后修改时间 */
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdateDate;

    /** ip地址 */
    @Excel(name = "ip地址")
    private String ip;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setCustomsCode(String customsCode) 
    {
        this.customsCode = customsCode;
    }

    public String getCustomsCode() 
    {
        return customsCode;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setEnglishName(String englishName) 
    {
        this.englishName = englishName;
    }

    public String getEnglishName() 
    {
        return englishName;
    }
    public void setCustomsDeclarationName(String customsDeclarationName) 
    {
        this.customsDeclarationName = customsDeclarationName;
    }

    public String getCustomsDeclarationName() 
    {
        return customsDeclarationName;
    }
    public void setCustomsInformation(String customsInformation) 
    {
        this.customsInformation = customsInformation;
    }

    public String getCustomsInformation() 
    {
        return customsInformation;
    }
    public void setProductId(String productId) 
    {
        this.productId = productId;
    }

    public String getProductId() 
    {
        return productId;
    }
    public void setProductType(String productType) 
    {
        this.productType = productType;
    }

    public String getProductType() 
    {
        return productType;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }
    public void setTaxRefundRate(String taxRefundRate) 
    {
        this.taxRefundRate = taxRefundRate;
    }

    public String getTaxRefundRate() 
    {
        return taxRefundRate;
    }
    public void setTaxRebatesCoefficient(String taxRebatesCoefficient) 
    {
        this.taxRebatesCoefficient = taxRebatesCoefficient;
    }

    public String getTaxRebatesCoefficient() 
    {
        return taxRebatesCoefficient;
    }
    public void setProductModel(String productModel) 
    {
        this.productModel = productModel;
    }

    public String getProductModel() 
    {
        return productModel;
    }
    public void setPlace(String place) 
    {
        this.place = place;
    }

    public String getPlace() 
    {
        return place;
    }
    public void setDeclareElements(String declareElements) 
    {
        this.declareElements = declareElements;
    }

    public String getDeclareElements() 
    {
        return declareElements;
    }
    public void setStandby01(String standby01) 
    {
        this.standby01 = standby01;
    }

    public String getStandby01() 
    {
        return standby01;
    }
    public void setStandby02(String standby02) 
    {
        this.standby02 = standby02;
    }

    public String getStandby02() 
    {
        return standby02;
    }
    public void setStandby03(String standby03) 
    {
        this.standby03 = standby03;
    }

    public String getStandby03() 
    {
        return standby03;
    }
    public void setCreateId(String createId) 
    {
        this.createId = createId;
    }

    public String getCreateId() 
    {
        return createId;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public Date getCreateDate() 
    {
        return createDate;
    }
    public void setLastUpdateId(String lastUpdateId) 
    {
        this.lastUpdateId = lastUpdateId;
    }

    public String getLastUpdateId() 
    {
        return lastUpdateId;
    }
    public void setLastUpdateDate(Date lastUpdateDate) 
    {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getLastUpdateDate() 
    {
        return lastUpdateDate;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("customsCode", getCustomsCode())
            .append("productName", getProductName())
            .append("englishName", getEnglishName())
            .append("customsDeclarationName", getCustomsDeclarationName())
            .append("customsInformation", getCustomsInformation())
            .append("productId", getProductId())
            .append("productType", getProductType())
            .append("unit", getUnit())
            .append("taxRefundRate", getTaxRefundRate())
            .append("taxRebatesCoefficient", getTaxRebatesCoefficient())
            .append("productModel", getProductModel())
            .append("place", getPlace())
            .append("declareElements", getDeclareElements())
            .append("standby01", getStandby01())
            .append("standby02", getStandby02())
            .append("standby03", getStandby03())
            .append("createId", getCreateId())
            .append("createDate", getCreateDate())
            .append("lastUpdateId", getLastUpdateId())
            .append("lastUpdateDate", getLastUpdateDate())
            .append("ip", getIp())
            .toString();
    }
}
