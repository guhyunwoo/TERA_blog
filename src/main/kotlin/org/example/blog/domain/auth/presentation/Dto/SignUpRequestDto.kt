package org.example.blog.domain.auth.presentation.Dto

data class SignUpRequestDto(
    val nickname: String,
    val password: String,
    val email: String
)