package org.example.blog.domain.auth.presentation.Dto

data class LoginRequestDto(
    val email: String,
    val password: String
)