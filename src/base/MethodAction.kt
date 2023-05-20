package base

import kotlin.math.min

/**
 * Create by SunnyDay 2023/05/20 21:22:34
 **/
class MethodAction {
}


class MainActivity {
    fun onCreate() {
       ItemAdapter().apply {
           onItemClick = {
               println("你点击了$it")
           }
       }
    }
}

class ItemAdapter {
     var onItemClick: (id: Int) -> Unit = {}
    fun onViewUpdate(id: Int) {
        onItemClick.invoke(id)
    }
}

fun main() {
    // 这里第3个参数传递其他函数时，结果可能不同:
    println(test(10, 5, ::plus))//15
    println(test(10, 5, ::min))//5
}

// 定义个高阶函数，根据传入的函数类型决定求两数之和还是两数之差
fun test(num1: Int, num2: Int, fun1: (Int, Int) -> Int) {
    val result = fun1(num1, num2)
    println(result)
}

fun plus(num1: Int, num2: Int): Int {
    return num1 + num2
}

fun minus(num1: Int, num2: Int): Int {
    return num1 - num2
}