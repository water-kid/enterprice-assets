package com.cj.project.assets.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.cj.framework.web.domain.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 外借信息
 * @TableName cc_assets_loans
 */
@TableName(value ="cc_assets_loans")
@Data
public class AssetsLoans extends BaseEntity implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 资产id
     */
    @NotNull(message = "借用资产不能为空")
    private Integer assetsId;

    /**
     * 谁借的
     */
    @NotBlank(message = "借用人不能为空")
    @NotNull(message = "借用人不能为空")
    private String borrower;

    /**
     * 借出时间
     */
    private Date borrowDate;

    /**
     * 归还时间
     */
    private Date returnDate;

    /**
     * 状态,0:借出 1:归还
     */
    private String status;

    /**
     * 
     */
//    private String createBy;
//
//    /**
//     *
//     */
//    private String updateBy;
//
//    /**
//     *
//     */
//    private Date createTime;
//
//    /**
//     *
//     */
//    private Date updateTime;

    /**
     * 备注
     */
//    private String remark;


    @Positive(message = "请输入正确的借出数量")
    @NotNull(message = "请输入正确的借出数量")
    private Integer borrowCount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;




}