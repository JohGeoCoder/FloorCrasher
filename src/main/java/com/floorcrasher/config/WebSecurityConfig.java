package com.floorcrasher.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// http://spring.io/blog/2013/07/03/spring-security-java-config-preview-web-security/

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
//				.antMatchers("/", "/homepage").permitAll()
//				.antMatchers("/admin/**").access("hasRole('ADMIN')")
//				.anyRequest().hasRole("USER")
				.anyRequest().permitAll()
			.and()
				.formLogin()	
					.loginPage("/fc_login")
					.defaultSuccessUrl("/homepage")
					.failureUrl("/fc_login?error")
					.usernameParameter("username")
					.passwordParameter("password")
					.permitAll()
			.and()
				.logout().permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/403")
			.and()
				.csrf();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth
			.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery(
				"select username, passhash, enabled from user where username= ?")
			.authoritiesByUsernameQuery(
				"select username, role from role where username = ?");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**"); // #3
	}
}
