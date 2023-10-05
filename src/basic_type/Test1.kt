package basic_type


/**
 * Create by SunnyDay 2023/10/03 17:00:11
 **/
fun main() {
    val list = arrayListOf(1, 3, 5, 7, 9)

    run runBlock@{
        list.forEach(fun(value: Int) {
            println("loop:${value}")
            if (value == 7)
                return@runBlock
        })
    }
    print("done with nested loop")
}