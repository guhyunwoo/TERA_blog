package org.example.blog.domain.post.presentation.dto.request

import jakarta.validation.constraints.Size

data class PostUpdateRequestDto(
    @field:Size(max = 40,message = "게시글 제목은 40자 이하로 작성해야 합니다.")
    val title: String?,

    @field:Size(max= 21000, message = "게시글 내용은 21000자 이하로 작성해야 합니다.")
    val postContent: String?
)