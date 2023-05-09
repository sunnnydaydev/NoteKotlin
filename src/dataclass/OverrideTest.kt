package dataclass

/**
 * Create by SunnyDay 2023/05/09 21:18:29
 **/
open class Animal {
    open val age = 0

    // 被覆盖的成员需要显式加open关键字，否则报错
    open fun run() {

    }
}

open class Fish : Animal() {
    override val age = 20

    //1、覆盖的方法需要显式加override 关键字，否则编译期间报错。
    override fun run() {

    }
}

class Shark : Fish() {
    override fun run() {
    }
}

fun main() {

}

abstract class BaseActivity {
   abstract val resId:Int

    fun onCreate() {
        setContentView(resId)
    }

    private fun setContentView(resId: Int) {}
}

class MainActivity:BaseActivity(){
    override val resId: Int = R.layout.layout_main_activity
}

