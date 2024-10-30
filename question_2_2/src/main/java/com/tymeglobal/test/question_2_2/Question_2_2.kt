package com.tymeglobal.test.question_2_2

fun findMissingNumber(numArray: IntArray): Int {
    val n = numArray.size
    val expectedSum =
        (n + 1) * (n + 2) / 2//Công thức này giúp chúng ta tính tổng của dãy số liên tiếp từ 1 đến n + 1
    val actualSum = numArray.sum()
    return expectedSum - actualSum
}

fun main() {
    val array = intArrayOf(3, 7, 1, 2, 6, 4)//Change this numArray to test different cases
    println("=========================Question 2.2============================")
    println("Array: [${array.joinToString()}]")
    println("n: ${array.size}")
    println("Missing number: ${findMissingNumber(array)}")
    println("=================================================================")
}