package com.ruoyi.project.hq.data.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.hq.data.mapper.FormDataMapper;
import com.ruoyi.project.hq.data.domain.FormData;
import com.ruoyi.project.hq.data.service.IFormDataService;
import com.ruoyi.util.id.UUIDUtil;
import com.ruoyi.common.utils.text.Convert;

/**
 * 瀚淇订单数据Service业务层处理
 * 
 * @author lty
 * @date 2020-03-28
 */
@Service
public class FormDataServiceImpl implements IFormDataService 
{
    @Autowired
    private FormDataMapper formDataMapper;

    /**
     * 查询瀚淇订单数据
     * 
     * @param id 瀚淇订单数据ID
     * @return 瀚淇订单数据
     */
    @Override
    public FormData selectFormDataById(String id)
    {
        return formDataMapper.selectFormDataById(id);
    }

    /**
     * 查询瀚淇订单数据列表
     * 
     * @param formData 瀚淇订单数据
     * @return 瀚淇订单数据
     */
    @Override
    public List<FormData> selectFormDataList(FormData formData)
    {
        return formDataMapper.selectFormDataList(formData);
    }

    /**
     * 新增瀚淇订单数据
     * 
     * @param formData 瀚淇订单数据
     * @return 结果
     */
    @Override
    public int insertFormData(FormData formData)
    {
    	if(formData.getId()==null) {
    		formData.setId(UUIDUtil.getId());
    	}
        return formDataMapper.insertFormData(formData);
    }
    
    

    /**
     * 修改瀚淇订单数据
     * 
     * @param formData 瀚淇订单数据
     * @return 结果
     */
    @Override
    public int updateFormData(FormData formData)
    {
        return formDataMapper.updateFormData(formData);
    }

    /**
     * 删除瀚淇订单数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFormDataByIds(String ids)
    {
        return formDataMapper.deleteFormDataByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除瀚淇订单数据信息
     * 
     * @param id 瀚淇订单数据ID
     * @return 结果
     */
    @Override
    public int deleteFormDataById(String id)
    {
        return formDataMapper.deleteFormDataById(id);
    }
}
