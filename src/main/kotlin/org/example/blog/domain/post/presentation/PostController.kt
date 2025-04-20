package org.example.blog.domain.post.presentation

import jakarta.validation.Valid
import org.example.blog.domain.post.presentation.dto.request.PostAddRequestDto
import org.example.blog.domain.post.presentation.dto.request.PostUpdateRequestDto
import org.example.blog.domain.post.presentation.dto.response.PostResponseDto
import org.example.blog.domain.post.service.PostService
import org.example.blog.global.security.principal.AuthDetails
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService,
) {
    @GetMapping("/{postId}")
    fun getPost(@PathVariable postId: Long): ResponseEntity<PostResponseDto> {
        val response = postService.getOnePostById(postId)
        return ResponseEntity.ok().body(response)
    }

    @GetMapping
    fun getAllPosts(
        @RequestParam(defaultValue = "0") page: Int, // 기본값은 1
        @RequestParam(defaultValue = "10") size: Int  // 기본값은 10
    ): ResponseEntity<List<PostResponseDto>> {
        val posts = postService.getAllPosts(page, size) // 서비스 메서드를 호출하여 페이징 처리된 데이터 가져오기
        return ResponseEntity.ok(posts)
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun addPost(
        @RequestPart("post") @Valid request: PostAddRequestDto,
        @RequestPart("image", required = false) image: MultipartFile?,
        @AuthenticationPrincipal authDetails: AuthDetails,
    ): ResponseEntity<PostResponseDto> {
        val response = postService.addPost(request, image, authDetails.getUser())
        return ResponseEntity.ok().body(response)
    }

    @PatchMapping("/{postId}")
    fun updatePost(
        @PathVariable("postId") postId: Long,
        @RequestPart @Validated request: PostUpdateRequestDto,
        @RequestPart("image", required = false) image: MultipartFile?,
        @AuthenticationPrincipal authDetails: AuthDetails,
    ): ResponseEntity<PostResponseDto> {
        val response = postService.updatePost(postId, request, image, authDetails.getUser())
        return ResponseEntity.ok().body(response)
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable("postId") postId: Long,
        @AuthenticationPrincipal authDetails: AuthDetails
    ): ResponseEntity<Any> {
        postService.deletePost(postId, authDetails.getUser())
        return ResponseEntity.noContent().build()
    }
}