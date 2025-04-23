package org.example.blog.domain.postLike.domain.repository

import org.example.blog.domain.post.domain.Post
import org.example.blog.domain.postLike.domain.Like
import org.example.blog.domain.postLike.domain.PostLike
import org.springframework.data.jpa.repository.JpaRepository

interface PostLikeRepository: JpaRepository<PostLike, Like> {
    fun countPostLikesByPost(post: Post): Long
}