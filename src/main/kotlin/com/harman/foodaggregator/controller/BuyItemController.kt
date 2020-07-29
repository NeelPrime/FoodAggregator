package com.harman.foodaggregator.controller

import com.harman.foodaggregator.service.BuyItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class BuyItemController {

    @Autowired lateinit var buyItemService:BuyItemService


    @RequestMapping("/")
    fun home():String
    {
        return "Welcome User"
    }

    @RequestMapping("/buy-item")
    fun buyItem(@RequestParam("name")name:String): List<Any> {
        return buyItemService.getItems(name)
    }
    @RequestMapping("/buy-item-qty-price")
    fun buyItemQtyPrice(@RequestParam("item")item:String,@RequestParam("quantity")quantity:Int,@RequestParam("price")price:Double): List<Any> {
        return buyItemService.getItemByQtyPrice(item,quantity,price)
    }
    @RequestMapping("/fast-buy-item")
    fun fastBuyItem(@RequestParam("name")name:String): List<Any> {
        return buyItemService.getFastBuyItems(name)
    }


}