package basic_type


/**
 * Create by SunnyDay 2023/10/03 17:00:11
 **/
fun main() {
    val list = arrayListOf(1,3,5,7,9)
    list.forEach {
        println("loop:${it}")
        if (it==7)return
    }
}