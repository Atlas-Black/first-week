package com.yiguan.firstweek.config;

import com.yiguan.firstweek.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//告诉Spring，哪些接口需要上锁，哪些接口可以直接访问
@Configuration
//自定义MVC的配置
public class WebConfig implements WebMvcConfigurer {

    @Override
    //专门注册拦截器的方法
    public void addInterceptors(InterceptorRegistry registry) {
        //把LoginInterceptor加入MVC的请求处理链里
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/device/**")
                .excludePathPatterns("/user/login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                //是电脑本地磁盘目录里的文件
                .addResourceLocations("file:/Users/atlasblack/Desktop/Study-personal/实习/campus-images");
    }
}