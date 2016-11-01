package org.jasonxiao.config;

import org.jasonxiao.security.TokenAuthenticationFilter;
import org.jasonxiao.security.TokenAuthenticationProcessingFilter;
import org.jasonxiao.security.TokenAuthenticationService;
import org.jasonxiao.security.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Jason Xiao
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final TokenHandler tokenHandler;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, TokenHandler tokenHandler) {
        this.userDetailsService = userDetailsService;
        this.tokenHandler = tokenHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() throws Exception {
        return new TokenAuthenticationService(tokenHandler, authenticationManager());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()

                //allow static assets
                .antMatchers("/dist/**").permitAll()

                //allow anonymous GETs to index, login
                .antMatchers(HttpMethod.GET, "/", "/api/current", "/index.html", "/favicon.ico").permitAll()

                //allow anonymous POSTs to authentication
                .antMatchers(HttpMethod.POST, "/api/auth").permitAll()

                //authenticate all others
                .anyRequest().authenticated().and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
                .addFilterBefore(new TokenAuthenticationProcessingFilter("/api/auth", tokenAuthenticationService()), UsernamePasswordAuthenticationFilter.class)

                // custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new TokenAuthenticationFilter(tokenAuthenticationService()), UsernamePasswordAuthenticationFilter.class);
    }
}
