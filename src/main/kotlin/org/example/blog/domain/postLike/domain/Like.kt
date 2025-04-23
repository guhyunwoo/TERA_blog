package org.example.blog.domain.postLike.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class Like(
    @Column(name = "user_id")
    var userId : Long,
    @Column(name = "post_id")
    var postId : Long,
) : Serializable