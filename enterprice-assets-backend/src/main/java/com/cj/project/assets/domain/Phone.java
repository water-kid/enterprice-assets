package com.cj.project.assets.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.cj.framework.aspectj.lang.annotation.Excel;
import com.cj.framework.web.domain.BaseEntity;

/**
 * 手机号管理与使用对象 cc_phone
 * 
 * @author c c
 * @date 2025-01-01
 */
@TableName(value = "cc_phone")
public class Phone extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 所属部门 */
    @Excel(name = "所属部门")
    private String department;

    /** 使用人 */
    @Excel(name = "使用人")
    private String consumer;

    /** 所属手机编码 */
    @Excel(name = "所属手机编码")
    private String phoneCode;

    /** 实名 */
    @Excel(name = "实名")
    private String informationName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String tel;

    /** 运营商 */
    @Excel(name = "运营商")
    private String operator;

    /** 套餐 */
    @Excel(name = "套餐")
    private String plan;

    /** 号码状态 */
    @Excel(name = "号码状态")
    private String numberStatus;

    /** 资产状态 */
    @Excel(name = "资产状态")
    private String status;

    /** 二维码图片 */
    @Excel(name = "二维码图片")
    private String img;

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
    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }
    public void setConsumer(String consumer) 
    {
        this.consumer = consumer;
    }

    public String getConsumer() 
    {
        return consumer;
    }
    public void setPhoneCode(String phoneCode) 
    {
        this.phoneCode = phoneCode;
    }

    public String getPhoneCode() 
    {
        return phoneCode;
    }
    public void setInformationName(String informationName) 
    {
        this.informationName = informationName;
    }

    public String getInformationName() 
    {
        return informationName;
    }
    public void setTel(String tel) 
    {
        this.tel = tel;
    }

    public String getTel() 
    {
        return tel;
    }
    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return operator;
    }
    public void setPlan(String plan) 
    {
        this.plan = plan;
    }

    public String getPlan() 
    {
        return plan;
    }
    public void setNumberStatus(String numberStatus) 
    {
        this.numberStatus = numberStatus;
    }

    public String getNumberStatus() 
    {
        return numberStatus;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
            .append("department", getDepartment())
            .append("consumer", getConsumer())
            .append("phoneCode", getPhoneCode())
            .append("informationName", getInformationName())
            .append("tel", getTel())
            .append("operator", getOperator())
            .append("plan", getPlan())
            .append("numberStatus", getNumberStatus())
            .append("status", getStatus())
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
