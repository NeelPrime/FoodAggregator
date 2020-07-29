package com.harman.foodaggregator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FoodAggregatorApplication

fun main(args: Array<String>) {
    println("Hello before")
    runApplication<FoodAggregatorApplication>(*args)
    println("Hello after")
}
