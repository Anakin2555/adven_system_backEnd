//package com.example.mybatisplus.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig extends WebMvcConfigurerAdapter {
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("adven后端的API接口文档")
//                .description("")
//                .version("1.0.0")
//                .build();
//    }
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.mybatisplus.web.controller")) //这里写的是API接口所在的包位置
//                .paths(PathSelectors.any())
//                .build();
//    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//
//}
