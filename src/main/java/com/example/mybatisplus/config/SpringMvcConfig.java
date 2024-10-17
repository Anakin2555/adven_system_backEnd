package com.example.mybatisplus.config;


import com.example.mybatisplus.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Value("${file-upload-path}")
    private String path;
    @Value("${}")

    @Bean
    LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }


    /**
     * @Function: 配置生成器：添加一个拦截器
     * @author: zyc
     * @date: 2022/3/2
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){

        List<String> excludePatterns = new ArrayList<>();
        excludePatterns.add("/api/activity/getAllActivity");
        excludePatterns.add("/api/activity/getOne");
        excludePatterns.add("/file/**");
        excludePatterns.add("/api/verify/**");



        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePatterns);
    }


    /**
     * 添加静态资源访问
     *
     *
     * @param registry
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:" + path).addResourceLocations("classpath:/static/");
    }
}
