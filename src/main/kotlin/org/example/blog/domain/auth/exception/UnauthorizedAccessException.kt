package org.example.blog.domain.auth.exception

import org.example.blog.global.error.exception.BusinessException
import org.example.blog.global.error.exception.ErrorCode

class UnauthorizedAccessException: BusinessException(ErrorCode.UnauthorizedAccess)