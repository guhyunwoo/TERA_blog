package org.example.blog.domain.user.presentation.dto

import sun.security.util.Password

data class SignUpRequestDto(
    val nickname: nickname,
    val password: Password,
) {

}