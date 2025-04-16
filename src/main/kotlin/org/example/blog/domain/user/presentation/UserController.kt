package org.example.blog.domain.user.presentation

import lombok.RequiredArgsConstructor
import org.example.blog.domain.user.domain.User
import org.example.blog.domain.user.presentation.dto.SignUpRequestDto
import org.example.blog.domain.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequiredArgsConstructor
@RestController
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun register(@RequestBody requset: SignUpRequestDto): ResponseEntity<Token> {
        userService.register(requset);
        jwtProvider
    }
}