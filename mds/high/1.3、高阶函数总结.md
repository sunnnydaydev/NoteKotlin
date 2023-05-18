# 高阶函数总结

###### 1、kotlin的函数

kotlin的函数看着才像标准的"输入输出"函数，因为输入在前输出在后，看个普通函数的🌰

```kotlin
fun sum(num1:Int,num2:Int):Int{
   return num1+num2
}
```
如上方法函数的参数就是输入，返回值类型就是输出。参数在前返回值在后。

高阶函数定义也是类似的，标准的"输入输出"方式定义，看个🌰

```kotlin
fun main() {
    val methodAction: () -> String
}
```
先看下这段代码代表的意思：定义个函数类型的变量，函数无参数，返回值类型是String类型。可以看到高阶函数也是标准的输入输出函数:

小括号就是"输入",String就代表输出。

###### 2、对高阶函数的理解

（1） 先定义再赋值再使用

```kotlin
    // 1、高阶函数定义声明
    val methodAction: () -> String
    // 2、对高阶函数进行赋值
    methodAction = {
      "返回值"
    }
    //3、使用高阶函数
    methodAction.invoke()
    methodAction()
```
（2）定义时直接赋值

```kotlin
    val methodAction: () -> String = {
      "返回值"
    }
```

（3）带参数的高阶函数

```kotlin
    val methodAction: (Int,String) -> String = { age,name ->
      "返回值"
    }
```
可以看到函数类型中定义了两个参数类型，分别为Int类型，String类型。那么参数类型定义了参数定义在哪呢？后面的age，name就是对应的参数。

（4）一个参数时lambda中默认为it

```kotlin
    val methodAction: (String) -> String = {
        it.length
        "返回值"
    }
```

注意点
- 上文的{}其实就是lambda表达式，lambda是匿名高阶函数。
- lambda表达式的最后一行"表达式"就是其返回值


 




