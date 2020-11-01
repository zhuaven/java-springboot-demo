package com.zhu.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhu.demo.dto.ProductDto;
import com.zhu.demo.entity.Product;
import com.zhu.demo.entity.User;

import java.util.List;

public interface ProductService {
    IPage<Product> getAll(ProductDto productDto);

    void add(Product product) throws Exception;

    Product getProductInfo(int pid) throws Exception;

    void productReduce(int pid, int number) throws Exception;
}
