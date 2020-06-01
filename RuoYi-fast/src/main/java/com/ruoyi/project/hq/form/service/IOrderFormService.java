package com.ruoyi.project.hq.form.service;

import java.util.List;
import com.ruoyi.project.hq.form.domain.OrderForm;

/**
 * 瀚淇订单Service接口
 * 
 * @author lty
 * @date 2020-03-24
 */
public interface IOrderFormService 
{
    /**
     * 查询瀚淇订单
     * 
     * @param id 瀚淇订单ID
     * @return 瀚淇订单
     */
    public OrderForm selectOrderFormById(String id);

    /**
     * 查询瀚淇订单列表
     * 
     * @param orderForm 瀚淇订单
     * @return 瀚淇订单集合
     */
    public List<OrderForm> selectOrderFormList(OrderForm orderForm);

    /**
     * 新增瀚淇订单
     * 
     * @param orderForm 瀚淇订单
     * @return 结果
     */
    public String insertOrderForm(OrderForm orderForm);

    /**
     * 修改瀚淇订单
     * 
     * @param orderForm 瀚淇订单
     * @return 结果
     */
    public int updateOrderForm(OrderForm orderForm);

    /**
     * 批量删除瀚淇订单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderFormByIds(String ids);

    /**
     * 删除瀚淇订单信息
     * 
     * @param id 瀚淇订单ID
     * @return 结果
     */
    public int deleteOrderFormById(String id);
}
