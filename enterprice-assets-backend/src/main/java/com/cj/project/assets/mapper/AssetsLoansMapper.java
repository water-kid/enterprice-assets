package com.cj.project.assets.mapper;

import com.cj.project.assets.domain.AssetsLoans;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author chenjie
* @description 针对表【cc_assets_loans(外借信息)】的数据库操作Mapper
* @createDate 2025-01-01 21:36:23
* @Entity com.cj.project.assets.domain.AssetsLoans
*/
public interface AssetsLoansMapper extends BaseMapper<AssetsLoans> {
    /**
     * 查询外借信息
     *
     * @param id 外借信息主键
     * @return 外借信息
     */
    public AssetsLoans selectAssetsLoansById(Long id);

    /**
     * 查询外借信息列表
     *
     * @param assetsLoans 外借信息
     * @return 外借信息集合
     */
    public List<AssetsLoans> selectAssetsLoansList(AssetsLoans assetsLoans);

    /**
     * 新增外借信息
     *
     * @param assetsLoans 外借信息
     * @return 结果
     */
    public int insertAssetsLoans(AssetsLoans assetsLoans);

    /**
     * 修改外借信息
     *
     * @param assetsLoans 外借信息
     * @return 结果
     */
    public int updateAssetsLoans(AssetsLoans assetsLoans);

    /**
     * 删除外借信息
     *
     * @param id 外借信息主键
     * @return 结果
     */
    public int deleteAssetsLoansById(Long id);

    /**
     * 批量删除外借信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAssetsLoansByIds(Long[] ids);
}




