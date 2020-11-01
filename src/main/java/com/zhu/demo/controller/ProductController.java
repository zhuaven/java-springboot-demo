package com.zhu.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhu.demo.common.ReturnData;
import com.zhu.demo.dto.ProductDto;
import com.zhu.demo.entity.Product;
import com.zhu.demo.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="产品controller",tags={"产品操作接口"})
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "add")
    @ResponseBody
    @ApiOperation("添加商品")
    @ApiImplicitParam("产品参数")
    public ReturnData add(@RequestBody Product product) {
        try {
            productService.add(product);
        }catch (Exception e){
            return new ReturnData("6000002", e.getMessage());
        }

        return new ReturnData();
    }

    @PostMapping(value = "list")
    @ResponseBody
    @ApiOperation("查询产品列表")
    public ReturnData list(ProductDto productDto){
        ReturnData rd = new ReturnData();
        IPage<Product> list = productService.getAll(productDto);
        rd.setData(list);
        return rd;
    }
}
