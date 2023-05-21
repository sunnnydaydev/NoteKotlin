package base

/**
 * Create by SunnyDay 2023/04/26 20:54:41
 **/

fun main() {
    val transformResult1 = transform{ 3 }
    println(transformResult1)
}

/**
 *把任意一个函数返回值转化为String输出
 * */
fun transform(methodAction: () -> Any): String {
    return methodAction.invoke().toString()
}

class Animal {
    override fun toString() = "i am Animal"
}