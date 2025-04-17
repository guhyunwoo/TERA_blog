package org.example.blog.global.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.blog.global.error.ErrorResponse
import org.example.blog.global.error.exception.BusinessException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token: String? = jwtTokenProvider.resolveToken(request)
            token?.let {
                val authentication: Authentication = jwtTokenProvider.authorization(token)
                SecurityContextHolder.getContext().authentication = authentication
            }

            filterChain.doFilter(request, response)
        } catch (e: BusinessException) {
            handleException(response, e)
        }

    }

    private fun handleException(response: HttpServletResponse, e: BusinessException) {
        val errorCode = e.errorCode
        val res = ErrorResponse(
            errorCode.status, errorCode.message
        )

        response.characterEncoding = "UTF-8"
        response.contentType = "application/json; charset=utf-8"
        response.status = errorCode.status
        response.writer.write(res.toString())
    }
}