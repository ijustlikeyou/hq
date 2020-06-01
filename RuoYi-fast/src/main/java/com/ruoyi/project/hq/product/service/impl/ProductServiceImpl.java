package com.ruoyi.project.hq.product.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.hq.product.mapper.ProductMapper;
import com.ruoyi.project.hq.product.domain.Product;
import com.ruoyi.project.hq.product.service.IProductService;
import com.ruoyi.util.id.UUIDUtil;
import com.ruoyi.common.utils.text.Convert;

/**
 * 瀚淇产品Service业务层处理
 * 
 * @author lty
 * @date 2020-03-24
 */
@Service
public class ProductServiceImpl implements IProductService 
{
    @Autowired
    private ProductMapper productMapper;

    /**
     * 查询瀚淇产品
     * 
     * @param id 瀚淇产品ID
     * @return 瀚淇产品
     */
    @Override
    public Product selectProductById(String id)
    {
        return productMapper.selectProductById(id);
    }

    /**
     * 查询瀚淇产品列表
     * 
     * @param product 瀚淇产品
     * @return 瀚淇产品
     */
    @Override
    public List<Product> selectProductList(Product product)
    {
        return productMapper.selectProductList(product);
    }

    /**
     * 新增瀚淇产品
     * 
     * @param product 瀚淇产品
     * @return 结果
     */
    @Override
    public int insertProduct(Product product)
    {
    	if(product.getId()==null||product.getId().trim().length()==0) {
    		product.setId(UUIDUtil.getId());
    	}
        return productMapper.insertProduct(product);
    }

    /**
     * 修改瀚淇产品
     * 
     * @param product 瀚淇产品
     * @return 结果
     */
    @Override
    public int updateProduct(Product product)
    {
        return productMapper.updateProduct(product);
    }

    /**
     * 删除瀚淇产品对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProductByIds(String ids)
    {
        return productMapper.deleteProductByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除瀚淇产品信息
     * 
     * @param id 瀚淇产品ID
     * @return 结果
     */
    @Override
    public int deleteProductById(String id)
    {
        return productMapper.deleteProductById(id);
    }

	@Override
	public Product selectProductByProductId(String productId) {
	    if(productId==null||productId.length()==0) {
	    	return null;
	    }
		return productMapper.selectProductByProductId(productId);
	}

	@Override
	public Product selectProductByProductName(String name) {
		if(name==null||name.length()==0) {
			return null;
		}
		return productMapper.selectProductByProductName(name);
	}

	@Override
	public Product selectProductByProductParameter(String productId, String productName, String productModel) {
		if((productId==null&&productName==null&&productModel==null)||(productId==null&&productModel==null)) {
			return null;
		}
		if(productId!=null&&productId.length()>0) {
			productName=null;
		}
		return productMapper.selectProductByProductParameter(productId,productName,productModel);
	}
}
