package org.example.blog.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.example.blog.domain.auth.domain.RefreshToken
import org.example.blog.domain.auth.domain.repository.RefreshTokenRepository
import org.example.blog.global.config.properties.JwtProperties
import org.example.blog.global.security.jwt.exception.InvalidJwtException
import org.example.blog.global.security.jwt.exception.ExpiredJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authDetailsService: UserDetailsService
) {
    private val ACCESS_KEY: String = "access_token"
    private val REFRESH_KEY: String = "refresh_token"

    fun createAccessToken(email: String): String
            = createToken(email, ACCESS_KEY, jwtProperties.accessTime)

    fun createRefreshToken(email: String): String {
        val token: String = createToken(email, REFRESH_KEY, jwtProperties.refreshTime)
        refreshTokenRepository.save(RefreshToken(token, email))
        return token
    }

    fun createToken(email: String, type: String, time: Long): String {
        val now = Date()
        val key = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())
        return Jwts.builder()
            .signWith(key)
            .setSubject(email)
            .setHeaderParam("typ", type)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + time))
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearer: String? = request.getHeader("Authorization")
        return parseToken(bearer)
    }

    fun parseToken(bearerToken: String?): String? {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "")
        }
        return null
    }

    fun authorization(token: String): UsernamePasswordAuthenticationToken {
        val userDetails: UserDetails = authDetailsService.loadUserByUsername(getTokenSubject(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getTokenSubject(subject: String):String
            = getTokenBody(subject).body.subject

    fun getTokenBody(token: String): Jws<Claims> {
        try {
            val key = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())
            return Jwts.parser()
                .verifyWith(key).build()
                .parseSignedClaims(token)
        } catch (e: io.jsonwebtoken.ExpiredJwtException) {
            throw ExpiredJwtException
        } catch (e: Exception) {
            throw InvalidJwtException
        }
    }
}