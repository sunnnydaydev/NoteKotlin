###### 1、 getter和setter基本用法

- kotlin为我们自动创建 getter 、setter方法。
- val类型的变量 为可读变量 我们能访问 getter方法不能访问setter方法
- var类型的变量 getter setter方法我们都能访问

```kotlin
/**
 * Create by SunnyDay on 2019/07/12
 */
class LengthCount {
    var counter = 0 // kotlin 会自动帮我们生层getter、setter字段
}

fun main(args: Array<String>){
    val lengthCount = LengthCount()
    lengthCount.counter      // 内部就是调用的get方法
    lengthCount.counter = 3 // 内部就是调用的set方法 
    
}

```

###### 自定义getter和setter

(1)可以改变其访问性

```kotlin
class LengthCount {
    var counter = 0
        private set //外部访问不了set
}

```
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
