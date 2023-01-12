# toString 和equals

> 1、kotlin的tostring和java的一样需要时重写即可
>
> 2、平时我们可以使用双“==”号代替equals方法（==的底层就是equals，重写equals时“==”也就变化，和重写后的equals作用一样）
>
> 3、三个的“===”号比较对象时比较的是引用（内存地址）
>
> ps：没有重弄写equals时两个的“==”比较对象时也是比较对象引用



```java


/**
 * Created by sunnyDay on 2019/7/12 20:16
 *
 */
class Client(val name: String, val postalcode: Int) {

    // 重写 toString
    override fun toString(): String {
        return "[name = $name,postalcode = $postalcode]"
    }

    override fun equals(other: Any?): Boolean {
        // return super.equals(other)
        if (other !is Client)
            return false

        val anotherClient: Client = other as Client
        return anotherClient.name == this.name && anotherClient.postalcode == this.postalcode


    }
}

fun main() {
    val client = Client("Tom", 20)
    // 没有重写时
    // println(client.toString()) // aa.Client@60e53b93 默认打印对象地址  包名.类名@xxx（xxx为16进制数字）

    //重写后
    println(client) //[name = Tom,postalcode = 20]


    val client2 = Client("Tom", 20)

    println(client == client2)  //kotlin“等号”底层使用的equals方法进行对比  重写equals后 等号的作用也会和equals一样
    println(client.equals(client2))

    println(client===client2)  // kotlin 中三个等号“===”比较的是引用（内存地址）
}

```

