package com.floorcrasher.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.floorcrasher.interceptor.BaseInterceptor;


@Configuration
@EnableWebMvc
// declare where to find annotated controllers
@ComponentScan({"com.floorcrasher"})
@MapperScan("com.floorcrasher.mappers")
@EnableTransactionManagement
public class ServletConfig extends WebMvcConfigurerAdapter{
	
	@Bean
    MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
	
//    @Bean
//    ViewResolver internalViewResolver() {
//        // the view resolver bean ...
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/static/WEB-INF/jsp/");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    } 
    
    /**
     * Database Config
     * @return
     */
    @Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	    driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/floorcrasher?autoReconnect=true&amp;");
	    driverManagerDataSource.setUsername("root");
	    driverManagerDataSource.setPassword("password");
	    return driverManagerDataSource;
	}
    
    /**
     * Database Config
     * @return
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
    	DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }
    
    /**
     * Mybatis SQL session factory
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
      SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
      sqlSessionFactory.setDataSource(dataSource());
      return (SqlSessionFactory) sqlSessionFactory.getObject();
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new BaseInterceptor());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/static/css/**").addResourceLocations("/static/css/");
//		registry.addResourceHandler("/static/fonts/**").addResourceLocations("/static/fonts/");
//		registry.addResourceHandler("/static/js/**").addResourceLocations("/static/js/");
		registry.addResourceHandler("/resources/static/css/**").addResourceLocations("/resources/static/css/**");
		registry.addResourceHandler("/resources/static/fonts/**").addResourceLocations("/resources/static/fonts/**");
		registry.addResourceHandler("/resources/static/js/**").addResourceLocations("/resources/static/js/**");
		
	}
    
	@Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        return source;
    }
}
