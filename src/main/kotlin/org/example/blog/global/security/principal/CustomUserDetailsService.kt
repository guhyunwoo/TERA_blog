package org.example.blog.global.security.principal

import org.example.blog.domain.user.facade.UserFacade
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userFacade: UserFacade
): UserDetailsService {
    override fun loadUserByUsername(email: String)
            = AuthDetails(userFacade.getUserByEmail(email))
}