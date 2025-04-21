package org.example.blog.domain.like.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import lombok.EqualsAndHashCode
import java.io.Serializable

@Embeddable
@EqualsAndHashCode
data class Like(
    @Column(name = "user_id")
    var userId : Long,
    @Column(name = "post_id")
    var postId : Long,
) : Serializable