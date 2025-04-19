package org.example.blog.domain.auth.presentation

import jakarta.servlet.http.HttpServletRequest
import org.example.blog.domain.auth.presentation.Dto.TokenResponse
import org.example.blog.domain.auth.service.AuthDetailService
import org.example.blog.domain.auth.presentation.Dto.LoginRequestDto
import org.example.blog.domain.auth.presentation.Dto.SignUpRequestDto
import org.example.blog.global.security.principal.AuthDetails
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth/user")
@RestController
class AuthDetailController(
    private val authDetailService: AuthDetailService
) {
    @PostMapping("/signup")
    fun register(@RequestBody request: SignUpRequestDto): ResponseEntity<TokenResponse> {
        val response = authDetailService.register(request)
        return response
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequestDto): ResponseEntity<TokenResponse> {
        val response = authDetailService.login(request)
        return response
    }

    @PostMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        @AuthenticationPrincipal user: AuthDetails
    ): ResponseEntity<Any> {
        val response = authDetailService.logout(request, user)
        return response
    }
}