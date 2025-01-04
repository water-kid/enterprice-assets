package com.cj.project.assets.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cj.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cj.project.assets.mapper.PhoneMapper;
import com.cj.project.assets.domain.Phone;
import com.cj.project.assets.service.IPhoneService;

/**
 * 手机号管理与使用Service业务层处理
 * 
 * @author c c
 * @date 2025-01-01
 */
@Service
public class PhoneServiceImpl extends ServiceImpl<PhoneMapper,Phone> implements IPhoneService
{
    @Autowired
    private PhoneMapper phoneMapper;

    /**
     * 查询手机号管理与使用
     * 
     * @param id 手机号管理与使用主键
     * @return 手机号管理与使用
     */
    @Override
    public Phone selectPhoneById(Long id)
    {
        return phoneMapper.selectPhoneById(id);
    }

    /**
     * 查询手机号管理与使用列表
     * 
     * @param phone 手机号管理与使用
     * @return 手机号管理与使用
     */
    @Override
    public List<Phone> selectPhoneList(Phone phone)
    {
        return phoneMapper.selectPhoneList(phone);
    }

    /**
     * 新增手机号管理与使用
     * 
     * @param phone 手机号管理与使用
     * @return 结果
     */
    @Override
    public int insertPhone(Phone phone)
    {
        phone.setCreateTime(DateUtils.getNowDate());
        return phoneMapper.insertPhone(phone);
    }

    /**
     * 修改手机号管理与使用
     * 
     * @param phone 手机号管理与使用
     * @return 结果
     */
    @Override
    public int updatePhone(Phone phone)
    {
        phone.setUpdateTime(DateUtils.getNowDate());
        return phoneMapper.updatePhone(phone);
    }

    /**
     * 批量删除手机号管理与使用
     * 
     * @param ids 需要删除的手机号管理与使用主键
     * @return 结果
     */
    @Override
    public int deletePhoneByIds(Long[] ids)
    {
        return phoneMapper.deletePhoneByIds(ids);
    }

    /**
     * 删除手机号管理与使用信息
     * 
     * @param id 手机号管理与使用主键
     * @return 结果
     */
    @Override
    public int deletePhoneById(Long id)
    {
        return phoneMapper.deletePhoneById(id);
    }
}
