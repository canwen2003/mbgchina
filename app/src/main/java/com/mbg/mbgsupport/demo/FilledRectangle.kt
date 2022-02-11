package com.mbg.mbgsupport.demo

class FilledRectangle: Rectangle() {
    init { println("Initializing a FilledRectangle class") }
    override fun draw() {
        val filler = Filler()
        filler.drawAndFill()
    }
    inner class Filler {
        fun fill() { println("Filling") }
        fun drawAndFill() {
            super@FilledRectangle.draw() // Calls Rectangle's implementation of draw()
            fill()
            println("Drawn a filled rectangle with color ${super@FilledRectangle.borderColor}")
            // Uses Rectangle's implementation of borderColor's get()

            val person1 = Person("John","men")
            val person2 = Person("John","men")
            val person3 = Person("John","women")
            person1.age = 10
            person2.age = 20

            println("person1 == person2: ${person1 == person2}")
            println("person1 == person3: ${person1 == person3}")
            println("person1 with age ${person1.age}: ${person1}")
            println("person2 with age ${person2.age}: ${person2}")
        }
    }
}