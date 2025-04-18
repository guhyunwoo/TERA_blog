package org.example.blog.global.config

import org.example.blog.global.security.jwt.JwtTokenFilter
import org.example.blog.global.security.jwt.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
){
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf {it.disable()}
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .cors(Customizer.withDefaults())
        http
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        http
            .addFilterBefore(
                JwtTokenFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )

        http
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/auth/user/login").permitAll()
                authorize.requestMatchers("/auth/user/signup").permitAll()
                authorize.requestMatchers("/auth/user/logout").permitAll()

                authorize.requestMatchers("/posts/**").hasAnyRole("USER", "ADMIN")

                authorize.anyRequest().authenticated()
            }

        return http.build()
    }
}