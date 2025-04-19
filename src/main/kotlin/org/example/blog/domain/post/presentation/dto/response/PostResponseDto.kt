package org.example.blog.domain.post.presentation.dto.response

import org.example.blog.domain.user.presentation.dto.UserResponseDto
import java.time.LocalDateTime

data class PostResponseDto(
    val postId: Long,
    val title: String,
    val postContent: String,
    val imageUrl: String,
    val createdAt: LocalDateTime,
    val likeCount: Long,
    val user: UserResponseDto,
)