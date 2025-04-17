package org.example.blog.domain.user.service

import jakarta.servlet.http.HttpServletRequest
import org.example.blog.domain.auth.exception.RefreshTokenNotFoundException
import org.example.blog.domain.auth.presentation.Dto.TokenResponse
import org.example.blog.domain.user.domain.User
import org.example.blog.domain.user.domain.repository.UserRepository
import org.example.blog.domain.user.exception.InvalidPasswordException
import org.example.blog.domain.user.exception.UserEmailAlreadyExistsException
import org.example.blog.domain.user.exception.UserNicknameAlreadyExistsException
import org.example.blog.domain.user.exception.UserNotFoundException
import org.example.blog.domain.user.presentation.dto.LoginRequestDto
import org.example.blog.domain.user.presentation.dto.SignUpRequestDto
import org.example.blog.global.security.jwt.JwtTokenProvider
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val redisTemplate: StringRedisTemplate
) {
    @Transactional
    fun register(request: SignUpRequestDto): ResponseEntity<TokenResponse> {
        if (userRepository.existsUserByEmail(request.email)) {
            throw UserEmailAlreadyExistsException
        }
        if (userRepository.existsUserByNickname(request.nickname)) {
            throw UserNicknameAlreadyExistsException
        }

        val user = User(
            encodedPassword = passwordEncoder.encode(request.password),
            nickname = request.nickname,
            email = request.email,
        )
        userRepository.save(user)

        return responseCreate(user)
    }

    fun login(request: LoginRequestDto): ResponseEntity<TokenResponse> {
        val user = userRepository.findUserByEmail(request.email)
            ?: throw UserNotFoundException

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw InvalidPasswordException
        }

        return responseCreate(user)
    }

    fun logout(request: HttpServletRequest): ResponseEntity<Any> {
        val refreshToken = request.cookies?.find { it.name == "refresh_token" }?.value
        if (refreshToken.isNullOrEmpty()) {
            throw RefreshTokenNotFoundException
        }

        redisTemplate.delete("refresh_Token:$refreshToken")

        return ResponseEntity.ok().build()
    }

    fun responseCreate(user: User): ResponseEntity<TokenResponse> {
        val accessToken = jwtTokenProvider.createAccessToken(user.email)
        val refreshToken = jwtTokenProvider.createRefreshToken(user.email)

        redisTemplate.opsForValue().set(
            "refresh_token:${user.email}", refreshToken, Duration.ofDays(30)
        )

        val cookie = ResponseCookie.from("refresh_token", refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(60 * 60 * 24 * 30)
            .sameSite("Strict")
            .build()

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(TokenResponse(accessToken))
    }
}
