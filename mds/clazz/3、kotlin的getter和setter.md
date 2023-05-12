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

(1)可以改变其访问性

```kotlin
class LengthCount {
    var counter = 0
        private set //外部访问不了set
}

```
（2）自定义get


（2）自定义set

```kotlin
class LengthCount {
    var counter = 0
    set(value) {
        //field = value // setter的默认实现
        field = value+1  
    }
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

初始化时有三种方式：

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
