package com.zhu.demo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
@TableName("order")
public class OrderDto {

    private String orderNo;

    private Integer userId;

    @JsonProperty("username")
    private String username;

    private Integer productId;

    @JsonProperty("productName")
    private String productName;

    private Double money;

    private String remark;

    private Integer status;

    private String createBy;

    private Date createDate;

    private Long current = 1L;

    private Long size = 8L;
}
