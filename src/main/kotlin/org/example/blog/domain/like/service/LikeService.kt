package org.example.blog.domain.like.service

import org.example.blog.domain.post.domain.repository.PostRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val postRepository: PostRepository
) {
    fun toggleLike(postId: Long, userId: Long): Long {
        val key = "post:$postId:likes"
        val ops = redisTemplate.opsForSet()

        if (ops.isMember(key, userId.toString()) == true) {
            ops.remove(key, userId.toString())
        } else {
            ops.add(key, userId.toString())
        }

        return ops.size(key) ?: 0L
    }
}