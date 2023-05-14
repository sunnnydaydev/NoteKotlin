package abs

/**
 * Create by SunnyDay 2023/05/11 20:37:43
 **/

interface Animal {
    //默认为abstract，子类实现接口时需要实现这个字段
    val desc: String

    //访问器实现.注意 val age: Int = 18 是不允许的
    val age: Int
        get() = 18

}

class Dog : Animal {
    override val desc: String = "i am a dog"
}

interface A {
    fun foo() {
        print("A")
    }
}

interface B {
    fun foo() {
        print("B")
    }
}

class D : A, B {
    override fun foo() {
        println("D")
        super<A>.foo()
        super<B>.foo()
    }
}

open class Test(age: Int, name: String) {
    private val age: Int
    private val name: String

    init {
        this.age = age
        this.name = name
    }
}

class LengthCount {
    //注意val类型的变量自定义get时是不允许初始化的，如 val counter: Int = 0这样写法编译器报错
    var counter:Int = 0
        get() = 10
}

fun main() {
    val lengthCount = LengthCount()
    lengthCount.counter = 20
    println("counter:${lengthCount.counter}")
}