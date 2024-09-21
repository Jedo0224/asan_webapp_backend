package com.asanhospital.server.config;

import com.asanhospital.server.service.Jwt.JwtAuthenticationFilter;
import com.asanhospital.server.service.Jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] SWAGGER_PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        // 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정
                        (auth) -> auth
                                // 해당 API에 대해서는 모든 요청을 허가
                                .requestMatchers("/auth/login").permitAll()
                                .requestMatchers("/auth/signUp").permitAll()
                                .requestMatchers("/organization").permitAll()
                                .requestMatchers("/beacon-data/**").permitAll()  // Allow access to /beacon-data endpoints
                                .requestMatchers("/sensorFile/**").permitAll()
                                .requestMatchers("/patient/getPatientList").permitAll()
                                .requestMatchers("/patient/getConnectionLogList/**").permitAll()
                                .requestMatchers("/token/**").permitAll()
                                //Swagger 관련 권한 설정
                                .requestMatchers(SWAGGER_PERMIT_URL_ARRAY).permitAll()
                                // USER 권한이 있어야 요청할 수 있음
                                .requestMatchers("/auth/test").hasRole("USER")
                                .anyRequest().authenticated()
                )
                // REST API이므로 basic auth 및 csrf 보안을 사용하지 않음
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                // JWT를 사용하기 때문에 세션을 사용하지 않음
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
