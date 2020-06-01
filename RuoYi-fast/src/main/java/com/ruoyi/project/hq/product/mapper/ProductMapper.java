package com.ruoyi.project.hq.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.project.hq.product.domain.Product;

/**
 * 瀚淇产品Mapper接口
 * 
 * @author lty
 * @date 2020-03-24
 */
public interface ProductMapper 
{
    /**
     * 查询瀚淇产品
     * 
     * @param id 瀚淇产品ID
     * @return 瀚淇产品
     */
    public Product selectProductById(String id);

    /**
     * 查询瀚淇产品列表
     * 
     * @param product 瀚淇产品
     * @return 瀚淇产品集合
     */
    public List<Product> selectProductList(Product product);
    
    
    /**
     * 根据产品id查询产品对象
     * @param productId
     * @return
     */
    public Product selectProductByProductId(String productId);
    
    /**
     * 根据产品名称查询对象
     * 用来查询药品
     * @param name
     * @return
     */
    public Product selectProductByProductName(String name);

    /**
         * 根据产品型号企业编码名称型号查询
     * @return
     */
    public Product selectProductByProductParameter(@Param("productId")String productId,@Param("productName")String productName,@Param("productModel")String productModel);
    
    /**
     * 新增瀚淇产品
     * 
     * @param product 瀚淇产品
     * @return 结果
     */
    public int insertProduct(Product product);

    /**
     * 修改瀚淇产品
     * 
     * @param product 瀚淇产品
     * @return 结果
     */
    public int updateProduct(Product product);

    /**
     * 删除瀚淇产品
     * 
     * @param id 瀚淇产品ID
     * @return 结果
     */
    public int deleteProductById(String id);

    /**
     * 批量删除瀚淇产品
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProductByIds(String[] ids);
}
