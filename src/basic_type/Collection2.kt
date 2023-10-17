package basic_type

/**
 * Create by SunnyDay 2023/10/12 21:04:31
 **/
fun main() {
    val mutableListOf = mutableListOf("init")
    val toList = mutableListOf.toMutableList()

    /**
     * 验证内存地址一致
     * listOf1:3237167
     * toList:3237167
     * */
    println("listOf1:${mutableListOf.hashCode()}")
    println("toList:${toList.hashCode()}")

    /**
     * 验证使用kt类库提供的api创建的副本，针对副本操作不会影响源集合
     * mutableListOf:init、mutableListOf:1
     * toList:init、toList:2
     * */
    mutableListOf.add("1")
    toList.add("2")
    mutableListOf.forEach {
        println("mutableListOf:${it}")
    }
    toList.forEach {
        println("toList:${it}")
    }

    /**
     * 验证原始的引用赋值方式创建的副本针对副本修改会影响源集合
     * mutableListOf1:init、mutableListOf1:1
     * */
    val mutableListOf1 = mutableListOf("init")
    val mutableListOf2 = mutableListOf1
    mutableListOf2.add("1")
    mutableListOf1.forEach{
        println("mutableListOf1:${it}")
    }

    val iterableList  =  listOf("1","2","3")
    val  iterator = iterableList.toMutableList().listIterator()
     iterator.next() //默认当前元素为首个元素，调用next后当前元素指向"2"
     iterator.add("test") // 此时集合为"1","test"，"2","3",当前元素还是指向"2"
     println("current:${iterator.next()}") //此时应打印"2",然后当前元素指向3


}