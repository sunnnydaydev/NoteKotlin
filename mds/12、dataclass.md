# data class

给类加上data关键字即可，编译器自动生成 符合你的类的toString、equals、hashcode、copy方法。

###### 1、栗子

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
    // log:
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
- 如果在数据类体中有显式实现 equals()、 hashCode() 或者 toString()，或者这些函数在父类中有 final 实现，那么不会生成这些函数，而会使用现有函数。


###### 2、copy 方法

在很多情况下，我们需要复制一个对象改变它的一些属性，但其余部分保持不变。 copy() 函数就是为此而生成。

- 使用上文的data关键字后编译器自动生成
- 作用生成类实例的副本（和原来实例hash地址不一样）

```kotlin
fun main() {
    val person = Person("Tom")
    println("person 内存地址:${person.hashCode()}")
    val copyPerson = person.copy(name = "Kate")
    println("copyPerson 内存地址:${copyPerson.hashCode()}")
}

data class Person(val name: String)
```
log ->

person 内存地址:84274

copyPerson 内存地址:2331239

###### 3、探究

data class 编译为java代码是怎样的呢？我们搞个demo然后反编译下试试~

```kotlin
data class Person(val name: String)
```

看下生成后的java代码 ->

```java

public final class Person {
   @NotNull
   private final String name;

   @NotNull
   public final String getName() {
      return this.name;
   }

   public Person(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      super();
      this.name = name;
   }

   @NotNull
   public final String component1() {
      return this.name;
   }

   @NotNull
   public final Person copy(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      return new Person(name);
   }

   // $FF: synthetic method
   public static Person copy$default(Person var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = var0.name;
      }

      return var0.copy(var1);
   }

   @NotNull
   public String toString() {
      return "Person(name=" + this.name + ")";
   }

   public int hashCode() {
      String var10000 = this.name;
      return var10000 != null ? var10000.hashCode() : 0;
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof Person) {
            Person var2 = (Person)var1;
            if (Intrinsics.areEqual(this.name, var2.name)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
```

###### 4、数据类的解结声明

对于data class我们可以以解结声明方式定义变量 ->

```kotlin
fun main() {
    // 语法很简单定义多个变量使用括号扩住即可，多个变量间逗号隔开。
    val (aName,aAge) = Animal("",1)
    // 使用变量
    println("name:$aName")
    println("age:$aAge")
}
data class Animal(val name: String,val age:Int)
```

方法、映射都可使用结构声明：

（1）方法的解结构

方法的解构其实还是data class的解构，方法的返回值要是data class，普通的class是无法解构的。

```kotlin
data class Result(val result: Int, val status: Status)
fun function(): Result {
    // 各种计算

    return Result(result, status)
}

// 现在，使用该函数：
val (result, status) = function()
```

（2）映射解构

这个一般用来进行遍历

```kotlin
fun main() {
    val map = hashMapOf("userName" to "kate", "userSex" to "girl")
    for ((key,value) in map){
        println("key:$key value:$value")
    }
}
```

（3）未使用的变量使用下划线代替

未使用的解构变量可以使用下划线 "_"替代

```kotlin
val (_, status) = getResult()
```

[解构参考](https://www.kotlincn.net/docs/reference/multi-declarations.html)


