package com.previred.springbootinit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.previred.ext.jwt.JWTAuthorizationFilter;


@SpringBootApplication
@ComponentScan({"com.previred.controllers","com.previred.services"})
@EntityScan("com.previred.entities")
@EnableJpaRepositories("com.previred.repositories")
public class PreviredSpringBoot {
	
	private static final String[] AUTH_WHITELIST = {
	       
	        "/swagger-resources",
	        "/swagger-resources/**",
	        "/swagger-ui.html",
	        "/generateJWT",
	        "/api-docs/**",
	        "/swagger-ui/**"
	};

	public static void main(String[] args) {
		SpringApplication.run(PreviredSpringBoot.class, args);
	}
	
    @Bean
    SecurityFilterChain webSecurityConfigSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(requests -> requests
                        .antMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }
    
	

}
