package com.harman.foodaggregator.service

import com.harman.foodaggregator.model.Grain
import com.harman.foodaggregator.model.Vegetable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.stream.Collector
import java.util.stream.Collectors
import kotlin.collections.ArrayList
import com.harman.foodaggregator.model.Fruit as Fruit

@Service
class BuyItemService {

    @Autowired
    lateinit var restTemplate: RestTemplate

    private val fruitsContainer = HashSet<Fruit>()
    private val vegetablesContainer = HashSet<Vegetable>()
    private val grainsContainer = HashSet<Grain>()

    fun getItems(name: String): List<Any> {

        val itemList = ArrayList<Any>()

        val fruitResponse = restTemplate.getForEntity("https://run.mocky.io/v3/c51441de-5c1a-4dc2-a44e-aab4f619926b", Array<Fruit>::class.java)

        //itemList.addAll(listOf(Arrays.asList(response1.getBody())))
        val fruits = fruitResponse.body

        if (fruits != null)
            for (item in fruits) {
                if (item.name.equals(name, true)) {
                    itemList.add(item)
                }
            }
        val vegetableResponse = restTemplate.getForEntity("https://run.mocky.io/v3/4ec58fbc-e9e5-4ace-9ff0-4e893ef9663c", Array<Vegetable>::class.java)

        // itemList.addAll(listOf(Arrays.asList(response2.getBody())))
        val vegetables = vegetableResponse.body


        if (vegetables != null)
            for (item in vegetables) {
                if (item.productName.equals(name, true)) {
                    itemList.add(item)
                }
            }

        val grainResponse = restTemplate.getForEntity("https://run.mocky.io/v3/e6c77e5c-aec9-403f-821b-e14114220148", Array<Grain>::class.java)

        // itemList.addAll(listOf(Arrays.asList(response3.getBody())))
        val grains = grainResponse.body
        // println(fruits)

        if (grains != null)
            for (item in grains) {
                if (item.itemName.equals(name, true)) {
                    itemList.add(item)
                }
            }
        //  println(itemList)
        if (itemList.size == 0) {
            //   println(itemList)
            itemList.add("NOT_FOUND")
        }
        println(itemList)
        return itemList
    }

    fun getItemByQtyPrice(item: String, quantity: Int, price: Double): List<Any> {
        val itemSet = HashSet<Any>()

        if (fruitsContainer.size == 0 || vegetablesContainer.size == 0 || grainsContainer.size == 0) {
            //initialize data from API
            initializeDataIntoSupplierFromApi()

            itemSet.addAll(getItemFromContainter(item, quantity, price))


        } else {
            //find data into current memory
            itemSet.addAll(getItemFromContainter(item, quantity, price))

            //reinitialize data from API
            initializeDataIntoSupplierFromApi()

            //once again finding data into current memory
            itemSet.addAll(getItemFromContainter(item, quantity, price))


        }
        if (itemSet.size == 0) {
            itemSet.add("NOT_FOUND")
        }
        var lst = ArrayList<Any>()
        lst.addAll(itemSet)



        return lst

    }

    private fun initializeDataIntoSupplierFromApi() {
        // Call Fruit Supplier API
        val fruitResponse = restTemplate.getForEntity("https://run.mocky.io/v3/c51441de-5c1a-4dc2-a44e-aab4f619926b", Array<Fruit>::class.java)
        val fruits = fruitResponse.body
        if (fruits != null)
            for (i in fruits) {
                fruitsContainer.add(i)
            }


        // Call Vegetable Supplier API
        val vegetableResponse = restTemplate.getForEntity("https://run.mocky.io/v3/4ec58fbc-e9e5-4ace-9ff0-4e893ef9663c", Array<Vegetable>::class.java)
        val vegetables = vegetableResponse.body
        if (vegetables != null)
            for (i in vegetables) {
                vegetablesContainer.add(i)
            }

        // Call Vegetable Supplier API
        val grainResponse = restTemplate.getForEntity("https://run.mocky.io/v3/e6c77e5c-aec9-403f-821b-e14114220148", Array<Grain>::class.java)
        val grains = grainResponse.body
        if (grains != null)
            for (i in grains) {
                grainsContainer.add(i)
            }
    }

    private fun getItemFromContainter(item: String, quantity: Int, price: Double): List<Any> {
        val itemList = ArrayList<Any>()
        for (fruit in fruitsContainer) {
            if (fruit.name.equals(item, true) && fruit.quantity >= quantity && fruit.price.substring(1).toDouble() >= price) {
                itemList.add(fruit)
            }

        }
        for (veg in vegetablesContainer) {

            if (veg.productName.equals(item, true) && veg.quantity >= quantity && veg.price.substring(1).toDouble() >= price) {
                itemList.add(veg)
            }

        }
        for (grain in grainsContainer) {
            if (grain.itemName.equals(item, true) && grain.quantity <= quantity && grain.price.substring(1).toDouble() <= price) {
                itemList.add(grain)
            }
        }
        return itemList

    }

    fun getFastBuyItems(name: String): List<Any> {

        var itemList = ArrayList<Any>()

        var fruit = getFruitsItem(name)
        var veg = getVegetablesItem(name)
        var grain = getGrainsItem(name)

        itemList.addAll(fruit.get())
        itemList.addAll(veg.get())
        itemList.addAll(grain.get())
        println(itemList)

        return itemList
    }

    @Async
    private fun getFruitsItem(name: String): CompletableFuture<List<Any>> {
        val itemList = ArrayList<Any>()

        val fruitResponse = restTemplate.getForEntity("https://run.mocky.io/v3/c51441de-5c1a-4dc2-a44e-aab4f619926b", Array<Fruit>::class.java)

        //itemList.addAll(listOf(Arrays.asList(response1.getBody())))
        val fruits = fruitResponse.body

        if (fruits != null)
            for (item in fruits) {
                if (item.name.equals(name, true)) {
                    itemList.add(item)
                }
            }
        //return itemList
        return CompletableFuture.completedFuture(itemList)
    }


    @Async
    private fun getVegetablesItem(name: String): CompletableFuture<List<Any>> {
        val itemList = ArrayList<Any>()

        val vegetableResponse = restTemplate.getForEntity("https://run.mocky.io/v3/4ec58fbc-e9e5-4ace-9ff0-4e893ef9663c", Array<Vegetable>::class.java)

        // itemList.addAll(listOf(Arrays.asList(response2.getBody())))
        val vegetables = vegetableResponse.body


        if (vegetables != null)
            for (item in vegetables) {
                if (item.productName.equals(name, true)) {
                    itemList.add(item)
                }
            }

        return CompletableFuture.completedFuture(itemList)
    }

    @Async
    private fun getGrainsItem(name: String): CompletableFuture<List<Any>> {
        val itemList = ArrayList<Any>()

        val grainResponse = restTemplate.getForEntity("https://run.mocky.io/v3/e6c77e5c-aec9-403f-821b-e14114220148", Array<Grain>::class.java)

        // itemList.addAll(listOf(Arrays.asList(response3.getBody())))
        val grains = grainResponse.body
        // println(fruits)

        if (grains != null)
            for (item in grains) {
                if (item.itemName.equals(name, true)) {
                    itemList.add(item)
                }
            }
        return CompletableFuture.completedFuture(itemList)
    }


}

