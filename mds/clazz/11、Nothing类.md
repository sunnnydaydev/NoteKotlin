# Nothing类

这个类通常是用来做标记的，标记一个不存在的东西，接下来看下api文档的介绍：

```kotlin
/**
 * Nothing has no instances. You can use Nothing to represent "a value that never exists": for example,
 * if a function has the return type of Nothing, it means that it never returns (always throws an exception).
 */
public class Nothing private constructor()
```

现学

```kotlin
fun main (){
   TODO("todo")
}
//Exception in thread "main" kotlin.NotImplementedError: An operation is not implemented: todo
```
我们可以发现kt标准库Standard.kt中提供了个方法TODO，这个方法使用起来直接抛出了异常。其源码是这样的：

```kotlin
/**
 * Always throws [NotImplementedError] stating that operation is not implemented.
 *
 * @param reason a string explaining why the implementation is missing.
 */
@kotlin.internal.InlineOnly
public inline fun TODO(reason: String): Nothing = throw NotImplementedError("An operation is not implemented: $reason")
```

现用 😄

```kotlin
fun test():Nothing{
   throw NotImplementedError("you need not implemented this method!")
}
```