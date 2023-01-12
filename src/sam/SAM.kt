package sam

interface Animal{
    fun run()
}
fun main() {
    // 方式1：普通创建方法
    val dog = object : Animal {
        override fun run() {
            println("dog run in land")
        }
    }
    dog.run()

    // 方式2：SAM转换

    val fish = Animal{
        println("")
    }

}