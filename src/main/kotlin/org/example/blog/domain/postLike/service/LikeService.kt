package org.example.blog.domain.postLike.service

import org.example.blog.domain.post.domain.repository.PostRepository
import org.example.blog.domain.postLike.domain.Like
import org.example.blog.domain.postLike.domain.PostLike
import org.example.blog.domain.postLike.domain.repository.PostLikeRepository
import org.example.blog.domain.user.domain.repository.UserRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val postLikeRepository: PostLikeRepository,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    @Scheduled(fixedRate = 10000)
    fun syncLikesToDB() {
        val keys = redisTemplate.keys("post:*:likes")

        for (key in keys) {
            val postId = key.split(":")[1].toLong()
            val userIdObjs = redisTemplate.opsForSet().members(key) ?: continue
            val userIds = userIdObjs.mapNotNull { it.toString().toLongOrNull() }

            if (userIds.isEmpty()) continue

            val post = postRepository.findById(postId).orElse(null) ?: continue

            val users = userRepository.findAllById(userIds)
            val userMap = users.associateBy { it.user_id }

            val postLikes = mutableListOf<PostLike>()

            for (userId in userIds) {
                val user = userMap[userId] ?: continue

                val postLike = PostLike(
                    id = Like(userId = userId, postId = postId),
                    user = user,
                    post = post
                )
                postLikes.add(postLike)
            }

            if (postLikes.isNotEmpty()) {
                postLikeRepository.saveAll(postLikes)
            }

            redisTemplate.delete(key)
        }
    }


    fun toggleLike(postId: Long, userId: Long): Long {
        val key = "post:$postId:likes"
        val ops = redisTemplate.opsForSet()

        if (ops.isMember(key, userId) == true) {
            ops.remove(key, userId)
        } else {
            ops.add(key, userId)
        }

        return ops.size(key) ?: 0L
    }

    fun checkLikeStatus(postId: Long, userId: Long): Boolean {
        val key = "post:$postId:likes"
        return redisTemplate.opsForSet().isMember(key, userId) == true ||
                postLikeRepository.existsById(Like(userId, postId))
    }

    fun getLikeCount(postId: Long): Long {
        val key = "post:$postId:likes"
        val redisCount = redisTemplate.opsForSet().size(key) ?: 0L

        val post = postRepository.findById(postId).orElse(null)
        val dbCount = postLikeRepository.countPostLikesByPost(post)
        return redisCount + dbCount
    }
}