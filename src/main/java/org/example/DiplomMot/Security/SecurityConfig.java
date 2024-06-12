package org.example.DiplomMot.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //в конфигурацию безопасности внедряем зависимость - обработчик аутентификации
    @Autowired
    private AuthHandler authenticationSuccessHandler;

    //настраиваем цепочку фильтров безопасности для разных страниц и разных ролей
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/css/**", "/showroom", "/login").permitAll()
                        .requestMatchers("/user-profile").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin-profile", "motorcycle-sell/{name}").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .csrf().disable();
        return http.build();
    }

    //создаем кодировщик паролей
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //создаем пользователей, задаем им имена, пароли и роли для менеджера пользовательских данных
    @Bean
    UserDetailsManager inMemoryUserDetailsManager() {
        var commonUser = User.withUsername("user").password("{noop}password").roles("USER").build();
        var admin = User.withUsername("admin").password("{noop}password").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(commonUser, admin);
    }
}