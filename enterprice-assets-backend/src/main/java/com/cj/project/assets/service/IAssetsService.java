package com.cj.project.assets.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cj.project.assets.domain.Assets;
import com.cj.project.assets.domain.AssetsLoans;

/**
 * 固定资产盘点明细Service接口
 * 
 * @author cc
 * @date 2025-01-01
 */
public interface IAssetsService extends IService<Assets>
{
    /**
     * 查询固定资产盘点明细
     * 
     * @param id 固定资产盘点明细主键
     * @return 固定资产盘点明细
     */
    public Assets selectAssetsById(Long id);

    /**
     * 查询固定资产盘点明细列表
     * 
     * @param assets 固定资产盘点明细
     * @return 固定资产盘点明细集合
     */
    public List<Assets> selectAssetsList(Assets assets);

    /**
     * 新增固定资产盘点明细
     * 
     * @param assets 固定资产盘点明细
     * @return 结果
     */
    public int insertAssets(Assets assets);

    /**
     * 修改固定资产盘点明细
     * 
     * @param assets 固定资产盘点明细
     * @return 结果
     */
    public int updateAssets(Assets assets);

    /**
     * 批量删除固定资产盘点明细
     * 
     * @param ids 需要删除的固定资产盘点明细主键集合
     * @return 结果
     */
    public int deleteAssetsByIds(Long[] ids);

    /**
     * 删除固定资产盘点明细信息
     * 
     * @param id 固定资产盘点明细主键
     * @return 结果
     */
    public int deleteAssetsById(Long id);

    void borrowAssets(AssetsLoans assetsLoans, String consumed);

    void backAssets(Long assetsLoans);
}
