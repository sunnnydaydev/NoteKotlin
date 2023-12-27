### 一、高阶函数

> kotlin中除了Int、Number、String等常见的数据类型之外又加入了一种新的数据类型即函数类型。kotlin中每个函数都有特定的函数类型。

###### 1、kotlin的函数

kotlin的函数看着才像标准的"输入输出"函数，因为输入在前输出在后，看个普通函数的🌰

```kotlin
fun sum(num1: Int, num2: Int): Int {
    return num1 + num2
}
```

如上,函数的参数就是输入，返回值类型就是输出。参数在前返回值在后这就是标准的输入输出思想。

为啥先讲下这个呢？因为我感觉了解了标准输入输出思想有助于我们对函数类型的理解~

###### 2、函数类型🌰

```kotlin
fun getString(n: Int): Int {}
```

如上就是一个普通的kotlin方法，其函数类型为（Int）->Int。我们可以把函数类型看做与Int、String等同等级的一种新的类型。函数类型的语法表示如下：

```kotlin
(参数类型)->返回值类型
```

可以看到高阶函数也是标准的输入输出函数:小括号内就是"输入",返回值类型就是"输出"。

因此针对任意一个方法我们都能说出其函数类型：

```kotlin
   //(String)->Unit
fun test1(name: String) {}

//(String)->String
fun test2(name: String): String {
    return ""
}

//(String,Int)->Long
fun test3(name: String, age: Int): Long {
    return 3L
}

//(String,List<String>)->Int
fun test4(name: String, list: List<String>): Int {
    return 3L
}
```

###### 3、函数类型的作用

既然函数类型与String、Int、Byte等类型并列为kotlin中的数据类型，那么函数类型同样具备如下功能：

- 作为变量使用（成员或局部）
- 作为形参使用
- 作为方法返回值使用

###### 4、函数类型的定义赋值使用

（1）定义

函数类型变量的定义也是类似标准的"输入输出"方式定义，看个🌰

```kotlin
fun main() {
    val methodAction: () -> String
}
```

先看下这段代码代表的意思：定义个函数类型的变量methodAction。methodAction就是变量名，变量类型为() -> String

（2）赋值

常见的赋值方式有两种，一种是先定义变量，然后对变量进行赋值：

```kotlin
val methodAction: () -> String
    methodAction = {
    "返回值" 
}
```

另一种是定义变量时直接赋值：

```kotlin
val methodAction: () -> String = {
    "返回值"
}
```

这个感觉和kt的这种方法类似:右面的结果就是左面类型返回值

```kotlin
//完整写法fun sum(num1: Int, num2: Int):Int = num1 + num2
fun sum(num1: Int, num2: Int) = num1 + num2
```

其实右面的{} 还有个名字叫lambda表达式（无函数名的高阶函数），就是没有名字的高阶函数。常见的有如下几种：

```kotlin
//1、无参数，返回值类型为Unit的匿名函数
{}
//2、无参数，返回值类型为String的匿名函数
{
    "我是返回值"
}
//3、有参数，返回值类型为String的匿名函数
{ name:String ->
    "我是返回值:$name"
}
```
- {}的函数类型为 ()->Unit
- {"我是返回值"}的函数类型为 ()->String
- { name:String ->"我是返回值:$name"}的函数类型为 (String)->String

到这里应该明白🌰栗子中高阶函数左右的对等关系了吧，右面的表达式的类型和左边methodAction👈🏻所属的类型一致。

```kotlin
    val methodAction: () -> String = { "返回值" }
```

（3）使用

使用就很简单啦，常见的有两种调用方式

```kotlin
    val methodAction: () -> String = {
      "返回值"
    }
    //两种使用高阶函数方式:
    methodAction.invoke()
    methodAction()
```

###### 5、高阶函数的应用

（1）栗子🌰

```kotlin
class MainActivity {
    //模拟onCreate
    fun onCreate() {
        ItemAdapter(::onViewClick)
    }

    private fun onViewClick(id:Int){
        println("你点击了id")
    }
}

class ItemAdapter(private val onItemClick: (id: Int) -> Unit = {}) {
    //模拟View渲染完成
    fun onViewUpdate(id: Int) {
        onItemClick.invoke(id)//或者onItemClick(id)
    }
}
```
一个Android中常见的例子，RecyclerView的点击事件回调，使用高阶函数完全可以做到，代替了接口回调。上面有几点需要注意:

- val onItemClick: (id: Int) -> Unit = {} 这里{}中隐含个变量it,完整写法是这样的val onItemClick: (id: Int) -> Unit = {it->}
假如等号左边的高阶函数只有一个参数，则lambda表达式默认参数为it而且还可省略不写。
- lambda表达是是有返回值的，返回值默认为表达式的最后一行语句
- 注意这里有一个新的操作符::这个操作符带上函数名就代表把普通函数当做其函数类型引用

(2)栗子🌰

还是上述例子，不用::还有种写法

```kotlin
class MainActivity {
    fun onCreate() {
        ItemAdapter().apply {
            onItemClick = {
                println("你点击了$it")
            }
        }
    }
}

class ItemAdapter {
    var onItemClick: (id: Int) -> Unit = {}
    fun onViewUpdate(id: Int) {
        onItemClick.invoke(id)
    }
}
```

（3）栗子🌰

 定义个高阶函数，根据传入的函数类型决定求两数之和还是两数之差

```kotlin

fun test(num1: Int, num2: Int, fun1: (Int, Int) -> Int) {
    val result = fun1(num1, num2) 
    println(result)
}

//求和普通函数
fun plus(num1: Int, num2: Int): Int {
    return num1 + num2
}
//求差普通函数
fun minus(num1: Int, num2: Int): Int {
    return num1 - num2
}
```

```kotlin
fun main() {
    // 这里第3个参数传递其他函数时，结果可能不同:
    println(test(10, 5, ::plus))//15
    println(test(10, 5, ::min))//5
}
```

# 二、匿名高阶函数lambda表达式

Lambda 本质也是高阶函数，是一种没有名字的高阶函数。它同样可以作为表达式、形参、和函数返回值使用。语法如下:

```kotlin
{参数:参数类型-> Lambda表达式体}
```

###### 1、lambda有如下特点

(1)Lambda 表达式作为一个方法，不需要 fun 关键字，也无须指定方法名

(2)lambda函数体放在 -> 右面

(3)lambda的最后一行"表达式"默认为 Lambda 的返回值，且无须 return 关键字

```kotlin
fun main() {
   val test = { name:String,age:Int ->
       "i am return:[name $name, age $age] "
    }
    println(test.invoke("Carry",18))
}

//log:
//i am return:[name Carry, age 18]
```

(4)如果lambda表达式有形参列表，参数及其参数类型要放在 -> 左面

```kotlin
fun main() {
    // 直接定义一个匿名函数
    { name:String,age:Int ->
        
    }
}
```
(5)如果lambda表达式有形参列表，且等号左面对应有定义的函数类型时，形参列表可省略，类型会自推断

```kotlin
fun main() {
   val test:(String,Int) ->String = { name,age ->
       "i am return:[name $name, age $age] "
    }
    println(test.invoke("Carry",18))
}
```

(6)如果lambda表达式有且仅有一个参数，且等号左面对应有定义的函数类型时，这个参数可省略，默认为it，类型自推断。

```kotlin
fun main() {
   val test:(String) ->String = {
       "i am return:[name $it] "
    }
    println(test.invoke("Carry"))
}
```
此时lambda体内有个it类型变量，it所属的类型就是前面函数类型小括号中定义的参数类型

###### 2、kotlin中的lambda规定

（1）当lambda表达式是函数的最后一个参数时，可将lambda表达式移动到括号外面

（2）如果lambda表达式是函数的“唯一”参数，还可以将函数的括号省略。

（3）当“lambda表达式”的参数列表中只有一个参数时，也不必声明参数名，可以使用it关键字代替。

```kotlin
fun main() {

    val method1: () -> Int = { 3 }
    val transformResult1 = transform(method1)
    println(transformResult1)

    val method2: () -> Animal = { Animal() }
    val transformResult2 = transform(method2)
    println(transformResult2)
    
    //log
    //3
    //i am Animal
}

/**
 *把任意一个函数返回值转化为String输出
 * */
fun transform(methodAction: () -> Any): String {
    return methodAction.invoke().toString()
}

class Animal {
    override fun toString() = "i am Animal"
}
```

如上🌰为了方便理解transform函数的功能我写了两个变量测试了下，其实我们还可以定义其他类型的去练习下加深理解~  我们就以transformResult1为例子
来讲解下吧，其实我们熟练高阶函数后method1这个变量就没必要写了，而是直接省略：

```kotlin
fun main() {
    val transformResult1 = transform({ 3 })//这里其实编译器回报⚠️警告的，我们先不管。
    println(transformResult1)
}
```

接下来就是规则的应用嘞，当lambda表达式是函数的最后一个参数时，可将lambda表达式移动到括号外面->

```kotlin
fun main() {
    val transformResult1 = transform(){ 3 }//这里你会看到小括号变灰了（idea上）,我们先不管。
    println(transformResult1)
}
```
如果lambda表达式是函数的“唯一”参数，还可以将函数的括号省略->

```kotlin
fun main() {
    val transformResult1 = transform{ 3 }
    println(transformResult1)
}
```

看看是不是简洁多啦~  至于最后一条规则it我这就不举例了，前面的🌰已经有啦，偷下懒~

偷偷告诉你，其实在上述黄色警告那，Mac上只需option+enter编译器即可帮助我们快速转换一步到位~ 哈哈哈哈哈😝

# 三、趁热打铁let函数的源码实现

kotlin提供了let、run、apply、also等函数，实现原理都类似，离开不了高阶函数，我们就捡个let函数看下背后的实现原理

kotlin的任意对象都有个let函数，我们经常这样使用

```kotlin
obj.let {
    //todo code.
}

```
###### 1、let函数源码

```kotlin
public inline fun <T, R> T.let(block: (T) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(this)
}
```

源码我们给他分解inline不用管就是这样的

```kotlin
public  fun <T, R> T.let(block: (T) -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return block(this)
}
```
这里先看下泛型<T, R > 这里表示输入T类型，输出R类型

再看T.let 这里表示为任意T类对象定义一个扩展函数let

继续看let函数接受的参数为函数类型，返回值为R类型

中间的contract我们先不管 就变成了这样

```kotlin
public  fun <T, R> T.let(block: (T) -> R): R {
    return block(this)
}
```
到这里我们看下return直接执行了函数类型变量block，因为block函数需要T类型的变量，这里进行了扩展函数的扩展this就代表T对象，所以用this作为参数
传递。

block函数类型执行完返回值就是R正好符合let函数返回值

是不是很简单，豁然开朗了，会议下上面我们🌰中的transform函数是不是和这个类似~

###### 2、总结

let函数使用知识点如下

- 泛型
- 扩展函数
- 高阶函数

let函数特点
- 任意函数的对象都具有let函数
- let函数的返回值是lambda表达式的返回值（其实是执行block函数的返回值,而block函数通常简洁转化后就是lambda作为参数传递的）

```kotlin
fun main() {
    //3
    println("111".let {
        it.length
    })

    //i am Animal
    println(Animal().let {
        it.toString()
    })

    //base.Animal
    println(Animal().let {
        it.javaClass
    })

    //kotlin.Unit
    println(
        "111".let {

        }
    )

    //null
    println(
        "111".let {
            null
        }
    )
    
}

class Animal {
    override fun toString() = "i am Animal"
}
```

# The end

相关总结

[Kotlin高阶函数收获](./Kotlin高阶函数收获.md)

[Kotlin高阶函数的应用](./Kotlin高阶函数的应用.md)

[Kotlin高阶函数总结](./Kotlin高阶函数总结.md)







