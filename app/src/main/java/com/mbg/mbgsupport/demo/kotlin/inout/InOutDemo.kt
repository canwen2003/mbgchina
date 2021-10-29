package com.mbg.mbgsupport.demo.kotlin.inout


//协变
interface Production<out T> {
    fun produce(): T
}

//逆变
interface Consumer<in T> {
    fun consume(item: T)
}

class InOutDemo {

    fun main() {
        val production1: Production<Food> = FoodStore()
        production1.produce()
        val production2: Production<Food> = FastFoodStore()
        production2.produce()
        val production3: Production<Food> = InOutBurger()
        production3.produce()
        val production4: Production<Food> = object : Production<Burger> {
            override fun produce(): Burger {
                println("Produce Burger")
                return Burger()
            }
        }
        production4.produce()

       val consumer1:Consumer<Burger> =Everybody()
        val consumer2:Consumer<Burger> =ModernPeople()
        val consumer3:Consumer<Burger> =American()
    }
}

open class Food
open class FastFood : Food()
class Burger : FastFood()

class FoodStore : Production<Food> {
    override fun produce(): Food {
        println("Produce food")
        return Food()
    }
}

class FastFoodStore : Production<FastFood> {
    override fun produce(): FastFood {
        println("Produce food")
        return FastFood()
    }
}

class InOutBurger : Production<Burger> {
    override fun produce(): Burger {
        println("Produce burger")
        return Burger()
    }
}

class Everybody : Consumer<Food> {
    override fun consume(item: Food) {
        println("Eat food")
    }
}

class ModernPeople : Consumer<FastFood> {
    override fun consume(item: FastFood) {
        println("Eat fast food")
    }
}

class American : Consumer<Burger> {
    override fun consume(item: Burger) {
        println("Eat burger")
    }
}

