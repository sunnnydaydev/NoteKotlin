###### 1、 getter和setter基本用法

- kotlin为我们自动创建 getter 、setter方法。
- val类型的变量 为可读变量 我们能访问 getter方法不能访问setter方法
- var类型的变量 getter setter方法我们都能访问

```kotlin
/**
 * Create by SunnyDay on 2019/07/12
 */
class LengthCount {
    var counter = 0 // kotlin 会自动帮我们生成getter、setter字段
}

fun main(args: Array<String>){
    val lengthCount = LengthCount()
    lengthCount.counter      // 内部就是调用的get方法
    lengthCount.counter = 3 // 内部就是调用的set方法 
    
}

```

###### 2、自定义getter和setter

(1)自定义get

```kotlin
class LengthCount {
    //自定义get后，每次通过对象访问该字段时访问的是自定义get返回的值，如下🌰，counter的值永远是18
    var counter: Int = 0
        get() = 18
}

fun main() {
    val lengthCount = LengthCount()
    lengthCount.counter = 20
    println("counter:${lengthCount.counter}")
}
```

```kotlin
class LengthCount {
    //注意val类型的变量自定义get时是不允许初始化的，如 val counter: Int = 0这样写法编译器报错
    val counter: Int
        get() = 18
}
```

总结下，大致语法是这样的

```kotlin
// var 写法,字段必须定义时初始化
class LengthCount {
    var counter: Int = 0
        get() = 18
}
// val 写法，字段定义时不允许初始化
class LengthCount {
    val counter: Int
        get() = 18
}
```

(2)自定义set

```kotlin
class LengthCount {
    var counter = 0
    set(value) {
        //field = value // setter的默认实现
        field = value+1  
    }
}
```

(3)可以改变其访问性

```kotlin
class LengthCount {
    var counter = 0
        private set //外部访问不了set,相当于val功能
}

```
###### 3、成员变量的初始化

普通成员变量定义时为啥要求初始化？？？如下：

```kotlin
class Test{
    var age:Int //Property must be initialized or be abstract
    val name:String? //Property must be initialized or be abstract
}
```
如上我们定义好后会看到编译器报红提示，原因很简单 age中隐含默认 getter 和 setter，name 中隐含默认 getter。要想使用二者的值必须先初始化

初始化时有如下几种方式：

```kotlin

// 方式1:定义变量时进行初始化
open class Test {
    var age:Int = 18
    val name:String = ""
}

//方式2:init 快中初始化
open class Test {
    var age:Int
    val name:String 
    init {
        age = 18
        name = "carry"
    }
}
//方式3:主构造函数中定义 "成员" "成员" "成员" 变量，创建对象时自动初始化

open class Test(var age: Int, val name: String)

// 方式4：主构造函数中定义"局部"、"局部"、"局部"变量,需要在init块中自己手动完成初始化
open class Test( age: Int,  name: String){
    // 定义成员变量
    private val age:Int
    private val name:String
    // init 快中主动初始化
    init {
        this.age = age
        this.name= name
    }
}

```

###### 4、幕后字段field

field叫幕后字段，kt定义好的，只能在set、get块中使用。


###### 5、编译期常量

- 顶层属性
- object 关键字定义类内部的属性
- companion object 快内的属性
- 以 String 或原生类型值初始化

