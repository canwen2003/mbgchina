package com.mbg.mbgsupport.demo.thisLabel
 class ThisDemo {
    //添加 inner 类似于java的内部类，可以访问外部类的对象，如果去掉inner，就相当于java的静态外部类
    inner class InnerClass { // implicit label @B

          fun Int.foo() { // implicit label @foo
            val a = this@ThisDemo // ThisDemo's this
            val b = this@InnerClass // InnerClass's this

            val c = this // foo()'s receiver, an Int
            val c1 = this@foo // foo()'s receiver, an Int

            val funLit = lambda@ fun String.() {
                val d = this // funLit's receiver
            }

            val funLit2 = { s: String ->
                // foo()'s receiver, since enclosing lambda expression
                // doesn't have any receiver
                val d1 = this
            }

            println("a=$a")
            println("b=$b")
            println("c=$c")
            println("c1=$c1")
            println("funLit=$funLit")
            println("funLit2=$funLit2")
        }

        fun test(){
            val intData=3
            intData.foo()
        }
    }
}