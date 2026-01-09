package com.cro.resource_monitoring_service.config;

import com.cro.resource_monitoring_service.interceptor.RequestHeaderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestHeaderInterceptor interceptor;

    public WebConfig(RequestHeaderInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns(
                "/resource/health",
                "/actuator/**"
        );
    }
}
