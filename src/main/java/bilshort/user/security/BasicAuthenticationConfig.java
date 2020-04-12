package bilshort.user.security;

import bilshort.user.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class BasicAuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/api/v1/register/**").permitAll()
                    .antMatchers("/api/v1/shortURL").permitAll()
                    .antMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/api/v1/user/**").hasAnyAuthority("ADMIN", "USER")
                    .anyRequest()
                    .authenticated()
                .and()
                    .csrf()
                        .disable()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/api/v1/user")
                    .usernameParameter("user_name")
                    .passwordParameter("password")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/api/v1/user")
                .and()
                    .exceptionHandling();
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}
