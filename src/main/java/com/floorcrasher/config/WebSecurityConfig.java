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
import com.floorcrasher.role.Role;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// http://spring.io/blog/2013/07/03/spring-security-java-config-preview-web-security/

	@Autowired
	DataSource dataSource;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		http
			.authorizeRequests()
				.antMatchers("/", "/registration/**").permitAll()
                .antMatchers("/homepage/**", "/conventions/**").hasRole(Role.USER.getId())
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
				.logout()
					.logoutUrl("/logout")
					.permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/registration");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		
		auth
			.jdbcAuthentication().dataSource(dataSource)
			.passwordEncoder(this.passwordEncoder)
			.usersByUsernameQuery(
				"select username, passhash, enabled from user where username= ?")
			.authoritiesByUsernameQuery(
				"select username, role from role where username = ?");
		
		System.out.println("cheese");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new CrackStationPasswordEncoder();
		return encoder;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**", "/css/**", "/fonts/**"); // #3
	}
}
