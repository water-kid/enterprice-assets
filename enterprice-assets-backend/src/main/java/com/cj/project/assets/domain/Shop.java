package com.cj.project.assets.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cj.framework.aspectj.lang.annotation.Excel;
import com.cj.framework.web.domain.BaseEntity;

import javax.validation.constraints.NotBlank;

/**
 * 店铺详情统计对象 cc_shop
 * 
 * @author cc
 * @date 2025-01-01
 */
@Data
@TableName(value = "cc_shop")
public class Shop extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 平台 */
    @Excel(name = "平台")
    private String platform;

    /** 账号 */
    @Excel(name = "账号")
    private String account;

    @Excel(name = "编码")
    @NotBlank(message = "编码不能为空")
    private String code;

    /** 店铺名称 */
    @Excel(name = "店铺名称")
    private String shopName;

    /** 归属人 */
    @Excel(name = "归属人")
    private String ownerName;

    /** 官方账号所绑定手机 */
    @Excel(name = "官方账号所绑定手机")
    private String officialAccountTel;

    /** 官方账号归属人 */
    @Excel(name = "官方账号归属人")
    private String officialAccountOwner;

    /** 作者保证金 */
    @Excel(name = "作者保证金")
    private String authorDeposit;

    /** 营业执照(图) */
    @Excel(name = "营业执照(图)")
    private String businessLicenseImg;

    /** 法人 */
    @Excel(name = "法人")
    private String legalPerson;

    /** 保证金金额 */
    @Excel(name = "保证金金额")
    private String deposit;

    /** 经营状态 */
    @Excel(name = "经营状态")
    private String operationStatus;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPlatform(String platform) 
    {
        this.platform = platform;
    }

    public String getPlatform() 
    {
        return platform;
    }
    public void setAccount(String account) 
    {
        this.account = account;
    }

    public String getAccount() 
    {
        return account;
    }
    public void setShopName(String shopName) 
    {
        this.shopName = shopName;
    }

    public String getShopName() 
    {
        return shopName;
    }
    public void setOwnerName(String ownerName) 
    {
        this.ownerName = ownerName;
    }

    public String getOwnerName() 
    {
        return ownerName;
    }
    public void setOfficialAccountTel(String officialAccountTel) 
    {
        this.officialAccountTel = officialAccountTel;
    }

    public String getOfficialAccountTel() 
    {
        return officialAccountTel;
    }
    public void setOfficialAccountOwner(String officialAccountOwner) 
    {
        this.officialAccountOwner = officialAccountOwner;
    }

    public String getOfficialAccountOwner() 
    {
        return officialAccountOwner;
    }
    public void setAuthorDeposit(String authorDeposit) 
    {
        this.authorDeposit = authorDeposit;
    }

    public String getAuthorDeposit() 
    {
        return authorDeposit;
    }
    public void setBusinessLicenseImg(String businessLicenseImg) 
    {
        this.businessLicenseImg = businessLicenseImg;
    }

    public String getBusinessLicenseImg() 
    {
        return businessLicenseImg;
    }
    public void setLegalPerson(String legalPerson) 
    {
        this.legalPerson = legalPerson;
    }

    public String getLegalPerson() 
    {
        return legalPerson;
    }
    public void setDeposit(String deposit) 
    {
        this.deposit = deposit;
    }

    public String getDeposit() 
    {
        return deposit;
    }
    public void setOperationStatus(String operationStatus) 
    {
        this.operationStatus = operationStatus;
    }

    public String getOperationStatus() 
    {
        return operationStatus;
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
            .append("platform", getPlatform())
            .append("account", getAccount())
            .append("shopName", getShopName())
            .append("ownerName", getOwnerName())
            .append("officialAccountTel", getOfficialAccountTel())
            .append("officialAccountOwner", getOfficialAccountOwner())
            .append("authorDeposit", getAuthorDeposit())
            .append("businessLicenseImg", getBusinessLicenseImg())
            .append("legalPerson", getLegalPerson())
            .append("deposit", getDeposit())
            .append("operationStatus", getOperationStatus())
            .append("remarks", getRemarks())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
