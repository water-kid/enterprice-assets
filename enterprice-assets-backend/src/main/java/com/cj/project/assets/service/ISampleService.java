package com.cj.project.assets.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cj.project.assets.domain.Sample;

/**
 * 样品库存管理Service接口
 * 
 * @author cc
 * @date 2025-01-01
 */
public interface ISampleService extends IService<Sample>
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
     * 批量删除样品库存管理
     * 
     * @param ids 需要删除的样品库存管理主键集合
     * @return 结果
     */
    public int deleteSampleByIds(Long[] ids);

    /**
     * 删除样品库存管理信息
     * 
     * @param id 样品库存管理主键
     * @return 结果
     */
    public int deleteSampleById(Long id);
}
