package org.example.blog.global.error.exception

enum class ErrorCode(
    val status: Int,
    val message: String
) {
    EXPIRED_JWT(401, "Expired Jwt"),
    INVALID_JWT(401, "Invalid Jwt"),
    USER_EMAIL_ALREADY_EXISTS(400, "User Email Already Exists"),
    USER_NICKNAME_ALREADY_EXISTS(400, "User Nickname Already Exists"),
    INVALID_PASSWORD(401, "Password Invalid"),
    USER_NOT_FOUND(404, "User Not Found"),


    TOKEN_NOT_FOUND(404, "Token Not Found"),
    REFRESH_TOKEN_NOT_FOUND(404, "Refresh Token Not Found"),



    // server
    VALIDATION_ERROR(400, "Validation Error"),
    NOT_SUPPORTED_URI_ERROR(500, "URI Not Supported"),
    NOT_SUPPORTED_METHOD_ERROR(405, "Method Not Allowed"),
}