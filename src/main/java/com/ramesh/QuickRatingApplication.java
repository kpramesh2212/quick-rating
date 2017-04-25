package com.ramesh;

import com.ramesh.domain.Account;
import com.ramesh.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders
        .AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration
        .WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.inject.Inject;

@SpringBootApplication
@EnableWebSecurity
@ImportResource(value = "classpath:hsql_cfg.xml")
public class QuickRatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickRatingApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			public void addCorsMappings(CorsRegistry corsRegistry) {
				corsRegistry.addMapping("/**").allowedOrigins("*")
						.allowedMethods("POST", "GET", "DELETE", "PUT");
			}
		};
	}

	@Bean
	public WebSecurityConfigurer securityConfigurer() {
	    return new WebSecurityConfigurerAdapter() {
            @Inject
            AccountRepository accountRepository;

            @Autowired
            public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(email -> {
                    Account account = accountRepository.findByEmail(email);
                    if (account != null) {
                        return new User(account.getEmail(),
                                account.getPassword(),
                                true,
                                true,
                                true,
                                true,
                                AuthorityUtils.createAuthorityList("USER"));
                    }
                    throw new UsernameNotFoundException("Invalid username or password");
                });
            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic()
                        .and().csrf().disable();
            }

        };
    }

}
