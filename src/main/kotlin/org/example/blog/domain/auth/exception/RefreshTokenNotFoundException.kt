package org.example.blog.domain.auth.exception

import org.example.blog.global.error.exception.BusinessException
import org.example.blog.global.error.exception.ErrorCode

object RefreshTokenNotFoundException: BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND)