package com.cj.project.assets.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cj.common.exception.ServiceException;
import com.cj.common.utils.DateUtils;
import com.cj.project.assets.domain.AssetsLoans;
import com.cj.project.assets.domain.enums.BorrowStatus;
import com.cj.project.assets.service.AssetsLoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cj.project.assets.mapper.AssetsMapper;
import com.cj.project.assets.domain.Assets;
import com.cj.project.assets.service.IAssetsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 固定资产盘点明细Service业务层处理
 * 
 * @author cc
 * @date 2025-01-01
 */
@Service
public class AssetsServiceImpl extends ServiceImpl<AssetsMapper,Assets> implements IAssetsService
{
    @Autowired
    private AssetsMapper assetsMapper;

    /**
     * 查询固定资产盘点明细
     * 
     * @param id 固定资产盘点明细主键
     * @return 固定资产盘点明细
     */
    @Override
    public Assets selectAssetsById(Long id)
    {
        return assetsMapper.selectAssetsById(id);
    }

    /**
     * 查询固定资产盘点明细列表
     * 
     * @param assets 固定资产盘点明细
     * @return 固定资产盘点明细
     */
    @Override
    public List<Assets> selectAssetsList(Assets assets)
    {
        return assetsMapper.selectAssetsList(assets);
    }

    /**
     * 新增固定资产盘点明细
     * 
     * @param assets 固定资产盘点明细
     * @return 结果
     */
    @Override
    public int insertAssets(Assets assets)
    {
        assets.setCreateTime(DateUtils.getNowDate());
        return assetsMapper.insertAssets(assets);
    }

    /**
     * 修改固定资产盘点明细
     * 
     * @param assets 固定资产盘点明细
     * @return 结果
     */
    @Override
    public int updateAssets(Assets assets)
    {
        assets.setUpdateTime(DateUtils.getNowDate());
        return assetsMapper.updateAssets(assets);
    }

    /**
     * 批量删除固定资产盘点明细
     * 
     * @param ids 需要删除的固定资产盘点明细主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAssetsByIds(Long[] ids)
    {
        // 删除资产
        // 检测有没有外借的，，有外借的，不允许删除
        for (Long id : ids) {
            long count = assetsLoansService.count(new LambdaQueryWrapper<AssetsLoans>().eq(AssetsLoans::getAssetsId, id));
//            Assets assets = assetsLoansService.get(id);
            if (count > 0){
                throw new ServiceException("有外借信息，不允许删除");
            }
        }
        return assetsMapper.deleteAssetsByIds(ids);
    }

    /**
     * 删除固定资产盘点明细信息
     * 
     * @param id 固定资产盘点明细主键
     * @return 结果
     */
    @Override
    public int deleteAssetsById(Long id)
    {
        return assetsMapper.deleteAssetsById(id);
    }


    @Autowired
    AssetsLoansService assetsLoansService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void borrowAssets(AssetsLoans assetsLoans, String type) {
        Integer assetsId = assetsLoans.getAssetsId();
        Assets assets = this.getById(assetsId);

        if (assets == null){
            throw new ServiceException("资产不存在");
        }

        Integer borrowCount = assetsLoans.getBorrowCount();

        Integer remainingCount = assets.getRemainingCount();

        if (remainingCount < borrowCount){
            throw new ServiceException("资产数量不足");
        }

        assets.setRemainingCount(remainingCount - borrowCount);

        this.updateById(assets);


        assetsLoans.setBorrowDate(new Date());
        assetsLoans.setStatus(type);

        assetsLoansService.save(assetsLoans);


    }


    /**
     * 归还assets
     * @param assetsLoans
     */
    @Override
    public synchronized void backAssets(Long id) {



        AssetsLoans dbAssetsLoans = assetsLoansService.getById(id);
        if (dbAssetsLoans.getStatus().equals(BorrowStatus.RETURNING)){
            throw new ServiceException("资产已经归还");
        }

        if (dbAssetsLoans.getStatus().equals(BorrowStatus.CONSUMED)){
            throw new ServiceException("资产已经消耗");
        }

        Integer assetsId = dbAssetsLoans.getAssetsId();

        Assets assets = this.getById(assetsId);
        if (assets == null){
            throw new ServiceException("资产不存在");
        }

        // 归还，，，
        dbAssetsLoans.setStatus(BorrowStatus.RETURNING);
        Integer borrowCount = dbAssetsLoans.getBorrowCount();
        dbAssetsLoans.setReturnDate(new Date());

        assetsLoansService.updateById(dbAssetsLoans);


        //

        Integer remainingCount = assets.getRemainingCount();
        assets.setRemainingCount(remainingCount + borrowCount);

        this.updateById(assets);


    }
}
