package basic_type

/**
 * Create by SunnyDay 2023/05/08 22:59:21
 **/
fun main() {
    val array2 = Array(5) { 6 }//lambda 表达式的最后一个值为数组每个元素的初始值
    for (i in 0..4) {
        println(array2[i])
    }
    //6
    //6
    //6
    //6
    //6
}