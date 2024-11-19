package tw.idv.frank.simple_standard_law.common.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tw.idv.frank.simple_standard_law.common.oauth.OAuth2AuthenticationSuccessHandlerImpl;
import tw.idv.frank.simple_standard_law.common.security.filter.JwtFilter;
import tw.idv.frank.simple_standard_law.common.security.handler.AccessDeniedHandlerImpl;
import tw.idv.frank.simple_standard_law.common.security.handler.AuthenticationEntryPointHandlerImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private OAuth2AuthenticationSuccessHandlerImpl successHandler;

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
                        // 符合就不會在往下，故匹配範圍大的要再越後面
                        .requestMatchers("/*/*.pdf").permitAll()
                        .requestMatchers("/users/register", "/users/login", "/users/logout").permitAll()
                        .requestMatchers("/*.html", "/css/*.css", "/js/*.js", "/png/**", "/report/**").permitAll()
                        .requestMatchers("/users/*/funcs").authenticated()
                        .requestMatchers("/users/**", "/roles/**", "/funcs/**").hasAuthority("root_x")
                .anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login
                        .successHandler(successHandler)
                )

        // 加入自訂義的 Filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // 加入自訂義的 authenticationEntryPoint、accessDeniedHandler
                .exceptionHandling(exceptionHandler -> exceptionHandler
                        .authenticationEntryPoint(new AuthenticationEntryPointHandlerImpl())
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()))
                // 禁用 Security內建的 csrf保護機制
                .csrf(csrf -> csrf.disable());
//                // Security 預設登入畫面
//                .formLogin(Customizer.withDefaults());
//
//                // 這個配置主要用於防範 Clickjacking 攻擊，即保護應用不被其他網站通過 <iframe> 嵌入
//                .headers(
//                        headers -> headers.frameOptions(
//                                // 允許同源的頁面可以嵌入
//                                frameOptionsConfig -> frameOptionsConfig.sameOrigin()
//                        )
//                );
        return http.build();
    }
}
