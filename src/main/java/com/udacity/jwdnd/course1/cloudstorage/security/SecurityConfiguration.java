package com.udacity.jwdnd.course1.cloudstorage.security;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Defining Custom Security configurations to restrict unauthorized users from accessing pages other than the
 * login and signup pages
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    AuthenticationService authenticationService;

    public SecurityConfiguration(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    /**
     * Configuring the Authentication
     * @param auth Authentication Manager Builder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    /**
     * Configuring the Authorization
     * @param http Http Security
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/signup",
                        "/css/**",
                        "/js/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.formLogin().loginPage("/login").permitAll().failureUrl("/login?error=true");
        http.formLogin().defaultSuccessUrl("/home", true); // redirect to home when logged in
        http.logout().logoutUrl("/logout");
    }
}
