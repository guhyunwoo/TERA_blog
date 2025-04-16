package org.example.blog.global.security.jwt.exception

import org.example.blog.global.error.exception.BusinessException
import org.example.blog.global.error.exception.ErrorCode

object InvalidJwtException: BusinessException(ErrorCode.INVALID_JWT)