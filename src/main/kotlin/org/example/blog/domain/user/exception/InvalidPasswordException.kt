package org.example.blog.domain.user.exception

import org.example.blog.global.error.exception.BusinessException
import org.example.blog.global.error.exception.ErrorCode

object InvalidPasswordException: BusinessException(ErrorCode.INVALID_PASSWORD)