package org.example.blog.domain.user.exception

import org.example.blog.global.error.exception.BusinessException
import org.example.blog.global.error.exception.ErrorCode

object UserEmailAlreadyExistsException: BusinessException(ErrorCode.USER_EMAIL_ALREADY_EXISTS)