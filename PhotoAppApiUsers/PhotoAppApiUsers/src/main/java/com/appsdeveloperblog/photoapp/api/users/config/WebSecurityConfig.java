package com.appsdeveloperblog.photoapp.api.users.config;

import com.appsdeveloperblog.photoapp.api.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private Environment environment;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private UserService userService;

  Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

  @Autowired
  public WebSecurityConfig(final Environment environment, final BCryptPasswordEncoder bCryptPasswordEncoder, final UserService userService) {
    this.environment = environment;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userService = userService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests().antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
            .and()
            .addFilter(getAuthenticationFilter());
    http.headers().frameOptions().disable();
  }

  private AuthenticationFilter getAuthenticationFilter() throws Exception
  {
    AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment, authenticationManager());
    //authenticationFilter.setAuthenticationManager(authenticationManager());
    authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
    return authenticationFilter;
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
  }
}
