package com.tymeglobal.test.question_2_1

import java.text.DecimalFormat
import kotlin.collections.ArrayList

class Product(val name: String, val price: Double, val quantity: Int)

fun calculateTotalInventoryValue(inventory: List<Product>): Double {
    return inventory.sumOf { it.price * it.quantity }
}

fun findMostExpensiveProduct(inventory: List<Product>): Product? {
    return inventory.maxByOrNull { it.price }
}

fun checkProductInStock(inventory: List<Product>, productName: String): Boolean {
    return inventory.any { it.name == productName }
}

fun sortProductsByPrice(inventory: List<Product>): List<Product> {
    return inventory.sortedByDescending { it.price }
}

fun sortProductsByQuantity(inventory: List<Product>): List<Product> {
    return inventory.sortedBy { it.quantity }
}

fun Double.formatCurrency(): String {
    val format = DecimalFormat("#,###.00")
    return format.format(this)
}

fun printInventory(inventory: List<Product>) {
    // Calculate the maximum width for each column
    val nameColumnWidth = maxOf("Name".length, inventory.maxOf { it.name.length }) + 2
    val priceColumnWidth =
        maxOf("Price".length, inventory.maxOf { it.price.formatCurrency().length }) + 2
    val quantityColumnWidth =
        maxOf("Quantity".length, inventory.maxOf { it.quantity.toString().length }) + 2

    val totalWidth = nameColumnWidth + priceColumnWidth + quantityColumnWidth
    val headerTitle = "Inventory"
    val titleWidthExcludeTitle = totalWidth - headerTitle.length
    val sideWidth = (titleWidthExcludeTitle - 2) / 2
    val expectedTitleWidth = sideWidth + headerTitle.length + 2 + sideWidth

    // Print the table header
    println("=".repeat(sideWidth) + " $headerTitle " + "=".repeat(sideWidth + (totalWidth - expectedTitleWidth)))
    println(
        "Name".padEnd(nameColumnWidth) + "Price".padStart(priceColumnWidth) + "Quantity".padStart(
            quantityColumnWidth
        )
    )
    println("-".repeat(nameColumnWidth + priceColumnWidth + quantityColumnWidth))

    for (product in inventory) {
        val name = product.name.padEnd(nameColumnWidth)
        val price = product.price.formatCurrency().padStart(priceColumnWidth)
        val quantity = product.quantity.toString().padStart(quantityColumnWidth)
        println(name + price + quantity)
    }

    println("=".repeat(nameColumnWidth + priceColumnWidth + quantityColumnWidth))
}

fun main() {
    //Change this inventory to test different cases
    val inventory = ArrayList<Product>()
    inventory.add(Product("Laptop", 999.99, 5))
    inventory.add(Product("Smartphone", 499.99, 10))
    inventory.add(Product("Tablet", 299.99, 0))
    inventory.add(Product("Smartwatch", 199.99, 3))

    println("=========================Question 2.1============================")
    printInventory(inventory)

    // Calculate the total inventory value
    val totalInventoryValue = calculateTotalInventoryValue(inventory).formatCurrency()
    println("Total inventory value: $totalInventoryValue")

    // Find the most expensive product
    val mostExpensiveProduct = findMostExpensiveProduct(inventory)
    println("Most expensive product: ${mostExpensiveProduct?.name}")

    // Check if a product named "Headphones" is in stock
    val headphonesInStock = checkProductInStock(inventory, "Headphones")
    println("Headphones in stock: $headphonesInStock")

    // Sort products in descending order by price
    val sortedByPriceDescending = sortProductsByPrice(inventory)
    println("Products sorted by price descending: ${sortedByPriceDescending.joinToString { it.name }}")

    // Sort products in ascending order by quantity
    val sortedByQuantityAscending = sortProductsByQuantity(inventory)
    println("Products sorted by quantity ascending: ${sortedByQuantityAscending.joinToString { it.name }}")
    println("=================================================================")
}