package com.zhu.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user")
public class User implements Serializable {

    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @TableField("name")
    private String name;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @TableField("password")
    private String password;

    private String email;

    private Integer status;

    private String createBy;

    private Date createDate;
}

