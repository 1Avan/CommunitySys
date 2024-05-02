//package com.ivan.communitymanagementsys.config;
//
//import com.ivan.communitymanagementsys.interceptor.JwtTokenUserInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Autowired
//    private JwtTokenUserInterceptor jwtTokenUserInterceptor;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtTokenUserInterceptor).addPathPatterns("/cms/user/**")//拦截路径名
//                .excludePathPatterns("/cms/user/login");//不拦截登录请求
//    }
//
//}
