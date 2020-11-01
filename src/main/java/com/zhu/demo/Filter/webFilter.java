package com.zhu.demo.Filter;

import com.zhu.demo.Utils.RedisUtil;
import com.zhu.demo.dto.UserDto;
import com.zhu.demo.entity.User;
import com.zhu.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class webFilter implements Filter {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("================过滤器初始化 init======================");
    }

    private void setHeader(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        try {

            log.info("================进入过滤器,并设置 Header ======================");
            setHeader(httpRequest, httpResponse);
            log.info("================配置不需要校验接口=====================");
            checkUrl(httpRequest);

        }catch (ServletException e){
            log.error("================ ServletException ======================");
            httpResponse.sendError(505, e.getMessage());
            throw new ServletException(e.getMessage());
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    //无需登录接口
    private List<String> urlList(){
        List<String> urlList = new ArrayList<>();
        urlList.add("/test/doGet");
        urlList.add("/user/login");
        urlList.add("/user/register");
        return urlList;
    }

    //校验URL
    private Boolean checkUrl(HttpServletRequest httpRequest) throws ServletException {
        List<String> list = urlList();
        int index = Collections.binarySearch(list, httpRequest.getRequestURI());
        //无需登录url
        if(index >= 0){
            return true;
        }
        //校验token
        this.checkToken(httpRequest.getHeader("token"));
        return true;
    }

    //校验token
    @Transactional(rollbackFor = ServletException.class)
    private void checkToken(String token) throws ServletException {
        if(StringUtils.isEmpty(token)){
            throw new ServletException("需传Token");
        }
        String username = redisUtil.get(token);
        if(StringUtils.isEmpty(username)){
            throw new ServletException("Token不正确");
        }
        User user = userService.getUserInfo(username);
        UserDto userDto = new UserDto();
        userDto.setToken(token);
        BeanUtils.copyProperties(user, userDto);
        userService.updateUserUtil(userDto);
    }

    @Override
    public void destroy() {
        log.info("================过滤器结束======================");
    }
}
