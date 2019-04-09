package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";    //前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0;       //优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;      //是否执行此过滤器
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("Zuul过滤器执行了~~");

        //先获取到header  如果有token 直接转发
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String authorization = request.getHeader("Authorization");
        if(authorization!=null){
            currentContext.addZuulRequestHeader("Authorization",authorization);
        }
        return null;
    }
}
