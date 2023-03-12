package com.chicasensistemas.auth.config.security;

import com.chicasensistemas.auth.RoleType;
import com.chicasensistemas.auth.config.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.time.Duration;
import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowedHeaders(Arrays.asList("Origin,Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization"));
        cc.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        cc.setAllowedOrigins(Arrays.asList("/*"));
        cc.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "PATCH"));
        cc.addAllowedOrigin("http://localhost:3000");
        cc.setMaxAge(Duration.ZERO);
        cc.setAllowCredentials(Boolean.TRUE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cc);
        return source;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors(Customizer.withDefaults())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/register","/mp/notification")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/auth/google")
                .permitAll()
                .antMatchers(HttpMethod.PUT, "/user/modify")
                .hasAnyRole(RoleType.ADMIN.name(), RoleType.USER.name())
                .antMatchers(HttpMethod.PUT, "/user/disableUser")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/user/enableUser")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/user/list")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/user/me")
                .hasAnyRole(RoleType.ADMIN.name(), RoleType.USER.name())
                .antMatchers(HttpMethod.GET, "/user/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/course/create")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/course/disable/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/course/enable/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/course/modify")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/course/list")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/course/getByName")
                .permitAll()
                //====doesn't work
                .antMatchers(HttpMethod.PUT, "/course/addModule")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/course/removeModule")
                .hasRole(RoleType.ADMIN.name())
                //=====
                .antMatchers(HttpMethod.GET, "/course/find/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/difficulty/create")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/difficulty/enable/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/difficulty/disable/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/difficulty/modify")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/difficulty/list")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/difficulty/getByName")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/difficulty/find/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/inscription/create")
                .hasAnyRole(RoleType.USER.name(), RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/inscription/enable/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/inscription/disable/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/inscription")
                .hasAnyRole(RoleType.ADMIN.name(), RoleType.USER.name())
                .antMatchers(HttpMethod.GET, "/inscription/user/**")
                .hasAnyRole(RoleType.USER.name(), RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/inscription/course/**")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/inscription/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.POST,"/video/create")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/video/enable/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/video/disable/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/video/modify/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/video/list")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/video/add/module")//no deberia estar, es de modulo
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/video/find/{id:[\\d+]}")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/module/create")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/module/get")
                .permitAll()
                .antMatchers(HttpMethod.PUT,"/module/enable/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/module/disable/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/module/modify/{id:[\\d+]}")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/module/find/{id:[\\d+]}")
                .permitAll()
                .antMatchers(HttpMethod.PUT,"/module/removeVideo")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.PUT,"/module/addVideo")
                .hasRole(RoleType.ADMIN.name())
                .antMatchers(HttpMethod.POST,"/password/forgot")
                .permitAll()
                .antMatchers(HttpMethod.PUT,"/password/reset")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint());
    }

}
