package com.example.demo.configuration;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.demo.interceptor.TokenInterceptor;

@Configuration
public class WebmvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/fonts/")
				.setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS));
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images/")
				.setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS));
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/")
				.setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS));
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/")
				.setCacheControl(CacheControl.maxAge(1, TimeUnit.DAYS));
		registry.addResourceHandler("/**").addResourceLocations("classpath:/WEB-INF/views/");
		super.addResourceHandlers(registry);
	}

	@Bean
	public HttpMessageConverters customConverters() {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(
				Charset.forName("UTF-8"));
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		List<MediaType> types = new LinkedList<>();
		types.add(MediaType.ALL);
		converter.setSupportedMediaTypes(types);
		// converter.setDefaultCharset(Charset.forName("UTF-8"));
		return new HttpMessageConverters(converter, stringHttpMessageConverter);
	}

	@Override
	public void addInterceptors(InterceptorRegistry  registry) {
		System.err.println("注册拦截器");
		registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
	}

}
