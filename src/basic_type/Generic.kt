package basic_type

/**
 * Create by SunnyDay 2023/06/03 17:44:09
 **/
class Generic<T>(t:T)
class Dog

fun main(){
    val generic1 = Generic(1)
    val generic2 = Generic("1")
    val generic3 = Generic(Dog())
}