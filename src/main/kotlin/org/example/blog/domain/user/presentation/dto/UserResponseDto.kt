package org.example.blog.domain.user.presentation.dto

data class UserResponseDto(
    val id: Long,
    val nickname: String,
    val email: String,
    val profileImageUrl: String,
)