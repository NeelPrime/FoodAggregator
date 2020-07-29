package com.harman.foodaggregator.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Fruit (
        @JsonProperty("id") val id : String,
        @JsonProperty("name") val name: String,
        @JsonProperty("quantity") val quantity:Int,
        @JsonProperty("price") val price:String
)