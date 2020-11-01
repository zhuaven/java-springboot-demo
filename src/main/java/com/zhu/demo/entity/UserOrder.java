package com.zhu.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user_order")
public class UserOrder implements Serializable {
    private Integer id;

    @NotBlank(message = "orderNo不能为空")
    private String orderNo;

    @NotBlank(message = "userId不能为空")
    private Integer userId;

    @NotBlank(message = "productId不能为空")
    private Integer productId;

    private Integer number = 1;

    @NotBlank(message = "money不能为空")
    private Double money;

    private String remark = null;

    private Integer status;

    private String createBy;

    private Date createDate;
}
