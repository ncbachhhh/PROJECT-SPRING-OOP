package DO_AN.OOP.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/staff/**",
                                "/customer/**",
                                "/attendance/**",
                                "/dish/**",
                                "/ingredient/**",
                                "/inventory-item/**",
                                "/order/**",
                                "/recipe/**",
                                "/invoice/**"
                        ).permitAll() // Cho phép không xác thực
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    // Mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
