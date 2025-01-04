package com.cj.project.assets.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cj.framework.aspectj.lang.annotation.Excel;
import com.cj.framework.web.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 固定资产盘点明细对象 cc_assets
 * 
 * @author cc
 * @date 2025-01-01
 */
@Data
@TableName(value = "cc_assets")
public class Assets extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 购买日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "购买日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 资产类别 */
    @Excel(name = "资产类别")
    private String category;

    /** 固定资产编号 */
    @Excel(name = "固定资产编号")
    @NotBlank(message = "固定资产编号不能为空")
    private String assetsCode;

    /** 资产用途分类 */
    @Excel(name = "资产用途分类")
    private String assetUsage;

    /** 资产名 */
    @Excel(name = "资产名")
    @NotBlank(message = "资产名不能为空")
    private String name;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 数量/单位 */
    @Excel(name = "数量/单位")
    private String quantity;

    /** 颜色 */
    @Excel(name = "颜色")
    private String color;

    /** 内存 */
    @Excel(name = "内存")
    private String memory;

    /** 型号 */
    @Excel(name = "型号")
    private String model;

    /** 序列号/MEID */
    @Excel(name = "序列号/MEID")
    private String serialNumberMeid;

    /** 购买价 */
    @Excel(name = "购买价")
    private BigDecimal price;

    /** 采购人 */
    @Excel(name = "采购人")
    @NotBlank(message = "采购人不能为空")
    private String purchaser;

    /** 使用人 */
    @Excel(name = "使用人")
    private String consumer;

    /** 使用部门 */
    @Excel(name = "使用部门")
    private String consumerDepartment;

    /** 存放地 */
    @Excel(name = "存放地")
    private String store;

    /** 资产照 */
    @Excel(name = "资产照")
    private String img;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;


    @NotNull(message = "总个数不能为空")
    private Integer totalCount;
    @NotNull(message = "剩余个数不能为空")
    private Integer remainingCount;
    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPurchaseDate(Date purchaseDate) 
    {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() 
    {
        return purchaseDate;
    }
    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }
    public void setAssetsCode(String assetsCode) 
    {
        this.assetsCode = assetsCode;
    }

    public String getAssetsCode() 
    {
        return assetsCode;
    }
    public void setAssetUsage(String assetUsage) 
    {
        this.assetUsage = assetUsage;
    }

    public String getAssetUsage() 
    {
        return assetUsage;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setBrand(String brand) 
    {
        this.brand = brand;
    }

    public String getBrand() 
    {
        return brand;
    }
    public void setQuantity(String quantity) 
    {
        this.quantity = quantity;
    }

    public String getQuantity() 
    {
        return quantity;
    }
    public void setColor(String color) 
    {
        this.color = color;
    }

    public String getColor() 
    {
        return color;
    }
    public void setMemory(String memory) 
    {
        this.memory = memory;
    }

    public String getMemory() 
    {
        return memory;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setSerialNumberMeid(String serialNumberMeid) 
    {
        this.serialNumberMeid = serialNumberMeid;
    }

    public String getSerialNumberMeid() 
    {
        return serialNumberMeid;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setPurchaser(String purchaser) 
    {
        this.purchaser = purchaser;
    }

    public String getPurchaser() 
    {
        return purchaser;
    }
    public void setConsumer(String consumer) 
    {
        this.consumer = consumer;
    }

    public String getConsumer() 
    {
        return consumer;
    }
    public void setConsumerDepartment(String consumerDepartment) 
    {
        this.consumerDepartment = consumerDepartment;
    }

    public String getConsumerDepartment() 
    {
        return consumerDepartment;
    }
    public void setStore(String store) 
    {
        this.store = store;
    }

    public String getStore() 
    {
        return store;
    }
    public void setImg(String img) 
    {
        this.img = img;
    }

    public String getImg() 
    {
        return img;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("purchaseDate", getPurchaseDate())
            .append("category", getCategory())
            .append("assetsCode", getAssetsCode())
            .append("assetUsage", getAssetUsage())
            .append("name", getName())
            .append("brand", getBrand())
            .append("quantity", getQuantity())
            .append("color", getColor())
            .append("memory", getMemory())
            .append("model", getModel())
            .append("serialNumberMeid", getSerialNumberMeid())
            .append("price", getPrice())
            .append("purchaser", getPurchaser())
            .append("consumer", getConsumer())
            .append("consumerDepartment", getConsumerDepartment())
            .append("store", getStore())
            .append("img", getImg())
            .append("remarks", getRemarks())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
