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