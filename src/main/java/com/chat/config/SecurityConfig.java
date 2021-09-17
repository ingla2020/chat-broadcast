package com.chat.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;


import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
//	@Autowired
	//private CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;

    private static final ClearSiteDataHeaderWriter.Directive[] SOURCE =
        {CACHE, COOKIES, STORAGE, EXECUTION_CONTEXTS, ALL};
    
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());
		//auth.inMemoryAuthentication().
        //withUser("leo").password("123").roles("ADMIN");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.csrf().disable();
		//http.authorizeRequests().antMatchers("/rest/**").authenticated().anyRequest().permitAll();
			//.and()
		http.httpBasic()
		//.authenticationEntryPoint(customBasicAuthenticationEntryPoint)
		.and()
			.authorizeRequests()
			.antMatchers("/rest/auth/**").authenticated()
			//.anyRequest().hasAnyRole("ADMIN")
			.antMatchers("/secure/rest/**").authenticated()
			.antMatchers("/app/**").authenticated()
			.antMatchers("/user/rest/*").authenticated()
//			.antMatchers("/serverchat/*").anonymous()
//			.antMatchers("/app/*").anonymous()
			//.antMatchers("/csd/**").anonymous()
			
			.anyRequest().authenticated()
            .and()
            .formLogin()
//            .loginPage("/login")
//            .permitAll()
  
            .and()
            .logout()
            .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE)))
            .clearAuthentication(true)
            .logoutSuccessUrl("/login")
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            /*
            .and()
            .logout(logout -> logout
                .logoutUrl("/csd/logout")
                .addLogoutHandler((request, response, auth) -> {
                    for (Cookie cookie : request.getCookies()) {
                        String cookieName = cookie.getName();
                        Cookie cookieToDelete = new Cookie(cookieName, null);
                        cookieToDelete.setMaxAge(0);
                        response.addCookie(cookieToDelete);
                    }
                    new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE));
                })
            )
*/
        //.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
.and()
			.csrf().disable();
	}

	/*
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/stomp/**");
	}
*/
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	

	
}


