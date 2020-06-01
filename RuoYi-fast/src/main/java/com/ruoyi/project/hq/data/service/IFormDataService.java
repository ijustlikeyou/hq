package com.ruoyi.project.hq.data.service;

import java.util.List;
import com.ruoyi.project.hq.data.domain.FormData;

/**
 * 瀚淇订单数据Service接口
 * 
 * @author lty
 * @date 2020-03-28
 */
public interface IFormDataService 
{
    /**
     * 查询瀚淇订单数据
     * 
     * @param id 瀚淇订单数据ID
     * @return 瀚淇订单数据
     */
    public FormData selectFormDataById(String id);

    /**
     * 查询瀚淇订单数据列表
     * 
     * @param formData 瀚淇订单数据
     * @return 瀚淇订单数据集合
     */
    public List<FormData> selectFormDataList(FormData formData);

    /**
     * 新增瀚淇订单数据
     * 
     * @param formData 瀚淇订单数据
     * @return 结果
     */
    public int insertFormData(FormData formData);
   

    /**
     * 修改瀚淇订单数据
     * 
     * @param formData 瀚淇订单数据
     * @return 结果
     */
    public int updateFormData(FormData formData);

    /**
     * 批量删除瀚淇订单数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFormDataByIds(String ids);

    /**
     * 删除瀚淇订单数据信息
     * 
     * @param id 瀚淇订单数据ID
     * @return 结果
     */
    public int deleteFormDataById(String id);
}
