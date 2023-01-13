###### 1、通用对象方法

给类加上data关键字即可，编译器自动生成 符合你的类的toString、equals、hashcode、copy方法。

栗子：

```kotlin

// data  关键字
data class Person(val name: String)

fun main() {
    val person = Person("tom")
    val person1 = Person("tom")
    val set = hashSetOf(person)

    println(set.contains(person1))
    println(person.toString())
    println(person == person1)
    //log:
    // true
    // Person(name=tom)
    // true

}
```

注意：data class 要求

- 主构造函数需要至少有一个参数
- 主构造函数的所有参数需要标记为 val 或 var
- 数据类不能是抽象、开放、密封或者内部的。
- （在1.1之前）数据类只能实现接口


###### 2、copy 方法

在很多情况下，我们需要复制一个对象改变它的一些属性，但其余部分保持不变。 copy() 函数就是为此而生成。

- 使用上文的data关键字后编译器自动生成
- 作用生成类实例的副本

```kotlin
// data  关键字
data class Person(val name: String)

fun main() {
    val person = Person("tom")
    println(person.copy(name = "Kate")) 
   
}
```

###### 3、探究

data class 编译为java代码是怎样的呢？我们搞个demo然后反编译下试试

```kotlin
data class Person(val name: String)
```


