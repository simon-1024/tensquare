package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //无论如何都要放行，具体能不能操作，要在具体的操作中判断
        //拦截器只是负责把请求头中有包含token令牌进行解析验证
        System.out.println("经过了拦截器~~");
        String header = request.getHeader("Authorization");
        System.out.println("heder = "+header);
        if(header!=null &&header.startsWith("Bearer ")){
            String token = header.substring(7);
            try {
                Claims claims = jwtUtil.parseJWT(token);
                if(claims!=null){
                    String role = (String) claims.get("roles");
                    if ("admin".equals(role)){
                        request.setAttribute("admin_claims",claims);
                    }
                    if("user".equals(role)){
                        request.setAttribute("user_claims",claims);
                    }
                }
            }catch (Exception e){
                throw new RuntimeException("令牌有误");
            }
        }
        return true;
    }
}
