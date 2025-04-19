package org.example.blog.domain.post.domain

import jakarta.persistence.*
import org.example.blog.domain.user.domain.User
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "post")
class Post(
    title: String,
    postContent: String,
    user: User,
    imageUrl: String,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val post_id: Long = 0L

    @Column(columnDefinition = "VARCHAR(40)")
    var title: String = title
        protected set

    @Column(columnDefinition = "TEXT")
    var post_content: String = postContent
        protected set

    @Column(columnDefinition = "TEXT")
    var image: String = imageUrl
        protected set

    @CreationTimestamp
    @Column(updatable = false)
    lateinit var created_at: LocalDateTime

    @Column(nullable = false)
    var likeCount: Long = 0L
        protected set

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User = user

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updatePostContent(postContent: String) {
        this.post_content = postContent
    }

    fun updateImage(imageUrl: String) {
        this.image = imageUrl
    }

    fun addLike() {
        likeCount++
    }
}