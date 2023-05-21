package base

/**
 * Create by SunnyDay 2023/04/26 20:54:41
 **/

fun main() {
    //3
    println("111".let {
        it.length
    })

    //i am Animal
    println(Animal().let {
        it.toString()
    })

    //base.Animal
    println(Animal().let {
        it.javaClass
    })

    //kotlin.Unit
    println(
        "111".let {

        }
    )

    //null
    println(
        "111".let {
            null
        }
    )

}

class Animal {
    override fun toString() = "i am Animal"
}