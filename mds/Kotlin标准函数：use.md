# Kotlin标准函数 use

###### 1、函数主要功能：

在合适时机调用了实现类的close方法，这样保证了资源在合适的时机进行关闭。避免了有时书写代码时漏关资源的情景，（文件操作，操作完毕关闭输入输出流最常见，也最容易忘记关闭。

###### 2、函数可被哪些对象使用

这个方法的使用对象有点特殊，此方法可以被任意Closeable实现类的对象使用，其他的对象无法使用。使用方式如下：

```java
/**
 * Executes the given [block] function on this resource and then closes it down correctly whether an    exception is thrown or not.  
 * @param block a function to process this [Closeable] resource.
 * @return the result of [block] function invoked on this resource.
 */
@InlineOnly
@RequireKotlin("1.2", versionKind = RequireKotlinVersionKind.COMPILER_VERSION, message = "Requires newer compiler version to be inlined correctly.")
// 通过T.use可知方法时T的扩展函数。而T又是Closeable接口实现，所以use方法是Closeable扩展函数。
// emmm,没错这个源码就在Closeable.kt 文件中。
public inline fun <T : Closeable?, R> T.use(block: (T) -> R): R {
    var exception: Throwable? = null
    // 内部同过try catch 进行代码异常捕获，不用担心代码抛出异常导致crash问题。
    try {
        return block(this)
    } catch (e: Throwable) {
        exception = e
        throw e // 捕获异常继续抛出（所以不要以为use方法体内的代码不会crash）
    } finally { // 最终必被执行的代码，除非vm退出。
        when {
            apiVersionIsAtLeast(1, 1, 0) -> this.closeFinally(exception)
            this == null -> {}
            exception == null -> close()
            else ->
                try {
                    //调用close关闭资源
                    close()
                } catch (closeException: Throwable) {
                    // cause.addSuppressed(closeException) // ignored here
                }
        }
    }
}
```

###### 3、如何使用？？？ 很简单

```java
    reader.use { //这里reader可为任意Closeable实现类对象
        // todo 自己的逻辑处理
    }
```

###### 4、举个栗子

BufferedReader这个类实现了Closeable接口，并且在close中进行了资源关闭处理。这里就拿这个类进行下练习：

```java

/**
 * Create by SunnyDay on 16:45 2022/01/08
 */
fun main() {

    val file = File("F://test.txt")
    if (!file.exists()) {
        println("file is not exists ")
        return
    }
    val ins = FileInputStream(file)
    val reader = BufferedReader(InputStreamReader(ins))
    val sb = StringBuilder()
    // use 的使用
    reader.use { 
        // 假如这里有块逻辑会引发运行时异常这时会会会crash. 要留意、留意、留意。
       // val a = 0
       //     a= 1/a
        
        // forEachLine，Reader 的扩展函数。内部对“读”进行了封装。其实内部也用到了use方法。
        reader.forEachLine {
            sb.append(it)
        }
        println("读取文件成功，文件内容如下：\n $sb")
    }
}
```



###### 5、用途有哪些？？？？

- 通过文件读取操作我们就可以明显的看出use的优势了，假如我们自己写“读操作”这时就需要自己写try、catch来捕获异常，然后funally中各种判断来关闭资源。使用use方法简洁省事很多。
- kt的File相关类提供了好多扩展函数，这些扩展函数几乎都使用了use进行封装。use的出现更加简化文件操作。

还是上面读取文件栗子，用File#readLines（） 扩展函数来操作读取文件：

```java
    // readLines() 是file的扩展函数，源码最终还是调用Reader的forEachLine
    File("F://test.txt")
        .readLines() 
        .forEach {
            println("File 扩展函数读取：:$it")
        }
```



File的扩展函数真香，吃惊，，，， 太简洁了~~~~  




