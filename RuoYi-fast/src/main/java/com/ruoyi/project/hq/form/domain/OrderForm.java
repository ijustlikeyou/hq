package com.ruoyi.project.hq.form.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 瀚淇订单对象 hq_order_form
 * 
 * @author lty
 * @date 2020-03-24
 */
public class OrderForm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String id;

    /** 配送地址 */
    @Excel(name = "配送地址")
    private String shippingAddress;

    /** 目的地 */
    @Excel(name = "目的地")
    private String destination;

    /** 收货公司 */
    @Excel(name = "收货公司")
    private String receivingCompany;

    /** 单号 */
    @Excel(name = "单号")
    private String formId;

    /** 总数量 */
    @Excel(name = "总数量")
    private Long totalQuantity;

    /** 总立方 */
    @Excel(name = "总立方")
    private String totalCubic;

    /** 总重量 */
    @Excel(name = "总重量")
    private String totalWeight;

    /** 总金额 */
    @Excel(name = "总金额")
    private String totalMoney;

    /** 美元汇率 */
    @Excel(name = "美元汇率")
    private String dollarCurrencyRate;

    /** 退税率 */
    @Excel(name = "退税率")
    private String taxRefundRate;

    /** 总美元报关 */
    @Excel(name = "总美元报关")
    private String totalDollarsCustoms;

    /** 总件数 */
    @Excel(name = "总件数")
    private Long totalCount;

    /** 备用字段1 */
    @Excel(name = "备用字段1 尺码")
    private String standby01;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    private String standby02;

    /** 备用字段3 */
    @Excel(name = "备用字段3")
    private String standby03;

    /** 创建人id */
    @Excel(name = "创建人id")
    private String createId;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 最后修改人id */
    @Excel(name = "最后修改人id")
    private String lastUpdateId;

    /** 最后修改时间 */
    @Excel(name = "最后修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdateDate;

    /** ip */
    @Excel(name = "ip")
    private String ip;

    /** 订单类型 */
    @Excel(name = "订单类型")
    private String formType;

    /** 指运港 */
    @Excel(name = "指运港")
    private String portOfDestination;

    /** 离境口岸 */
    @Excel(name = "离境口岸")
    private String portOfDeparture;

    /** 成交方式 */
    @Excel(name = "成交方式")
    private String termsOfDelivery;

    /** 包装种类 */
    @Excel(name = "包装种类")
    private String kindOfPackage;

    /** 运输方式 */
    @Excel(name = "运输方式")
    private String transportation;

    /** 发货人 */
    @Excel(name = "发货人")
    private String consigner;

    /** excel文件路径 */
    @Excel(name = "excel文件路径")
    private String excelFilePath;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setShippingAddress(String shippingAddress) 
    {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAddress() 
    {
        return shippingAddress;
    }
    public void setDestination(String destination) 
    {
        this.destination = destination;
    }

    public String getDestination() 
    {
        return destination;
    }
    public void setReceivingCompany(String receivingCompany) 
    {
        this.receivingCompany = receivingCompany;
    }

    public String getReceivingCompany() 
    {
        return receivingCompany;
    }
    public void setFormId(String formId) 
    {
        this.formId = formId;
    }

    public String getFormId() 
    {
        return formId;
    }
    public void setTotalQuantity(Long totalQuantity) 
    {
        this.totalQuantity = totalQuantity;
    }

    public Long getTotalQuantity() 
    {
        return totalQuantity;
    }
    public void setTotalCubic(String totalCubic) 
    {
        this.totalCubic = totalCubic;
    }

    public String getTotalCubic() 
    {
        return totalCubic;
    }
    public void setTotalWeight(String totalWeight) 
    {
        this.totalWeight = totalWeight;
    }

    public String getTotalWeight() 
    {
        return totalWeight;
    }
    public void setTotalMoney(String totalMoney) 
    {
        this.totalMoney = totalMoney;
    }

    public String getTotalMoney() 
    {
        return totalMoney;
    }
    public void setDollarCurrencyRate(String dollarCurrencyRate) 
    {
        this.dollarCurrencyRate = dollarCurrencyRate;
    }

    public String getDollarCurrencyRate() 
    {
        return dollarCurrencyRate;
    }
    public void setTaxRefundRate(String taxRefundRate) 
    {
        this.taxRefundRate = taxRefundRate;
    }

    public String getTaxRefundRate() 
    {
        return taxRefundRate;
    }
    public void setTotalDollarsCustoms(String totalDollarsCustoms) 
    {
        this.totalDollarsCustoms = totalDollarsCustoms;
    }

    public String getTotalDollarsCustoms() 
    {
        return totalDollarsCustoms;
    }
    public void setTotalCount(Long totalCount) 
    {
        this.totalCount = totalCount;
    }

    public Long getTotalCount() 
    {
        return totalCount;
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
    public void setFormType(String formType) 
    {
        this.formType = formType;
    }

    public String getFormType() 
    {
        return formType;
    }
    public void setPortOfDestination(String portOfDestination) 
    {
        this.portOfDestination = portOfDestination;
    }

    public String getPortOfDestination() 
    {
        return portOfDestination;
    }
    public void setPortOfDeparture(String portOfDeparture) 
    {
        this.portOfDeparture = portOfDeparture;
    }

    public String getPortOfDeparture() 
    {
        return portOfDeparture;
    }
    public void setTermsOfDelivery(String termsOfDelivery) 
    {
        this.termsOfDelivery = termsOfDelivery;
    }

    public String getTermsOfDelivery() 
    {
        return termsOfDelivery;
    }
    public void setKindOfPackage(String kindOfPackage) 
    {
        this.kindOfPackage = kindOfPackage;
    }

    public String getKindOfPackage() 
    {
        return kindOfPackage;
    }
    public void setTransportation(String transportation) 
    {
        this.transportation = transportation;
    }

    public String getTransportation() 
    {
        return transportation;
    }
    public void setConsigner(String consigner) 
    {
        this.consigner = consigner;
    }

    public String getConsigner() 
    {
        return consigner;
    }
    public void setExcelFilePath(String excelFilePath) 
    {
        this.excelFilePath = excelFilePath;
    }

    public String getExcelFilePath() 
    {
        return excelFilePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("shippingAddress", getShippingAddress())
            .append("destination", getDestination())
            .append("receivingCompany", getReceivingCompany())
            .append("formId", getFormId())
            .append("totalQuantity", getTotalQuantity())
            .append("totalCubic", getTotalCubic())
            .append("totalWeight", getTotalWeight())
            .append("totalMoney", getTotalMoney())
            .append("dollarCurrencyRate", getDollarCurrencyRate())
            .append("taxRefundRate", getTaxRefundRate())
            .append("totalDollarsCustoms", getTotalDollarsCustoms())
            .append("totalCount", getTotalCount())
            .append("standby01", getStandby01())
            .append("standby02", getStandby02())
            .append("standby03", getStandby03())
            .append("createId", getCreateId())
            .append("createDate", getCreateDate())
            .append("lastUpdateId", getLastUpdateId())
            .append("lastUpdateDate", getLastUpdateDate())
            .append("ip", getIp())
            .append("formType", getFormType())
            .append("portOfDestination", getPortOfDestination())
            .append("portOfDeparture", getPortOfDeparture())
            .append("termsOfDelivery", getTermsOfDelivery())
            .append("kindOfPackage", getKindOfPackage())
            .append("transportation", getTransportation())
            .append("consigner", getConsigner())
            .append("excelFilePath", getExcelFilePath())
            .toString();
    }
}
