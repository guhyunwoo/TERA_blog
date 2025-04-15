package org.example.blog.domain.user.domain

import jakarta.persistence.*
import org.example.blog.domain.user.domain.type.Authority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
class User(
    nickname: String,
    password: String,
    email: String,
    ) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0L

    @Column(columnDefinition = "VARCHAR(20)", unique = true)
    var nickname: String = nickname
        protected set

    @Column(nullable = false)
    var password: String = BCryptPasswordEncoder().encode(password)
        protected set

    @Column(columnDefinition = "VARCHAR(40)", nullable = false, unique = true)
    var email: String = email
        protected set

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var authority: Authority = Authority.USER
}