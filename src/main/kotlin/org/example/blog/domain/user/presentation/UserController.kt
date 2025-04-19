package org.example.blog.domain.user.presentation

import org.example.blog.domain.user.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(path = ["/user"])
@RestController
class UserController(private val userService: UserService) {


}