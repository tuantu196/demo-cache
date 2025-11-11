package com.example.demo

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Controller
class TestController {
    @GetMapping("/health-check")
    fun healthCheck(): String {
        return "Ok!"
    }
}