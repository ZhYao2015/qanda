package com.zyao.qanda.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.zyao.qanda.interceptor.LoginRequiredInterceptor;
import com.zyao.qanda.interceptor.PassportInterceptor;

@Component
public class QandaConfiguration extends WebMvcConfigurationSupport {
    
	@Autowired
	PassportInterceptor passportInterceptor;
	
	@Autowired
	LoginRequiredInterceptor LoginRequiredInterceptor; 
	
	/**
     * SpringBoot2 必须重写该方法，否则静态资源无法访问
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        super.addResourceHandlers(registry);
    }

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		//这三行的顺序。。。？
		registry.addInterceptor(passportInterceptor);
		registry.addInterceptor(LoginRequiredInterceptor).addPathPatterns("/user/**");
		super.addInterceptors(registry);
		
	}
    
    
    
    
	
}