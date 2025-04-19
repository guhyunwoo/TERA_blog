package org.example.blog.domain.post.presentation.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.web.multipart.MultipartFile


data class PostAddRequestDto(
    @field:NotBlank(message = "게시물 제목이 비어있으면 안 됩니다.")
    @field:Size(message = "게시글 제목은 40자 이하로 작성해야 합니다.")
    val title: String,

    @field:NotBlank(message = "게시글 내용이 비어있으면 안 됩니다.")
    @field:Size(message = "게시글 내용은 21000자 이상 작성할 수 없습니다.")
    val postContent: String,

    val image: MultipartFile? = null,
)