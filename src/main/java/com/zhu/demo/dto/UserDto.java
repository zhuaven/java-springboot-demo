package com.zhu.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserDto {
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String name;
    @NotBlank(message = "密码不能为空")
    private String password;

    private String token;

    private String email;

    private String status;

    private String createBy;

    private Date createDate;

    private Long current = 1L;

    private Long size = 8L;
}
