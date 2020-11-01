package com.zhu.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhu.demo.common.ReturnData;
import com.zhu.demo.dto.OrderDto;
import com.zhu.demo.entity.UserOrder;
import com.zhu.demo.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value="订单controller",tags={"订单操作接口"})
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(value = "list")
    @ResponseBody
    @ApiOperation("订单列表")
    public ReturnData list(OrderDto orderDto){
        ReturnData rd = new ReturnData();
        IPage<OrderDto> userList = orderService.getAll(orderDto);
        rd.setData(userList);
        return rd;
    }

    @RequestMapping(value = "buyProduct", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加订单", notes = "")
    public ReturnData add(@RequestBody UserOrder userOrder) throws Exception {
        ReturnData rd = new ReturnData();
        try {
            orderService.add(userOrder);
        }catch (Exception e){
            rd.setCode("6000005");
            rd.setMsg(e.getMessage());
            log.error(e.getMessage());
        }

        return rd;
    }
}
