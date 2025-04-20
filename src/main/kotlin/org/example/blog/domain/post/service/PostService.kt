package org.example.blog.domain.post.service

import org.example.blog.domain.auth.exception.UnauthorizedAccessException
import org.example.blog.domain.minio.service.MinioService
import org.example.blog.domain.post.domain.Post
import org.example.blog.domain.post.domain.repository.PostRepository
import org.example.blog.domain.post.exception.PostNotFoundException
import org.example.blog.domain.post.presentation.dto.request.PostAddRequestDto
import org.example.blog.domain.post.presentation.dto.request.PostUpdateRequestDto
import org.example.blog.domain.post.presentation.dto.response.PostResponseDto
import org.example.blog.domain.user.domain.User
import org.example.blog.domain.user.presentation.dto.UserResponseDto
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.multipart.MultipartFile

@Service
class PostService(
    private val postRepository: PostRepository,
    private val minioService: MinioService,
) {
    fun getOnePostById(postId: Long): PostResponseDto{
        val response = postRepository.findById(postId)
            .orElseThrow { PostNotFoundException }

        return createPostResponseDto(response)
    }

    fun getAllPosts(page: Int, size: Int): List<PostResponseDto> {
            val pageable = PageRequest.of(page, size)
        return postRepository.findAll(pageable).content.map { createPostResponseDto(it) }
    }

    @Transactional
    fun addPost(request: PostAddRequestDto,
                image: MultipartFile?,
                user: User
    ): PostResponseDto {
        val imageUrl = minioService.createImageUrl(image)

        val post = Post(
            postContent = request.postContent,
            title = request.title,
            user = user,
            imageUrl = imageUrl,
        )

        return createPostResponseDto(postRepository.save(post))
    }

    @Transactional
    fun updatePost(
        postId: Long,
        request: PostUpdateRequestDto,
        image: MultipartFile?,
        user: User
    ): PostResponseDto {
        val post = postRepository.findById(postId)
            .orElseThrow { PostNotFoundException }

        if (post.user != user) throw UnauthorizedAccessException()

        val imageUrl = image?.let { minioService.createImageUrl(it) } ?: post.image

        request.title?.let { post.updateTitle(it) }
        request.postContent?.let { post.updatePostContent(it) }
        post.updateImage(imageUrl)

        return createPostResponseDto(post)
    }

    private fun createPostResponseDto(post: Post): PostResponseDto {
        return PostResponseDto(
            postId = post.post_id,
            title = post.title,
            postContent = post.post_content,
            imageUrl = post.image,
            createdAt = post.created_at,
            likeCount = post.likeCount,
            user = UserResponseDto(
                id = post.user.user_id,
                nickname = post.user.nickname,
                email = post.user.email,
                profileImageUrl = post.user.image,
            ),
        )
    }

    @Transactional
    fun deletePost(@PathVariable("postId") postId: Long, user: User) {
        val post = postRepository.findById(postId)
            .orElseThrow { PostNotFoundException }

        if (post.user.user_id != user.user_id) throw UnauthorizedAccessException()

        postRepository.deleteById(postId)
    }
}