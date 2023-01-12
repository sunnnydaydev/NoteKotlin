```kotlin

package myinterface

/**
 * Created by sunnyDay on 2019/7/12 14:13
 * 
 * kotlin中的关键字：
 * 1、 open public private protected（java默认为包私有kotlin中没有，kotlin默认为public）
 * 
 * 2、 java类方法 默认是开放的，子类默认可以继承重写父类，或者父类方法，kotlin的默认是final的
 * 
 * 3、final和open关键字的功能相反（open标注的就是可继承、可重写）
 * 
 * 4、子类中重写基类的方法或者成员后，子类内新的方法或者成员默认权限是 open 我们可以通过关键字修改
 *
 * 注意： 1、接口和接口中的方法默认是  开放的（open）继承后也是open
 *       2、抽象类同接口（抽象类，和抽象方法 默认 public open）
 *       3、抽象类中的非抽象函数并不是open 默认final
 */
class KeyWord : Button() { // 普通类默认public final 类型,这个类想要被继承则需要加open关键字

    fun getKeyWorld(): String = "我是关键字"  // 默认 方法public final

    override fun doClick() {} // 接口中的方法还是可以重写的

    override fun getButtonName(): String {
        return "重写啦 父类方法，父类的方法需要加open 关键字"
    }
}

interface Click {
    fun doClick()
    fun test(){
        println("我是Click-test()默认实现")
    }
}
```

- public ：kotlin默认（java不写任何修饰符默认为包访问权限）
- protected：  java中同一包中可以访问，kotlin中只能在同一类或者继承的子类中使用
- private： java 与kotlin使用一样
- 包访问权限：java 默认的，不写修饰符时默认为包访问权限。kotlin没有这个概念。
- internal：kotlin新增的关键字，模块访问权限。模块内部可见。一个模块就是一起一组编译的kotlin文件。

###### internal与 包访问权限 对比

- 对细节真正的隐藏
- 使用java时，外部代码可以将类定义在你的包中，从而访问你的包私有声明的权限。
- kotlin允许你在顶层声明使用private可见性，这些声明只在声明他们的文件中可见。


