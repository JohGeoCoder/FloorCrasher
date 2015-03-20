package com.floorcrasher.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.floorcrasher.interceptor.BaseInterceptor;


@Configuration
@EnableWebMvc
// declare where to find annotated controllers
@ComponentScan({"com.floorcrasher"})
@MapperScan("com.floorcrasher.mappers")
@EnableTransactionManagement
public class ServletConfig extends WebMvcAutoConfigurationAdapter{
	
	@Bean
    MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
    
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
    
	@Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        return source;
    }
}
