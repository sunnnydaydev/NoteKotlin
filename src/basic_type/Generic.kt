package basic_type

/**
 * Create by SunnyDay 2023/06/03 17:44:09
 **/
class Generic<T>(private val t:T){
    fun printT(){
        println("t:${t}")
    }
}
class Dog

fun main(){
    val generic1 = Generic(1)
    val generic2 = Generic("1")
    val generic3 = Generic(Dog())

    val map = mapOf("name" to "Tom")

    val numbers = listOf("one", "two", "three", "four")
    println(numbers.associateWith { 1})
}
