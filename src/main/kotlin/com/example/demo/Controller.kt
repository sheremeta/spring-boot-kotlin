package com.example.demo

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
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
                    Please specify your name with the 'name' param
                """
                else -> "hello, $name"
            })

    @GetMapping("/bot")
    fun bot(@RequestParam(value = "question", defaultValue = "") question: String) =
            Greeting(counter.incrementAndGet(), when (question) {
                "" -> """
                    Please ask something?
                    Please specify your question with the 'question' param
                """
                else -> {
                    val job1 = GlobalScope.async { " Hello, " }
                    val job2 = GlobalScope.async { " Is it question for me? " }
                    val job3 = GlobalScope.async { " $question " }

                    runBlocking {
                        job1.await() + job2.await() + job3.await()
                    }
                }
            })
}

data class Greeting(val id: Long, val content: String)