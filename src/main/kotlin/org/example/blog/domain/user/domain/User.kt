package org.example.blog.domain.user.domain

import jakarta.persistence.*
import org.example.blog.domain.post.domain.Post
import org.example.blog.domain.user.domain.type.Authority

@Entity
@Table(name = "user")
class User(
    nickname: String,
    encodedPassword: String,
    email: String,
    profileImageUrl: String,
    ) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val user_id: Long = 0L

    @Column(columnDefinition = "VARCHAR(20)", unique = true)
    var nickname: String = nickname
        protected set

    @Column(nullable = false)
    var password: String = encodedPassword
        protected set

    @Column(columnDefinition = "TEXT", nullable = false)
    var image: String = profileImageUrl

    @Column(columnDefinition = "VARCHAR(40)", nullable = false, unique = true)
    var email: String = email
        protected set

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var authority: Authority = Authority.ROLE_USER

    @OneToMany(mappedBy = "user", cascade = [(CascadeType.REMOVE)], orphanRemoval = true)
    val post: MutableList<Post> = mutableListOf()
}