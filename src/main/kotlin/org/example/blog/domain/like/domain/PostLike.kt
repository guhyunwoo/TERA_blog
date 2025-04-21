package org.example.blog.domain.like.domain

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "post_like")
class PostLike(
    @EmbeddedId
    val id: Like
)
