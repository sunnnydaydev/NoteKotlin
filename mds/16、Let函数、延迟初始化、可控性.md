### 一、Let函数

###### 1、语法

```kotlin
let{
    //todo
}
```

###### 2、使用

> 用处有两处
>
> 1、在函数体内使用it替代类的对象去访问其公有的属性和方法
>
> 2、与？.一起使用，表示对象不为null时执行let方法体
>
> ps:详情参考下面栗子

```kotlin
/**
 * Created by sunnyDay on 2019/8/19 16:31
 *
 */
fun main() {
      val xiaoHua = Stu()
      // case 1
      println(xiaoHua.let { it.getStuname() }) // it 代表xiaoHua这个对象
      // case 2
      println(xiaoHua?.let { "i am not empty" }) // xiaoHua 不为null时执行
}

class Stu {
    val name = "sunnyday"
    fun getStuname(): String ? {
        return name
    }
}
```



### 二 、延迟初始化lateinit

> 使用这个关键字不必声明对象时就初始化，可以向java一样使用时再创建对象

######  1、栗子

```kotlin
fun main() {

    val xiaoHua = Stu()
    println(xiaoHua.getBagName())
    
}
class Bag {
    val name = "i am bag"
}

class Stu {
    // lateinit var age:Int //  原始类型 不能在这里使用，只能为自定义类型对象
    lateinit var bag: Bag
    var name = "sunnyday"
    fun getStuname(): String? {
        return name
    }

    fun getBagName():String{
        bag = Bag() // 使用的时候初始化
        return bag.name
    }
}
```

###### 2、注意

> 1、该修饰符只能用于在类体中的属性（不是在主构造函数中声明的 `var` 属性，并且仅当该属性没有自定义 getter 或 setter 时）
>
> 2、而自 Kotlin 1.2 起，也用于顶层属性与局部变量。该属性或变量必须为非空类型，并且不能是原生类型。
>
> 3、在初始化前访问一个 `lateinit` 属性会抛出一个特定异常，该异常明确标识该属性被访问及它没有初始化的事实。



### 三、可空性

###### 1、可控性的扩展

> kotlin中定义了两个可空性判断的扩展函数：
>
> 1、isNullOrEmpty()：字符串判空
>
> 2、isNullOrBlank()：字符串判空，或者只包含空的字符

```kotlin

/**
 * Created by sunnyDay on 2019/8/19 16:31
 *
 */
fun main() {

    test("abc")
}

fun test(name:String){

    if (name.isNotEmpty()){ // 不需要我们显式进行非空判断（内部已经处理）
       println( name.length)
    }
}
```



###### 2、类型参数的可空性

> 1、kotlin中所有的泛型类和泛型函数的类型都是可空的。
>
> 2、Any代表任何类型

```kotlin
fun <T>test(t:T){  // t默认为 Any？类型，也就是任意可空类型 
   //使用之前判空处理
    if (t!=null){
        println(t.toString()) 
    }
    
}
```



> 如果不想让t为任意的可空类型需要规定非空上界（如下）

```kotlin
fun <T:Any>test(t:T){  // t默认为 Any？类型，也就是任意可空类型

     // 使用之前不用判空
        println(t.toString())

}

```





###### 3、java的可空

> 1、 @NotNull 不为null
>
> 2、@Nullable 可null



```java
/**
 * Created by sunnyDay on 2019/8/19 18:07
 */
public class Sunny {
    public static void main(String[] args) {
        test("Sunny",null);
    }

    public static void  test( @NotNull String name,@Nullable String sex){
       // @NotNull 不为null
        //@Nullable 可null
        System.out.println("测试:"+name+sex);
    }
}
```



