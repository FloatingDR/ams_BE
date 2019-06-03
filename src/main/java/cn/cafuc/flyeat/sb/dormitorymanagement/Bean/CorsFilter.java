package cn.cafuc.flyeat.sb.dormitorymanagement.Bean;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*",filterName="CorsFilter")
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("============================Filter初始化=================================");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        String curOrigin = request.getHeader("Origin");
        response.setHeader("Accesss-Control-Allow-Origin",curOrigin==null?"true":curOrigin);
        response.setHeader("Accesss-Control-Allow-Credentials","true");
        response.setHeader("Accesss-Control-Allow-Methods","POST,GET,PATCH,DE:ETE,PUT");
        response.setHeader("Accesss-Control-Allow-Headers","Origin,X-Requested-With,Content-Type,Accept");
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.OK.value());
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("============================Filter销毁=================================");
    }
}
