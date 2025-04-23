package org.example.blog.domain.postLike.domain

import jakarta.persistence.*
import org.example.blog.domain.post.domain.Post
import org.example.blog.domain.user.domain.User

@Entity
@Table(name = "post_like")
class PostLike(
    @EmbeddedId
    val id: Like,

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    var user : User,

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    var post : Post,
)
