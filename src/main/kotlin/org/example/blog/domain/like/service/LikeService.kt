package org.example.blog.domain.like.service

import org.example.blog.domain.post.domain.repository.PostRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val postRepository: PostRepository,
) {
//    @Scheduled(fixedRate = 10000)
//    fun syncLikesToDB() {
//        val keys = redisTemplate.keys("post:*:likes")
//        for (key in keys) {
//            val postId = key.split(":")[2].toLong()
//            val userIds = redisTemplate.opsForSet().members(key) ?: continue
//            for (userId in userIds) {
//                postRepository.(postId, userId) // 이건 직접 구현해야 해
//            }
//            redisTemplate.delete(key) // 동기화 끝나면 Redis 비워줌
//        }
//    }

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