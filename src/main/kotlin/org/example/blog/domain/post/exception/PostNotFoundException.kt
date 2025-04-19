package org.example.blog.domain.post.exception

import org.example.blog.global.error.exception.BusinessException
import org.example.blog.global.error.exception.ErrorCode

object PostNotFoundException: BusinessException(ErrorCode.POST_NOT_FOUND)