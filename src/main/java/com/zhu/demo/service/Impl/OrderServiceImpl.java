package com.zhu.demo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhu.demo.Utils.UserUtil;
import com.zhu.demo.dao.UserOrderMapper;
import com.zhu.demo.dto.OrderDto;
import com.zhu.demo.entity.UserOrder;
import com.zhu.demo.entity.Product;
import com.zhu.demo.service.OrderService;
import com.zhu.demo.service.ProductService;
import com.zhu.demo.service.UserMoneyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private UserOrderMapper userOrderMapper;

    @Autowired
    private UserMoneyService userMoneyService;

    @Autowired
    private ProductService productService;

    @Override
    public IPage<OrderDto> getAll(OrderDto orderDto) {
        UserOrder userOrder = new UserOrder();
        BeanUtils.copyProperties(orderDto, userOrder);
        QueryWrapper<UserOrder> queryWrapper = new QueryWrapper<>(userOrder);
        Page<UserOrder> orderPage = new Page<>(orderDto.getCurrent(), orderDto.getSize());
        IPage<OrderDto> list = userOrderMapper.list(orderPage, queryWrapper);
        return list;
    }

    @Override
    @Transactional
    public void add(UserOrder userOrder) throws Exception {
        if(StringUtils.isEmpty(userOrder.getProductId())){
            throw new Exception("productId不能为空");
        }
        if(StringUtils.isEmpty(userOrder.getNumber())){
            userOrder.setNumber(1);//默认买一件
        }
        //查詢商品金額
        Product product = productService.getProductInfo(userOrder.getProductId());
        //商品总花额
        Double costAll = userOrder.getNumber() * product.getMoney();

        userOrder.setOrderNo(getNo(001));
        userOrder.setUserId(UserUtil.id);
        userOrder.setMoney(costAll);
        userOrder.setStatus(1);
        userOrder.setCreateBy(UserUtil.name);
        userOrder.setCreateDate(new Date());

        //去库存
        productService.productReduce(product.getId(), userOrder.getNumber());
        //扣除用户的钱
        userMoneyService.costMoney(costAll);

        userOrderMapper.insert(userOrder);
    }

    /**
     * 生成订单号
     * @param type 类型，可选长度
     * @return
     */
    private String getNo(Integer type){
        int ran = (int) Math.random() * 100000;
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String rs = sdf.format(new Date()) + type + ran;
        return rs;
    }
}
