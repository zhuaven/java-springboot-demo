package com.zhu.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhu.demo.Utils.UserUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user_money")
public class UserMoney implements Serializable {
    private Integer id;

    @NotBlank(message = "userId不能为空")
    private Integer userId;

    @NotBlank(message = "money不能为空")
    private Double money;

    private Integer status;

    private String createBy;

    private Date createDate;
}
