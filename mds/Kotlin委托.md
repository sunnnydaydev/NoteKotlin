# 委托

委托顾名思义就是把要做的交付给别人去做，自己只负责接受请求，手下去干。典型的栗子就是老板接活员工去干！

首先定义个接口

```kotlin
/**
 * Created by SunnyDay on 2022/3/17 17:31:54
 */
interface DoWork {
    fun work(taskName: String)
}
```

工人

```kotlin
/**
 * Created by SunnyDay on 2022/3/17 17:33:42
 * 工人
 */
class Worker:DoWork {
    override fun work(taskName: String) {
        println("工人今天$taskName")
    }
}
```

老板

```kotlin
/**
 * Created by SunnyDay on 2022/3/17 17:32:46
 * 老板
 */
class Boss(private var worker: DoWork) : DoWork {
    override fun work(taskName: String) {
        worker.work(taskName)
    }
}
```

测试类

```kotlin
        val boss = Boss(Worker())
        boss.work("盖房子")
        boss.work("码头搬货物")
          
        log:
        I/System.out: 工人今天盖房子
        I/System.out: 工人今天码头搬货物
```



> 步骤小结：
>
> 1、委托者与被委托者实现相同接口
>
> 2、委托者持有被委托者引用
>
> 3、委托者在实现方法中进行委托。
>
> 这，，，，，和静态代理太像了。唯一的区别就是委托者在实现方法中只委托调用，未做自己的事情。



其实委托的好处就是可以分担代码量，吧某些具体的实现都抽象出来，让被委托的类来实现。

这样委托类就可以做很多事而不用担心代码都在自己类中，这些都不需要自己亲自去做~

# kotlin的类委托

”委托模式“是实现继承的一个很好的替代方式， 而 Kotlin 可以零样板代码地原生支持它。

###### 1、委托的基本实现

还是上述委托的栗子，我们只需简单修改下Boss类：

```kotlin
class Boss(worker: DoWork) : DoWork by worker{
 // 很好理解：
 // 1、使用关键字by代表委托
 // 2、在接口后面 by 接口实现类对象  
}
```

测试下调用

```kotlin
        val boss = Boss(Worker())
        boss.work("盖房子")
        boss.work("码头搬货物")
        
        log:
        I/System.out: 工人今天盖房子
        I/System.out: 工人今天码头搬货物
```

还是之前的代码写法？boss中没有接口实现方法也能使用？

事实正是如此，通过上述的写法后Boss类已经不需要实现DoWork接口的方法了，编译器编译成字节码时会默认实现这个方法，并转发给被委托的对象。接下来我们不妨可以看下被编译后的字节码：

```kotlin
//实现了接口,by只是语法糖，到字节码阶段已经去除。
public final class com/example/bitmapbasic/Boss implements com/example/bitmapbasic/DoWork{
  // 构造 
  public <init>(Lcom/example/bitmapbasic/DoWork;)V
  //持有接口实现类的引用
  private final synthetic Lcom/example/bitmapbasic/DoWork; $$delegate_0
  // 生成字节码时 自动生成接口中定义的方法  
  public work(Ljava/lang/String;)V
}
```



###### 2、委托与方法重写共存的case

有时候会出现”委托“与”方法重写“共存的case，这时调用时以方法重写为主~

还是上述栗子，我们修改代码,让委托与方法重写都存在时：可见调用的是重写的方法，而不是委托类对象内的实现方法。

```kotlin
//添加委托
class Boss(worker: DoWork) : DoWork by worker{
  // 重写方法
    override fun work(taskName: String) {
       println("测试~")
    }
}
```

```kotlin
        val boss = Boss(Worker())
        boss.work("盖房子")
        boss.work("码头搬货物")
        
        log:
        I/System.out: 测试~
        I/System.out: 测试~
```

###### 3、重写的成员和方法重写case一致

遵循优先使用“委托类”的成员原则。如下：

```kotlin
interface DoWork {
    //定义接口成员变量
    val msg:String
    fun work(taskName: String)
}
```



```kotlin
//实现接口成员
class Worker :DoWork {
    override val msg: String = "i am Worker"
    override fun work(taskName: String) {
        println("工人今天$taskName")
    }
}

class Boss(worker: DoWork) : DoWork by worker {
    // 当这行注释掉后，下面打印I/System.out: i am Worker
    override val msg: String = "i am boss"
    override fun work(taskName: String) {
        println("测试~")
    }
}
```

```kotlin
        //测试调用
        val boss = Boss(Worker())
        println(boss.msg)
        // 这里则会优先调用Boss内的字段打印：I/System.out: i am boss
```

# kotlin的属性委托

首先看下语法吧~ 看了好多文章都是吧官网的Demo搬来了，炸一看听懵逼的，这里就同样以官方栗子引入，栗子上解释时稍微平滑些吧~  

###### 1、自定义属性委托

```kotlin
语法：

val/var <属性名>: <类型> by <表达式>

注意：

1、在 by 后面的表达式是该 委托。

2、属性对应的 get、set会被委托给表达式的 getValue、setValue方法。

3、属性的委托不必实现任何的接口，但是需要提供一个 getValue函数与setValue。

```

上个栗子吧~炸一看可能会懵逼 😳

```java
/**
 * Created by SunnyDay on 2022/3/17 19:53:49
 */
class Example {
    // 定义个属性p，吧p委托给Delegate类对象。
    var p:String by Delegate()
}
```

```java
/**
 * Created by SunnyDay on 2022/3/17 19:54:34
 */
class Delegate {
    operator fun getValue(example: Example, property: KProperty<*>): String {
       return "$example-${property.name}"
    }

    operator fun setValue(example: Example, property: KProperty<*>, s: String) {
       println("$example-${property.name}-$s")
    }
}
```

可能令人懵逼的就是这个“表达式”中的实现方法了，没错这两个方法就是这样写的。在“表达式”中需要实现这量方法（val 类型的只需实现getValue即可）

所以我们目前只需明白operator fun getValue/setValue 固定写法即可。

接下来就是方法参数了，直接看参数多的setValue的吧~

- example：属性“所属类”的类型，类型可变的，其实这里可以Any，这样子就是官方栗子中的代码了。
- property：这个类中封装了属性的属性信息如、属性名字（name）、是否是final（isFinal）、是否是延迟初始化属性（isLateinit）
- s：属性“所属”的类型，这个也是可变的，与属性的定义有关。



###### 2、官方提供好的标准委托

- 延迟属性
- 可观察属性
- 将属性储存在映射中

(1)延迟属性lazy() 函数

```kotlin
public actual fun <T> lazy(initializer: () -> T): Lazy<T> = SynchronizedLazyImpl(initializer)
```



```kotlin
class MainActivity : AppCompatActivity() {
    //只能定义val 类型，lazy无setValue
    private val name:String by lazy {
        "Tom"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 第一次被调用时，初始化name值，后续不会再初始化，直接使用。
        println(name)
    }
}
```

可以看到很简单，lazy函数接收一个lambda表达式，并返回一个Lazy< T >实例的函数。返回的实例是具有延迟初始化的功能的，具体表现为第一次调用 `get()` 会执行已传递给 `lazy()` 的 lambda 表达式并记录结果， 后续调用 `get()` 只是返回记录的结果。

默认情况下，对于 lazy 属性的求值是“同步的”，“属性线程间可见的”。也即可认为求值加了同步锁的，并且属性加了volatile修饰的。

若是我们想修改可以在lazy方法中传参即可：

```kotlin
    private val name:String by lazy(LazyThreadSafetyMode.NONE) {
        "Tom"
    }
```

(2) 可观察属性 Observable

```kotlin
class MainActivity : AppCompatActivity() {
    
    private var name:String by Delegates.observable("Tom"){
        property, oldValue, newValue ->
        println("property name:${property.name} oldValue:$oldValue newValue:$newValue")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 每次重新赋值上述的lambda就会观察到
        name = "Kate"
        // I/System.out: property isLateinit?:name oldValue:Tom newValue:Kate
    }
}
```

属性值每次变化时，lambda表达式就会观察到。三个参数也很好理解：

- property：KProperty<*>类型参数。
- oldValue：为变化之前的值。
- newValue：变化后的值。

(3)将属性储存在映射中

kotlin提供委托，可以让我们在map中存储属性值。

```kotlin
/**
 * Created by Sunnyday on 2022/3/17 21:29:53
 */
class User(map: Map<String, Any>) {
    // 属性值委托给map
    val name: String by map
    val age:Int by map
}
```

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1、创建对象时给map填入初始值即可
        val user = User(
            mapOf(
                "name" to "Tom",
                "age" to 18
            )
        )
        // 2、取对象的值，自动取map中寻找对应的key值
        println("userName:${user.name}")
        println("userAge:${user.age}")
    }

}
```



# The end

参考：[官方文档](https://www.kotlincn.net/docs/reference/delegation.html)