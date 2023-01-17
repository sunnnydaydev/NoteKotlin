# kt内部类

```kotlin
/**
 * Created by sunnyDay on 2019/7/12 15:30
 */

fun main(){
    val o = Outer()
    val i = o.InnerClass()
    println(i.getNumber())

}
class Outer{
    val number = 10
    inner class InnerClass{
        fun getNumber():Int{
            return number
        }
    }
}

/**
 * java的内部类
 * 一、嵌套类
 *
 * 1、嵌套类：定义在一个类的内部作为类的成员。
 *
 * 2、嵌套类分类：
 *    a、静态嵌套类：不持有外部类引用
 *    
 *    b、非静态内部类：持有外部类引用
 *
 * 二、内部类：定义在累的内部
 * 
 *     分类：1、成员内部类
 *     
 *          2、方法内部类
 *          
 *          3、匿名内部类
 *          
 *          4、嵌套类
 *
 *
 * kotlin的内部类
 * 1、类A中定义 class B{}  这时B没有持有A的引用（与java中的静态内部类作用相同）
 * 2、类A中定义 inner class B{} 这时B持有A的应用
 * 
 * kotlin内部类想要持有外部类的引用要加inner关键字
 *
 * */
```

[参考](https://blog.csdn.net/hguisu/article/details/7270086)
