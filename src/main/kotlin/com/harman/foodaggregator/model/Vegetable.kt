package com.harman.foodaggregator.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Vegetable (
    @JsonProperty("productId") val productId : String,
    @JsonProperty("productName") val productName: String,
    @JsonProperty("quantity") val quantity:Int,
    @JsonProperty("price") val price:String
)