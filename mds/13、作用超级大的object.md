###### 1、对象声明：创建单利易如反掌

```java

/**
 * Created by sunnyDay on 2019/7/13 14:50
 *
 */
 
object PayRoll
```


>1、一个关键字搞定
>
>2、object定义的类名直接为对象
>
>ps：编译器（idea或者AS）找到你写的类：在Tools-kotlin-show kotlin bytecode会显显示窗口看生成的java代码

（1）简单使用

```java
/**
 * Created by sunnyDay on 2019/7/13 14:50
 *
 */
object PayRoll{  //类名就是对象
    fun test(){
        // todo nothing
    }
}

fun main(){
    val pay = PayRoll
    pay.test()
    
}
```





###### 2、半生对象：工厂方法和静态变量的位置

> 1、我们知道kotlin中没有static关键字取而代之的是顶层函数、或者顶层属性。
>
> 2、顶层函数有个缺点不能访问类的私有成员

（1）kotlin的companion 语法

```java
 companion object {
       // todo 这里声明 变量或者方法
    }
```



> 效果可以使用类名直接调用companion内部声明的 变量或者方法（类似java的静态调用）

栗子：

```java
class PayRoll {
    companion object {

        private val name: String = "tom"  //私有的还是不能访问

        val age: Int = 20

        fun test() {
            // todo nothing
        }
    }
}

fun main() {
    println(PayRoll.age)
    PayRoll.test()
}

```

> 这种方式我们就可以像java一样静态调用啦。

（2）伴生对象访问私有变量

```java
class PayRoll {
    private val sex: Char = 'b'

    companion object {

        private val name: String = "tom" 

        val age: Int = 20

        fun test() {
            println(PayRoll().sex)  // 访问私有成员
        }
    }
}

fun main() {
    println(PayRoll.age) // 使用 companion 类似静态调用
    PayRoll.test()       // 通过伴生对象 访问类的私有成员
}
```



###### 3、  companion object 须知

> 1、伴生对象可以有名字：object后面指出即可（不指出默认为companion ）
>
> 2、伴生对象可以实现接口，可以有扩展函数。

###### 4、匿名内部类

> 也需要使用object关键字

(1)语法

```java
//1 接口的匿名内部类语法
object:接口名{
   方法实现
}
// 2 抽象类的
object：抽象类名（）{
    方法体
}
```



（2）simple 栗子：

```java
package aa

import java.util.*

/**
 * Created by sunnyDay on 2019/7/13 16:05
 *
 */

// 接口
interface People {
    fun name()
}

// 抽象类
abstract class Animal{
    abstract fun run()
}

// 测试类
object  Test{
    fun testPeople(people: People){
        people.name()
    }
    fun testAnimal(animal: Animal){
        animal.run()
    }

}

fun main() {
     // 接口的匿名内部类写法
     Test.testPeople(object :People{
         override fun name() {
             println("I am Tom ！！！")
         }
     })

     // 抽象类的匿名内部类方式
    Test.testAnimal(object :Animal(){
        override fun run() {
            println("I am fish，I swim ！！！")
        }
    })


}
```



（3）匿名内部类的变量接收

```java
 val p =object :People{
       override fun name() {
           println("I am Tom ！！！")
       }

   }
```







