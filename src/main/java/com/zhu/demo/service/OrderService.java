package com.zhu.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhu.demo.dto.OrderDto;
import com.zhu.demo.entity.UserOrder;

public interface OrderService {
    IPage<OrderDto> getAll(OrderDto orderDto);

    void add(UserOrder userOrder) throws Exception;
}
