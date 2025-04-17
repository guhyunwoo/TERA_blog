package org.example.blog.domain.user.presentation

import jakarta.servlet.http.HttpServletRequest
import lombok.RequiredArgsConstructor
import org.example.blog.domain.auth.presentation.Dto.TokenResponse
import org.example.blog.domain.user.presentation.dto.LoginRequestDto
import org.example.blog.domain.user.presentation.dto.SignUpRequestDto
import org.example.blog.domain.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = ["/user"])
@RequiredArgsConstructor
@RestController
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun register(@RequestBody request: SignUpRequestDto): ResponseEntity<TokenResponse> {
        val response = userService.register(request)
        return response
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequestDto): ResponseEntity<TokenResponse> {
        val response = userService.login(request)
        return response
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): ResponseEntity<Any> {
        val response = userService.logout(request)
        return response
    }
}