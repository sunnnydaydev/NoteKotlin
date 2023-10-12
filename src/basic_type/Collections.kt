package basic_type

/**
 * Create by SunnyDay 2023/10/12 20:37:40
 **/
fun main() {
    val listOf1 = listOf<String>()
    val listOf2 = listOf("1", "2")

    val set = setOf("1", "2")
    val mutableListOf = mutableListOf("1", "2")
    val mutableSetOf = mutableSetOf("1", "2")

    val pair1 = Pair(first = "name", second = "Carry")
    val pair2 = Pair(first = "name", second = "Carry")
    val mapOf1 = mapOf(pair1, pair2)
    val mapOf2 = mapOf("name" to "Carry")
    val mutableMapOf = mutableMapOf("name" to "Carry")
    val mutableMapOf1 = mutableMapOf<String, String>().apply {
        this["name"] = "Carry"
    }

    val emptyList = emptyList<String>()
    val emptySet = emptySet<String>()
    val emptyMap = emptyMap<String, String>()

    val arrayList = arrayListOf("1","2")
    val arrayList2 = arrayListOf<Int>() // empty arrayList

    val newList = listOf1.toList()

    println("listOf1:${listOf1.hashCode()}")
    println("newList:${newList.hashCode()}")
    println("listOf2:${listOf2.hashCode()}")

}