package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class Controller {

    val counter = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "") name: String) =
            Greeting(counter.incrementAndGet(), when (name) {
                "" -> """
                    Who are you?
                    Please specify your name with the name param
                """.trimIndent()
                else -> "hello, $name"
            })
}

data class Greeting(val id: Long, val content: String)