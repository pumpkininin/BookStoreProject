package sad.fit2021.bookstoreproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import sad.fit2021.bookstoreproject.security.AppUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(    //Enable method level security base on annotations
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserDetailsService appUserDetailsService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // http builder configurations for authorize requests and form login (see below)

        http.authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/js/*").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/publisher/**").hasAnyRole("PUBLISHER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        http
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/book/page", true)
                .and()
                .logout().deleteCookies("JSESSIONID")
                .and()
                .rememberMe().rememberMeParameter("remember-me").key("UniqueKey").tokenValiditySeconds(84600);
        ;
        http.sessionManagement()
                .sessionFixation().migrateSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/invalidSession.html")
                .maximumSessions(2)
                .expiredUrl("/sessionExpired.html")
                .maxSessionsPreventsLogin(false);

    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


}

