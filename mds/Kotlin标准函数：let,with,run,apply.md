# Kotlin标准函数 let，with，run，apply

> Standard.kt文件中定义了一些函数，任何kt代码都可以自由的调用，故这些函数被称为标准函数。



# 本节目的

> 最近在开发中经常见到这些函数的使用，发现使用这些标准函数后代码可以更加精简，真的非常nice本着查漏补缺的想法这里就总结下。本节主要会做如下几件事：

- 基础：几个标准函数的学习总结。
- 提升：挑取一个函数设计探究。

# 几个高频标准函数

###### 1、let

（1）语法

```java
// 语法(student为任意对象，任意对象都有这个let方法)
student.let{
    //todo code.
}                
```

> lambda这块不熟悉可以参考：[高阶函数.md](https://github.com/sunnnydaydev/LearnKotlin/blob/master/mds/18%E3%80%81%E9%AB%98%E9%98%B6%E5%87%BD%E6%95%B0.md)

（2）作用

- 配合?.操作符在空指针检测时起到很大作用
- 执行lambda表达式，let无返回值。（虽lambda表达式函数体中最后一行表达式会作为lambda的返回值，但是let不会使用这个返回值）
- 可在函数体内使用it替代类的对象去访问其公有的属性和方法 。

有如下代码：

```java
/**
 * Create by SunnyDay on 20:20 2022/01/01
 */
class Student {

    fun doHomework(){}

    fun readBook(){}

}

// 测试：
fun study1(student: Student?) {
    student?.doHomework()
    student?.readBook()
}
```

kt中的?.可使用if语句替换。对study1中的代码进行“精确”替换：

```java
fun study2(student: Student?) {

    if (student != null) {
        student.doHomework()
    }
    if (student != null) {
        student.readBook()
    }
}
```

上述的代码太重复，我们进行优化：

```java
fun study3(student: Student?) {
    // 使用if 语句对study2 进行优化
    if (student != null) {
        student.doHomework()
        student.readBook()
    }
    // 使用let函数对study2进行优化

    student?.let {
        it.doHomework()
        it.readBook()
    }
}
```

emmm，优化后的两种方式看着都比较简洁呀，为啥还多次一举搞个let，直接使用if语句多好？？？？其实这里只是针对局部变量的写法，假如针对成员变量这时就体现出let的强大了。

我们看这个例子：

```java
class Student(private var name:String?) {

    fun doHomework(){}

    fun readBook(){}
    
    fun changeName() {
        /**
         * name 使用都会有警告：
         * 
         * Smart cast to 'String' is impossible,
         * because 'name' is a mutable property 
         * that could have been changed by this time.
         */
        if (name != null) {
            val code = name.substring(0)
            val length = name.length
        }
    }

```

if的弊端就出来了，对成员变量进行判空后，操作成员变量时，其实成员变量上还是会有编译问题的。

kotlin的空指针避免工作做的是十分完善的，kotlin认为，kotlin认为，kotlin认为，多线程中，name还是有可能被其他线程修改的风险，就有可能会出现空指针。所以kotlin中想使用上述代码仅仅使用if 判断是不行的还需要“依次”进行非空断言：

```java
    fun changeName() {
        if (name != null) {
            val code = name!!.substring(0)
            val length = name!!.length
        }
    }
```

这时我们看看let的优势：

```java
    fun changeName() {
        name?.let {
            // 不需要依次非空断言
            val code = it.substring(0)
            val length = it.length
        }
    }
```

###### 2、with

> 其实 with、run、apply的功能差不多。

（1）语法

```java
   val  result = with(obj){// obj 为任意对象
         // lambda表达式无参数，lambda表达式方法体中具有obj上下文环境
         // todo code
        //lambda方法体，最后一行代码。作为with的返回值。
   } 
```

> whth 需要两参数：
>
> 参数1：任意对象
>
> 参数2：lambda表达式，lambda中提供对象上下文环境。

（2）作用

- 连续调用同一个对象的多个方法时让代码变得更加简洁。

​    有如下字符串拼接栗子：

```java
    val sb = StringBuilder()
    sb.append("Http")
    sb.append(":")
    sb.append("//")
    sb.append("www.baidu.com")
    val result = sb.toString()
```

可见每次都需要使用sb对象调用（这里由于StringBuilder特殊提供了build链式调用我们也可以直接链式调用）对象的方法优点繁琐，若使用with则代码如下：

```java
   val result = with(StringBuilder()){
       append("Http")
       append(":")
       append("//")
       append("www.baidu.com")
       toString()
   }
```

###### 3、run

> 其实 with、run、apply的功能差不多。run方法的用法和使用场景其实和with非常类似。只是稍微做了语法的改变：
>
> 首先：run方法不能直接调用，run函数属于对象所有，需要被对象调用
>
> 其次：run方法需要个lambda，lambda表达式中提供对象上下文环境。

（1）语法

```java
    val result = obj.run {
          // lambda表达式无参数，lambda表达式方法体中具有obj上下文环境
         // todo code
        //lambda方法体，最后一行代码。作为with的返回值。
    }
```

with的栗子我们改写成run：

```java
    val result = StringBuilder().run {
        append("Http")
        append(":")
        append("//")
        append("www.baidu.com")
        toString()
    }
```

###### 4、apply

> 其实 with、run、apply的功能差不多。只是apply在返回值上做了改动，apply返回对象本身。

（1）语法

```java
    val result = obj.apply {
          // lambda表达式无参数，lambda表达式方法体中具有obj上下文环境
         // todo code
        //apply 返回值为调用apply对象本身。
    }

```



```java
    val result = StringBuilder().apply {
        append("Http")
        append(":")
        append("//")
        append("www.baidu.com")
    }
    val str = result.toString()
```



###### 5、小结

|              |                            let                            |                           with                           | run                                                      | apply                                       |
| :----------: | :-------------------------------------------------------: | :------------------------------------------------------: | -------------------------------------------------------- | ------------------------------------------- |
|   方法使用   |                         对象调用                          |                          类调用                          | 对象调用                                                 | 对象调用                                    |
|   方法参数   |                       lambda表达式                        |          参数1，任意对象。参数2，Lambda表达式。          | lambda表达式                                             | lambda表达式                                |
| lambda表达式 | lambda表达式有一个参数，参数类型为let对象调用者所属类型。 |          lambda表达式中持有，对象的上下文环境。          | lambda表达式中持有，对象的上下文环境。                   | lambda表达式中持有，对象的上下文环境。      |
|  方法返回值  |                        无（Unit）                         | lambda表达式的最后一行代码。（返回值类型由这行代码决定） | lambda表达式的最后一行代码。（返回值类型由这行代码决定） | apply对象调用者所属类型。（返回值类型固定） |



# 挑取一个函数设计探究



###### 1、run 源码

```java
@kotlin.internal.InlineOnly
public inline fun <T, R> T.run(block: T.() -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block()
}
```

> 设计十分简单：
>
> 1、inline，定义函数为内联函数这里可以不用管，不影响run业务。
>
> 2、contract，kt1.3新增新特性 契约。不影响run业务。
>
> 3、看下，首先定义了泛型参数T，R。run方法定义为T的扩展函数。run方法返回值为R
>
> 4、再看下run方法参数，这里接受函数类型的参数，函数类型为T.() T的扩展函数类型，扩展函数类型返回值为R
>
> 5、再看下run返回值为block函数， block 本身就是 T.() 扩展函数，且扩展函数返回值也是R。所以block（）返回值为R

###### 2、自定义

```java
/**
 * Create by SunnyDay on 10:29 2022/01/03
 */

// 
fun <T,R>T.customLet(block: T.()->R):R{
    return block()
}

fun main(){
    // 测试自定义
    val str = "name"

    str.customLet {
        println(this)
    }
}
```

> 总结：emmmm, 和源码一样啊，，，，，，没关系功能几乎实现了，契约那一块还不熟悉以后再查漏补缺下~