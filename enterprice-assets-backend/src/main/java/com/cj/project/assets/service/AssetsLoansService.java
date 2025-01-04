package com.cj.project.assets.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cj.common.utils.DateUtils;
import com.cj.project.assets.domain.AssetsLoans;
import com.cj.project.assets.mapper.AssetsLoansMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chenjie
* @description 针对表【cc_assets_loans(外借信息)】的数据库操作Service实现
* @createDate 2025-01-01 21:36:23
*/
@Service
public class AssetsLoansService extends ServiceImpl<AssetsLoansMapper, AssetsLoans>
{

    @Autowired
    private AssetsLoansMapper assetsLoansMapper;

    /**
     * 查询外借信息
     *
     * @param id 外借信息主键
     * @return 外借信息
     */
    public AssetsLoans selectAssetsLoansById(Long id)
    {
        return assetsLoansMapper.selectAssetsLoansById(id);
    }

    /**
     * 查询外借信息列表
     *
     * @param assetsLoans 外借信息
     * @return 外借信息
     */
    public List<AssetsLoans> selectAssetsLoansList(AssetsLoans assetsLoans)
    {
        return assetsLoansMapper.selectAssetsLoansList(assetsLoans);
    }

    /**
     * 新增外借信息
     *
     * @param assetsLoans 外借信息
     * @return 结果
     */
    public int insertAssetsLoans(AssetsLoans assetsLoans)
    {
        assetsLoans.setCreateTime(DateUtils.getNowDate());
        return assetsLoansMapper.insertAssetsLoans(assetsLoans);
    }

    /**
     * 修改外借信息
     *
     * @param assetsLoans 外借信息
     * @return 结果
     */
    public int updateAssetsLoans(AssetsLoans assetsLoans)
    {
        assetsLoans.setUpdateTime(DateUtils.getNowDate());
        return assetsLoansMapper.updateAssetsLoans(assetsLoans);
    }

    /**
     * 批量删除外借信息
     *
     * @param ids 需要删除的外借信息主键
     * @return 结果
     */
    public int deleteAssetsLoansByIds(Long[] ids)
    {
        return assetsLoansMapper.deleteAssetsLoansByIds(ids);
    }

    /**
     * 删除外借信息信息
     *
     * @param id 外借信息主键
     * @return 结果
     */
    public int deleteAssetsLoansById(Long id)
    {
        return assetsLoansMapper.deleteAssetsLoansById(id);
    }

}




