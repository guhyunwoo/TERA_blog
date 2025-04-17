package org.example.blog.domain.user.domain.repository

import org.example.blog.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findUserByEmail(email: String): User?

    fun existsUserByEmail(email: String): Boolean
    fun existsUserByNickname(nickname: String): Boolean
}