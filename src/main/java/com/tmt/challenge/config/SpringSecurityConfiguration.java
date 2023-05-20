package com.tmt.challenge.config;

import com.tmt.challenge.service.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration  extends WebSecurityConfigurerAdapter{

    CustomUserDetailsService userDetailsService;

    /**
     * Configure HTTP Security to make use the CustomJwtAuthenticationFiler
     */
    private final CustomJwtAuthenticationFilter customJwtAuthenticationFilter;

    /**
     * Configure HTTP Security to make use the JwtAuthenticationEntryPoint
     */
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    public SpringSecurityConfiguration(
            @Autowired CustomJwtAuthenticationFilter customJwtAuthenticationFilter,
            @Autowired JwtAuthenticationEntryPoint unauthorizedHandler
    ) {
        this.customJwtAuthenticationFilter = customJwtAuthenticationFilter;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Autowired
    public void setUserDetailsService(CustomUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Authentication Manager
     * */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/hello-admin").hasRole("ADMIN")
                .antMatchers("/hello-user").hasAnyRole("ADMIN","USER")
                .antMatchers( "/api/v1/auth/register", "/api/v1/auth/login", "/api/v1/auth/refresh-token", "/getResponse")
                .permitAll()  // set endpoint to be public access
                .anyRequest().authenticated()   // set another request to be endpoint to be public access
                // to use basic auth
                //.and().httpBasic()
                // if any exception occurs call this
                .and().exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(customJwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers( "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
