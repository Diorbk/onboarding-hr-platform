package uk.ac.cf.spring.Group13Project1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        String[] staticResources = {
                "/css/**",
                "/images/**",
                "/js/**",
                "/fonts/**",
                "/keystore/**",
                "/path/to/js/recaptcha-api.js"
        };

        String[] userPrivilegeList = {
                "item/**",
                "items/**"
                //add more privileges in future
        };

        String[] adminPrivilegeList = {
                "userList",
                "register",
                "contacts/**"
        };

        http
                .authorizeHttpRequests(request -> request
                    .requestMatchers("employees/**",
                            "item/**",
                            "items/**").hasAnyRole("ADMIN","USER")
                    .requestMatchers(adminPrivilegeList).hasRole("ADMIN")
                    .requestMatchers(userPrivilegeList).hasRole("USER")
                    .requestMatchers("/reset-password", "/reset-password-confirm", "/reset-password-confirm/**").permitAll()
                    .requestMatchers(staticResources).permitAll()
                    .anyRequest().authenticated()
        )

                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/employees", true)
                )

            .logout((l) -> l.permitAll().logoutSuccessUrl("/login"));

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        JdbcDaoImpl jdbcUserDetails = new JdbcDaoImpl();
        jdbcUserDetails.setDataSource(dataSource);
        jdbcUserDetails.setUsersByUsernameQuery("select username, password, enabled" +
                " from users where username=?");

        jdbcUserDetails.setAuthoritiesByUsernameQuery("select username, authority" +
                " from user_authorities where username=?");
        return jdbcUserDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}