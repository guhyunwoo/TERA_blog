package org.example.blog.domain.user.service

import lombok.RequiredArgsConstructor
import org.example.blog.domain.user.domain.User
import org.example.blog.domain.user.domain.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@RequiredArgsConstructor
@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun register(user: User): User {

    }


}