package com.floorcrasher.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.floorcrasher.common.CrackStationPasswordEncoder;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// http://spring.io/blog/2013/07/03/spring-security-java-config-preview-web-security/

	@Autowired
	DataSource dataSource;
	
	@Autowired
    private FloorCrasherAuthenticationProvider authenticationProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		http
			.authorizeRequests()
				.antMatchers("/", "/registration/**").permitAll()
                .antMatchers("/homepage/**", "/conventions/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
			.and()
				.formLogin()	
					.loginPage("/login")
					.defaultSuccessUrl("/homepage")
					.failureUrl("/login?error")
					.usernameParameter("username")
					.passwordParameter("password")
					.permitAll()
			.and()
				.logout().permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/403");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
//		auth
//			.inMemoryAuthentication()
//				.withUser("John").password("password").roles("USER", "ADMIN");
		
		auth
			.authenticationProvider(authenticationProvider)
			.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery(
				"select username, passhash, enabled from user where username= ?")
			.authoritiesByUsernameQuery(
				"select username, role from role where username = ?");
	}
	
	@Bean
	public FloorCrasherAuthenticationProvider authenticationProvider(){
		return new FloorCrasherAuthenticationProvider();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**", "/css/**", "/fonts/**"); // #3
	}
}
