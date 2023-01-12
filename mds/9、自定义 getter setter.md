###### 1、 get set
>1、kotlin为我们自动创建 getter 、setter方法。

>2、val类型的变量 为可读变量 我们能访问 getter方法不能访问setter方法

>3、var类型的变量 getter setter方法我们都能访问

###### 2、栗子
```java
/**
 * Create by SunnyDay on 2019/07/12
 */
class LengthCount {
    var counter = 0
   // private set
}


fun main(args: Array<String>){
    val lengthCount = LengthCount()
    lengthCount.counter   // 内部就是调用的get方法（可以通过java文件调用我们的LengthCount测试）
    lengthCount.counter =3 // 内部就是调用的set方法 
    
}

```
###### get set须知（set为栗子 ，重要看写法）
(1)可以改变其访问性
```java
class LengthCount {
    var counter = 0
        private set //外部访问不了set
}

```
（2）自定义set
```java

class LengthCount {
    var counter = 0

    // set 的内部实现：吧用户传的value赋值给field
//        set(value) {
//            field = value
//        }
        
        //我们自定义实现
    set(value) {
        field = value+1  
    }
}
```

###### 4、自定义栗子
```java

package myinterface

/**
 * Created by sunnyDay on 2019/7/12 18:53
 *
 */
class User() {
    // 给address 重写setter属性
    var address: String = ""
        set(value){ // set 换行
         if (value> 0.toString()){
           field =value+"我是后缀"
        }
    }
   val name:String
    get()="自定义getter"  // get换行
}

fun main(){
    val user = User()
    user.address = "sdfasf"
    println(user.address) // log:sdfasf我是后缀

    println(user.name) // log:自定义getter
}
```

> ps:
>
> 1、get() set()的书写格式
>
> 2、重写set时 filed、value字段是 set（）定义的。我们只需使用操作表达式即可。
