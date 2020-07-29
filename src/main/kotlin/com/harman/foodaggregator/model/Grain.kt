package com.harman.foodaggregator.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Grain (
        @JsonProperty("itemId") val itemId : String,
        @JsonProperty("itemName") val itemName: String,
        @JsonProperty("quantity") val quantity:Int,
        @JsonProperty("price") val price:String

)