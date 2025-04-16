package org.example.blog.domain.user.facade

import org.example.blog.domain.user.domain.User
import org.example.blog.domain.user.domain.repository.UserRepository
import org.example.blog.domain.user.exception.UserNotFoundException
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository
) {
    fun getCurrentUser(): User {
        val email: String = SecurityContextHolder.getContext().authentication.name
        return getUserByEmail(email)
    }

    fun getUserByEmail(email: String)
            = userRepository.findUserByEmail(email) ?: throw UserNotFoundException

    fun isLogin(): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication

        return authentication != null
                && authentication.isAuthenticated
                && authentication !is AnonymousAuthenticationToken
    }
}