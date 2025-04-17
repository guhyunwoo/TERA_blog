package org.example.blog.domain.user.presentation.dto

data class LoginRequestDto(
    val email: String,
    val password: String
)