package com.mleon.forumkotlin.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthcheckController {
    @GetMapping("/health-check")
    @ResponseBody
    fun execute() = "OK"
}
