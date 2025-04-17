package org.example.blog.domain.user.exception

import org.example.blog.global.error.exception.BusinessException
import org.example.blog.global.error.exception.ErrorCode

object UserNicknameAlreadyExistsException: BusinessException(ErrorCode.USER_NICKNAME_ALREADY_EXISTS)