package com.zhu.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhu.demo.Utils.UserUtil;
import com.zhu.demo.dao.ProductMapper;
import com.zhu.demo.dto.ProductDto;
import com.zhu.demo.entity.Product;
import com.zhu.demo.entity.UserMoney;
import com.zhu.demo.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    @Transactional
    public IPage<Product> getAll(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>(product);
        Page<Product> productDtoPage = new Page<>(productDto.getCurrent(), productDto.getSize());

        IPage<Product> list = productMapper.selectPage(productDtoPage, queryWrapper);
        return list;
    }

    @Override
    public Product getProductInfo(int pid) throws Exception {
        Product product = productMapper.selectById(pid);
        if(product == null){
            throw new Exception("productId有误");
        }
        return product;
    }

    //下单后，减少库存
    @Override
    public void productReduce(int pid, int number) throws Exception {
        Product product = productMapper.selectById(pid);
        int newNumber = 0;
        if(product.getNumber() >= number){
            newNumber = product.getNumber() - number;
        }else{
            throw new Exception("产品库存不够");
        }
        if(product == null){
            throw new Exception("productId有误");
        }
        product.setNumber(newNumber);
        productMapper.updateById(product);
    }

    @Override
    public void add(@Valid Product product) throws Exception {
        if(StringUtils.isEmpty(product.getType())){
            product.setType(1);
        }
        if(StringUtils.isEmpty(product.getName())){
            throw new Exception("产品名称不能为空");
        }
        if(StringUtils.isEmpty(product.getMoney())){
            throw new Exception("产品金额不能为空");
        }
        if(StringUtils.isEmpty(product.getNumber())){
            throw new Exception("产品数量不能为空");
        }
        product.setStatus(1);
        product.setCreateBy(UserUtil.name);
        product.setCreateDate(new Date());

        productMapper.insert(product);
    }
}
