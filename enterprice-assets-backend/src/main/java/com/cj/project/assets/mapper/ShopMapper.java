package com.cj.project.assets.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.project.assets.domain.Shop;

/**
 * 店铺详情统计Mapper接口
 * 
 * @author cc
 * @date 2025-01-01
 */
public interface ShopMapper extends BaseMapper<Shop>
{
    /**
     * 查询店铺详情统计
     * 
     * @param id 店铺详情统计主键
     * @return 店铺详情统计
     */
    public Shop selectShopById(Long id);

    /**
     * 查询店铺详情统计列表
     * 
     * @param shop 店铺详情统计
     * @return 店铺详情统计集合
     */
    public List<Shop> selectShopList(Shop shop);

    /**
     * 新增店铺详情统计
     * 
     * @param shop 店铺详情统计
     * @return 结果
     */
    public int insertShop(Shop shop);

    /**
     * 修改店铺详情统计
     * 
     * @param shop 店铺详情统计
     * @return 结果
     */
    public int updateShop(Shop shop);

    /**
     * 删除店铺详情统计
     * 
     * @param id 店铺详情统计主键
     * @return 结果
     */
    public int deleteShopById(Long id);

    /**
     * 批量删除店铺详情统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopByIds(Long[] ids);
}