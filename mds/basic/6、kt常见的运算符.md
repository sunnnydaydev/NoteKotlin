# 运算符

###### 1、安全运算符 ?

其实就是对变量进行了下判断，做了下保护，不至于产生空指针。我们看下源码就知晓了:

(1)首先来个栗子

```kotlin
/**
 * 若s不为空,b为s字符串的长度
 * 若s为空，b为null
 * */
fun test1(s: String?){
    //若s不为空,b为s字符串的长度
    //若s为空，b为null
    val b = s?.length
    println("value b:$b")
}

/**
 * 若s不为空,方法返回值为s字符串的长度
 * 若s为空，方法返回值为null
 * */
fun test2(s: String?) = s?.length
```

（2）源码验证

获取kt转java的源码

```java
public final class Test {
    
   public static final void test1(@Nullable String s) {
      Integer b = s != null ? s.length() : null;
      String var2 = "value b:" + b;
      System.out.println(var2);
   }

   @Nullable
   public static final Integer test2(@Nullable String s) {
      return s != null ? s.length() : null;
   }
}
```

###### 2、非空断言!!

对目标变量进行断言这个变量不为空。使用这个符号后运行期间会执行断言判断，当判断目标变量为空就抛出空指针。看下源码就真相大白了：

（1）栗子

```kotlin
fun main() {
    val str: String? = null
    println(str!!) //NullPointerException，你使用这个符号断言了变量非空。系统就会检测，检测到不实还是会抛出异常。
}
```

（2）生成的Java源码

```java

class Test {
    public static final void main() {
        String str = (String)null;
        // 1、系统针对变量进行检测
        Intrinsics.checkNotNull(str);
        System.out.println(str);
    }
    // 2、Intrinsics#checkNotNull摘抄
    public static void checkNotNull(Object object) {
        if (object == null) {
            throwJavaNpe();
        }
    }
    // 3、Intrinsics#throwJavaNpe摘抄
    public static void throwJavaNpe() {
        throw (NullPointerException)sanitizeStackTrace(new NullPointerException());
    }
}

```


###### 3、?:运算符


```kotlin
fun strLen(s: String?) = s?.length?:0
```

很好理解，变量的值是否为空，为空则执行?:之后的语句。


###### 4、 强制转化符号as

as和java的强制转换功能一样

(1)栗子

```kotlin
fun main() {
    val str = "123"
    println(str as String) //转换成功 正常输出as前面内容
    println(str as Int)// 类型转换失败 ：ClassCastException: java.lang.String cannot be cast to java.lang.Integer
}
```

（2）优雅的 as？

为了避免转换时的空指针我们需要添加if判断并结合as来进行类型判断，这时转化失败时返回null，而不是报ClassCastException ->

```kotlin
println(str as? Int) // null
```

###### 5、类型判断运算符is

is 和java 的instance of 功能一样




