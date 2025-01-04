package com.cj.project.assets.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cj.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cj.project.assets.mapper.ShopMapper;
import com.cj.project.assets.domain.Shop;
import com.cj.project.assets.service.IShopService;

/**
 * 店铺详情统计Service业务层处理
 * 
 * @author cc
 * @date 2025-01-01
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper,Shop> implements IShopService
{
    @Autowired
    private ShopMapper shopMapper;

    /**
     * 查询店铺详情统计
     * 
     * @param id 店铺详情统计主键
     * @return 店铺详情统计
     */
    @Override
    public Shop selectShopById(Long id)
    {
        return shopMapper.selectShopById(id);
    }

    /**
     * 查询店铺详情统计列表
     * 
     * @param shop 店铺详情统计
     * @return 店铺详情统计
     */
    @Override
    public List<Shop> selectShopList(Shop shop)
    {
        return shopMapper.selectShopList(shop);
    }

    /**
     * 新增店铺详情统计
     * 
     * @param shop 店铺详情统计
     * @return 结果
     */
    @Override
    public int insertShop(Shop shop)
    {
        shop.setCreateTime(DateUtils.getNowDate());
        return shopMapper.insertShop(shop);
    }

    /**
     * 修改店铺详情统计
     * 
     * @param shop 店铺详情统计
     * @return 结果
     */
    @Override
    public int updateShop(Shop shop)
    {
        shop.setUpdateTime(DateUtils.getNowDate());
        return shopMapper.updateShop(shop);
    }

    /**
     * 批量删除店铺详情统计
     * 
     * @param ids 需要删除的店铺详情统计主键
     * @return 结果
     */
    @Override
    public int deleteShopByIds(Long[] ids)
    {
        return shopMapper.deleteShopByIds(ids);
    }

    /**
     * 删除店铺详情统计信息
     * 
     * @param id 店铺详情统计主键
     * @return 结果
     */
    @Override
    public int deleteShopById(Long id)
    {
        return shopMapper.deleteShopById(id);
    }
}
