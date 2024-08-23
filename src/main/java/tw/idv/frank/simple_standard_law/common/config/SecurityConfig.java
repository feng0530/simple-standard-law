package tw.idv.frank.simple_standard_law.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tw.idv.frank.simple_standard_law.common.filter.JwtFilter;
import tw.idv.frank.simple_standard_law.common.handler.AuthenticationEntryPointHandlerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Security 過濾器鍊
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtFilter jwtFilter
    ) throws Exception {

        http.authorizeHttpRequests(registry -> registry
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers("/users/login").permitAll()
                .anyRequest().authenticated())

                // 加入自訂義的 Filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // 加入自訂義的 authenticationEntryPoint、accessDeniedHandler
                .exceptionHandling(exceptionHandler -> exceptionHandler
                        .authenticationEntryPoint(new AuthenticationEntryPointHandlerImpl())
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()))
                // 禁用 Security內建的 csrf保護機制
                .csrf(csrf -> csrf.disable());
                // Security 預設登入畫面
//                .formLogin(Customizer.withDefaults());
        return http.build();
    }
}
