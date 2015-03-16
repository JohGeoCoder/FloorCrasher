package com.floorcrasher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@EnableWebMvc
// declare where to find annotated controllers
@ComponentScan({"com.floorcrasher"})
public class ServletConfig extends WebMvcConfigurerAdapter{
	@Bean
    MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
    @Bean
    ViewResolver internalViewResolver() {
        // the view resolver bean ...
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
