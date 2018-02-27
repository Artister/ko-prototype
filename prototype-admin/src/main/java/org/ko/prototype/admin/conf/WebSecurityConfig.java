package org.ko.prototype.admin.conf;

import org.ko.prototype.support.filter.security.AuthenticationFilter;
import org.ko.prototype.support.handler.security.FailureHandler;
import org.ko.prototype.support.handler.security.SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        return successHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/admin", "/admin/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/timeout")
                .and().csrf().disable();
//        http.headers()
//                .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
//                .and()
//                .csrf()
//                .disable()
//                .formLogin()
//                .successHandler(authenticationSuccessHandler())
////                .failureHandler(authenticationFailureHandler())
//                .loginProcessingUrl("/login")
////                .loginPage("/index.html")
//                .permitAll()
//                .and()
//                .logout()
////                .logoutSuccessHandler(authenticationLogoutSuccessHandler())
//                .deleteCookies("JSESSIONID").invalidateHttpSession(true) // 设置退出,invalidateHttpSession设置退出后无效session
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .exceptionHandling()
////                .authenticationEntryPoint(new AjaxAwareAuthenticationEntryPoint("/index.html"))
//                .and()
//                .sessionManagement()
//                .invalidSessionUrl("/timeout")
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(false)
//                .expiredUrl("/timeout");
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new SuccessHandler());
        filter.setAuthenticationFailureHandler(new FailureHandler());
        filter.setFilterProcessesUrl("/login");

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }
}