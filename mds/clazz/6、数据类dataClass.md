# data class

定义类时加上data关键字时编译器自动生成类的toString、equals、hashcode、copy、componentN。

###### 1、栗子🌰

```kotlin
data class Person(val name: String)

fun main() {
    Person("tom").apply {
        println("toString:${toString()}")
    }
}
//toString:Person(name=tom)
```

###### 2、data class 注意点

- 主构造函数需要至少有一个参数
- 主构造函数的所有参数需要标记为 val 或 var
- 数据类不能是抽象、开放、密封或者内部的
- 在kotlin1.1之前数据类只能实现接口
- 如果在数据类体中有显式实现 equals、 hashCode、toString这些函数时数据类不会生成这些函数。 或者这些函数在父类中有 final 实现，那么数据类不
会生成这些函数。
- 不允许重写 componentN、 copy方法，因为这两个方法都不是Object类的方法
- 在 JVM 中，如果生成的类需要含有一个无参的构造函数，则所有的属性必须指定默认值。

```kotlin
data class User(val name: String = "", val age: Int = 0)
```
- 编译器自动为主构造函数中声明的所有属性生成toString、copy、equals等方法。注意必须是主构造函数。


###### 2、copy 方法

在很多情况下，我们需要复制一个对象改变它的一些属性，但其余部分保持不变。 copy函数就是为此而生成。


```kotlin
data class Person(val name: String)
fun main() {
    val person = Person("Tom")
    val copyPerson = person.copy(name = "Kate")
    println("person[name:${person.name},hashCode:${person.hashCode()}]")
    println("copyPerson[name:${copyPerson.name},hashCode:${copyPerson.hashCode()}]")
}

// log ->
person[name:Tom,hashCode:84274]
copyPerson[name:Kate,hashCode:2331239]
```

一个简单的🌰来说下了copy的用法，那么copy的应用场景是啥呢？安卓mvi中有这样一个场景，数据的局部刷新：

MVI架构的核心思想是单项数据流，以ViewModel中的数据来驱动UI状态。

```kotlin

data class ViewState(
    val loadingState: String = "empty",//主页加载状态
    val jsonString: String = "{}", // 主页数据
    val commitButtonEnable: Boolean = false // 主页按钮状态
)

class MainActivity {
    /**
     * 模拟回调，当ViewModel中的ViewState更改时这里会收到回调。
     * */
    fun onViewStateUpdate(state: ViewState) {
        // todo 数据驱动UI
        // 0、进度条处理（根据loadingState）
        // 1、填充主页数据（根据jsonString）
        // 2、处理按钮状态（根据commitButtonEnable）
    }
}

class MainViewModel {

    // 定义个字段，这个字段会受到监听，当值更改时自动把数据回调到MainActivity#onViewStateUpdate中（模拟，具体不实现了）
    var viewState: ViewState = ViewState()

    /**
     * 业务1、模拟网络请求，请求主页面数据。从网路拿到数据，回流给UI层
     * */
    fun handleNetData() {
        viewState = viewState.copy(
            loadingState = "data", // 加载状态改为data，代表请求网络成功拿到数据
            jsonString = "{\"firstName\": \"Brett\"}" // 从网络获取到的数据
        )
    }

    /**
     * 业务2、模拟按钮状态的处理，当用户输入的内容大于8位时按钮可用。
     * 注意 注意 注意 这里未更改json值，因为根据业务json不应该改，所以copy时不用重新赋值代表使用上次最新的值。更新数据copy时只copy我们想要
     * 更新的即可，如下只更新loadingState、commitButtonEnable
     * */
    fun changeButtonStateByContent(userInput: String){
        viewState = viewState.copy(loadingState = "empty", commitButtonEnable =  userInput.length > 8)
    }

}
```

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

   /**
    * componentN这个N就代表在主构造函数中成员变量声明的顺序。如data class Person(val age:Int=10,val name: String)
    * 这样声明后 age为component1 name为component2
    * */
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

###### 4、数据类的解构声明

对于data class我们可以以解结声明方式定义变量。数据类生成的 Component 函数 使它们可在解构声明中使用。

啥是解构声明呢？其实就是可以一下定义多个变量，变量的值直接从数据类的成员中取。变量定义的顺序对应构造函数

```kotlin

data class Animal(val name: String,val age:Int)

fun main() {
    // 语法很简单，定义多个变量使用括号扩住即可，多个变量间逗号隔开。
    val (aName,aAge) = Animal("",1)
    // 使用变量
    println("name:$aName")
    println("age:$aAge")
}
```

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

（4）系统提供的数据类

标准库提供了 Pair 与 Triple

[解构参考](https://www.kotlincn.net/docs/reference/multi-declarations.html)


