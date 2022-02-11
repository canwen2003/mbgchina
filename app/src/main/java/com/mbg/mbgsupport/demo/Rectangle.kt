package com.mbg.mbgsupport.demo

open class Rectangle {
    init { println("Initializing a Rectangle class") }
    open fun draw() { println("Drawing a rectangle") }
    val borderColor: String get() = "black"
}