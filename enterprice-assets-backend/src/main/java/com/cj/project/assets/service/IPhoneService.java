package com.cj.project.assets.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cj.project.assets.domain.Phone;

/**
 * 手机号管理与使用Service接口
 * 
 * @author c c
 * @date 2025-01-01
 */
public interface IPhoneService extends IService<Phone>
{
    /**
     * 查询手机号管理与使用
     * 
     * @param id 手机号管理与使用主键
     * @return 手机号管理与使用
     */
    public Phone selectPhoneById(Long id);

    /**
     * 查询手机号管理与使用列表
     * 
     * @param phone 手机号管理与使用
     * @return 手机号管理与使用集合
     */
    public List<Phone> selectPhoneList(Phone phone);

    /**
     * 新增手机号管理与使用
     * 
     * @param phone 手机号管理与使用
     * @return 结果
     */
    public int insertPhone(Phone phone);

    /**
     * 修改手机号管理与使用
     * 
     * @param phone 手机号管理与使用
     * @return 结果
     */
    public int updatePhone(Phone phone);

    /**
     * 批量删除手机号管理与使用
     * 
     * @param ids 需要删除的手机号管理与使用主键集合
     * @return 结果
     */
    public int deletePhoneByIds(Long[] ids);

    /**
     * 删除手机号管理与使用信息
     * 
     * @param id 手机号管理与使用主键
     * @return 结果
     */
    public int deletePhoneById(Long id);
}
