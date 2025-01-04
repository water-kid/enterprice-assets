package com.cj.project.assets.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.project.assets.domain.Sample;

/**
 * 样品库存管理Mapper接口
 * 
 * @author cc
 * @date 2025-01-01
 */
public interface SampleMapper extends BaseMapper<Sample>
{
    /**
     * 查询样品库存管理
     * 
     * @param id 样品库存管理主键
     * @return 样品库存管理
     */
    public Sample selectSampleById(Long id);

    /**
     * 查询样品库存管理列表
     * 
     * @param sample 样品库存管理
     * @return 样品库存管理集合
     */
    public List<Sample> selectSampleList(Sample sample);

    /**
     * 新增样品库存管理
     * 
     * @param sample 样品库存管理
     * @return 结果
     */
    public int insertSample(Sample sample);

    /**
     * 修改样品库存管理
     * 
     * @param sample 样品库存管理
     * @return 结果
     */
    public int updateSample(Sample sample);

    /**
     * 删除样品库存管理
     * 
     * @param id 样品库存管理主键
     * @return 结果
     */
    public int deleteSampleById(Long id);

    /**
     * 批量删除样品库存管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSampleByIds(Long[] ids);
}
