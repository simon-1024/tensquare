package com.tensquare.mananger.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        System.out.println("Zuul过滤器~");
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest servletRequest = currentContext.getRequest();

        //获取访问路径
        String url = servletRequest.getRequestURI();
        if(servletRequest.getMethod().equals("OPTIONS")){   //内部分发请求，第一次对网关请求
            return null ;
        }
        if(url.indexOf("/admin/login")>0){
            System.out.println("登录页面"+url);
            return null;
        }

        //获取头部信息
        String header = servletRequest.getHeader("Authorization");
        if(header!=null&&header.startsWith("Bearer ")){
            String token = header.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if(claims!=null&&!"".equals(claims)){
                if(claims.get("roles").equals("admin")){
                    currentContext.addZuulRequestHeader("Authorization",header);
                    System.out.println("验证通过~~");
                    return null ;
                }
            }
        }

        currentContext.setSendZuulResponse(false); //终止运行
        currentContext.setResponseStatusCode(401); //http状态码
        currentContext.setResponseBody("无权访问");  //中文字符，要设置编码
        currentContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
}
