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

/**
 * 样品库存管理对象 cc_sample
 * 
 * @author cc
 * @date 2025-01-01
 */
@TableName(value = "cc_sample")
@Data
public class Sample extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @TableId(type = IdType.AUTO)
    private Long id;
    @Excel(name = "编码")
    @NotBlank(message = "编码不能为空")
    private String code;

    /** 样品购买人 */
    @Excel(name = "样品购买人")
    private String purchaser;

    /** 样品金额 */
    @Excel(name = "样品金额")
    private BigDecimal amount;

    /** 样品规格 */
    @Excel(name = "样品规格")
    private String specification;

    /** store */
    @Excel(name = "store")
    private String store;

    /** 购买时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "购买时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseTime;

    /** 购买图片 */
    @Excel(name = "购买图片")
    private String purchaseImg;

    /** 购买人所属团队 */
    @Excel(name = "购买人所属团队")
    private String purchaserTeam;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPurchaser(String purchaser) 
    {
        this.purchaser = purchaser;
    }

    public String getPurchaser() 
    {
        return purchaser;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }
    public void setStore(String store) 
    {
        this.store = store;
    }

    public String getStore() 
    {
        return store;
    }
    public void setPurchaseTime(Date purchaseTime) 
    {
        this.purchaseTime = purchaseTime;
    }

    public Date getPurchaseTime() 
    {
        return purchaseTime;
    }
    public void setPurchaseImg(String purchaseImg) 
    {
        this.purchaseImg = purchaseImg;
    }

    public String getPurchaseImg() 
    {
        return purchaseImg;
    }
    public void setPurchaserTeam(String purchaserTeam) 
    {
        this.purchaserTeam = purchaserTeam;
    }

    public String getPurchaserTeam() 
    {
        return purchaserTeam;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("purchaser", getPurchaser())
            .append("amount", getAmount())
            .append("specification", getSpecification())
            .append("store", getStore())
            .append("purchaseTime", getPurchaseTime())
            .append("purchaseImg", getPurchaseImg())
            .append("purchaserTeam", getPurchaserTeam())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
