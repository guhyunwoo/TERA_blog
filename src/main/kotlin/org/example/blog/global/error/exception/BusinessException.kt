package org.example.blog.global.error.exception

import java.lang.Error

abstract class BusinessException (
    val errorCode: ErrorCode
): RuntimeException()