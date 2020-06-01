package com.ruoyi.project.hq.form.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.hq.form.mapper.OrderFormMapper;
import com.ruoyi.project.hq.form.domain.OrderForm;
import com.ruoyi.project.hq.form.service.IOrderFormService;
import com.ruoyi.util.id.UUIDUtil;
import com.ruoyi.common.utils.text.Convert;

/**
 * 瀚淇订单Service业务层处理
 * 
 * @author lty
 * @date 2020-03-24
 */
@Service
public class OrderFormServiceImpl implements IOrderFormService 
{
    @Autowired
    private OrderFormMapper orderFormMapper;

    /**
     * 查询瀚淇订单
     * 
     * @param id 瀚淇订单ID
     * @return 瀚淇订单
     */
    @Override
    public OrderForm selectOrderFormById(String id)
    {
        return orderFormMapper.selectOrderFormById(id);
    }

    /**
     * 查询瀚淇订单列表
     * 
     * @param orderForm 瀚淇订单
     * @return 瀚淇订单
     */
    @Override
    public List<OrderForm> selectOrderFormList(OrderForm orderForm)
    {
        return orderFormMapper.selectOrderFormList(orderForm);
    }

    /**
     * 新增瀚淇订单
     * 
     * @param orderForm 瀚淇订单
     * @return 结果
     */
    @Override
    public String insertOrderForm(OrderForm orderForm)
    {
    	if(orderForm.getId()==null) {
    		orderForm.setId(UUIDUtil.getId());
    	}
        return orderFormMapper.insertOrderForm(orderForm)==0?null:orderForm.getId();
    }

    /**
     * 修改瀚淇订单
     * 
     * @param orderForm 瀚淇订单
     * @return 结果
     */
    @Override
    public int updateOrderForm(OrderForm orderForm)
    {
        return orderFormMapper.updateOrderForm(orderForm);
    }

    /**
     * 删除瀚淇订单对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrderFormByIds(String ids)
    {
        return orderFormMapper.deleteOrderFormByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除瀚淇订单信息
     * 
     * @param id 瀚淇订单ID
     * @return 结果
     */
    @Override
    public int deleteOrderFormById(String id)
    {
        return orderFormMapper.deleteOrderFormById(id);
    }
}
