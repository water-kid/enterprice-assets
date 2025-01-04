package com.cj.project.assets.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cj.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cj.project.assets.mapper.SampleMapper;
import com.cj.project.assets.domain.Sample;
import com.cj.project.assets.service.ISampleService;

/**
 * 样品库存管理Service业务层处理
 * 
 * @author cc
 * @date 2025-01-01
 */
@Service
public class SampleServiceImpl extends ServiceImpl<SampleMapper,Sample> implements ISampleService
{
    @Autowired
    private SampleMapper sampleMapper;

    /**
     * 查询样品库存管理
     * 
     * @param id 样品库存管理主键
     * @return 样品库存管理
     */
    @Override
    public Sample selectSampleById(Long id)
    {
        return sampleMapper.selectSampleById(id);
    }

    /**
     * 查询样品库存管理列表
     * 
     * @param sample 样品库存管理
     * @return 样品库存管理
     */
    @Override
    public List<Sample> selectSampleList(Sample sample)
    {
        return sampleMapper.selectSampleList(sample);
    }

    /**
     * 新增样品库存管理
     * 
     * @param sample 样品库存管理
     * @return 结果
     */
    @Override
    public int insertSample(Sample sample)
    {
        sample.setCreateTime(DateUtils.getNowDate());
        return sampleMapper.insertSample(sample);
    }

    /**
     * 修改样品库存管理
     * 
     * @param sample 样品库存管理
     * @return 结果
     */
    @Override
    public int updateSample(Sample sample)
    {
        sample.setUpdateTime(DateUtils.getNowDate());
        return sampleMapper.updateSample(sample);
    }

    /**
     * 批量删除样品库存管理
     * 
     * @param ids 需要删除的样品库存管理主键
     * @return 结果
     */
    @Override
    public int deleteSampleByIds(Long[] ids)
    {
        return sampleMapper.deleteSampleByIds(ids);
    }

    /**
     * 删除样品库存管理信息
     * 
     * @param id 样品库存管理主键
     * @return 结果
     */
    @Override
    public int deleteSampleById(Long id)
    {
        return sampleMapper.deleteSampleById(id);
    }
}
