package org.example.blog.domain.user.presentation.dto

data class SignUpRequestDto(
    val nickname: String,
    val password: String,
    val email: String
)