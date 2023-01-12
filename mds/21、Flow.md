# Flow

###### 1、序列的概念

> 1、 除集合之外, Kotlin 还提供了另一种容器类型 -[序列](http://kotlin.liying-cn.net/docs/reference_zh/sequences.html) (`Sequence<T>`. 序列提供的函数和 `Iterable` 一样, 但对多步骤的集合处理提供另一种实现方式.
> 2、序列和集合的最大区别是操作的执行顺序也不同: `Sequence` 对每个元素依次执行所有的处理步骤. 而 `Iterable` 会对整个集合执行单个处理步骤, 然后再对结果集合执行下一个处理步骤.
> 3、序列可以同步的执行所有值的计算结果。

###### 2、Flow作用

> 挂起函数可以异步的返回单个值，使用 List 结果类型，意味着我们只能一次返回所有值。但是该如何异步返回多个计算好的值呢？这正是 Kotlin 流（Flow）的用武之地。

###### 3、流的概念

在介绍Flow时都会介绍到“流”的概念，比如冷流、热流之类的。

（1）流的构成

> 一个异步数据流，通常包含三部分：

- 上游
- 操作符（操作符都是flow对象的方法十分重要）
- 下游

（2）冷流

> 下游无消费行为时，上游不会产生数据，只有下游开始消费，上游才从开始产生数据。

（3）热流

> 无论下游是否有消费行为，上游都会自己产生数据。

# Flow操作符

Flow默认为冷流，即下游有消费时，才执行生产操作。所以，操作符也被分为两类——中间操作符和末端操作符，中间操作符不会产生消费行为，返回依然为Flow，而末端操作符，会产生消费行为，即触发流的生产。所以末端操作符的返回值可能不同。

###### 1、Flow的创建

（1）flow{}

> 通过flow{}构造器，可以快速创建Flow，在flow中，可以使用emit来生产数据或者emitAll生产批量数据.
> ps：emit或者emitAll应道在flow{}中被调用，否则会报错的，如下注释。

```
// emit should happen strictly in the dispatchers of the block in order to preserve the flow context.
        // For example, the following code will result in an IllegalStateException:
        flow {
            for (i in 0..10) {
                emit(i) 
            }
        }
```

（2）flowOf

> 与listOf类似，Flow可以通过flowOf来产生有限的已知数据。

```
flowOf(1, 2, 3) // 带吗进行了封装内部调用了emit如下源代码

public fun <T> flowOf(vararg elements: T): Flow<T> = flow {
for (element in elements) {
emit(element)
 }
}
```

（3）list.asFlow：asFlow用于将List转换为Flow。

（4）emptyFlow:创建一个空流

###### 2、末端操作符

仅仅创建Flow，是不会执行Flow中的任何代码的，末端操作符在调用之后，创建Flow的代码才会执行。

末端操作符都是suspend函数，所以需要运行在协程作用域中。

（1）collect：最常用的末端操作符

```
        val job = Job()
        val scope = CoroutineScope(job)
        scope.launch(Dispatchers.Main) {

            flow {
                for (i in 0..10) {
                    emit(i) // 默认情况下发射的值啥类型，末端操作符收集到的就是啥类型。
                }
            }.collect {
                  Log.d(TAG,"value:$it")
            }
        }
```

（2）collectIndexed：带下标的collect，下标是Flow中元素的索引。

```
        val job = Job()
        val scope = CoroutineScope(job)
        scope.launch(Dispatchers.Main) {

            flow {
                for (i in 0..10) {
                    emit(i) // 发射元素的索引
                }
            }.collectIndexed{ index, value ->
                  Log.d(TAG,"index:$index value:$value")
            }
        }
```

（3）toCollection、toSet、toList：将Flow转换为Collection、Set和List

```
                val list =     flow {
                for (i in 0..10) {
                    emit(i) // 发射元素的索引
                }
            }.toList()
```

（4）launchIn：在指定的协程作用域中直接执行Flow。

```
            val job =   flow {
            for (i in 0..10) {
                emit(i) // 发射元素的索引
            }
        }.launchIn(MainScope())
```

（5）last、lastOrNull、first、firstOrNull

last、lastOrNull，返回Flow的最后一个值。

first、firstOrNull，返回Flow的第一个值。

xxxOrNull 元素可为空，捕获抛异常。

```
        val job = Job()
        val scope = CoroutineScope(job)
        scope.launch(Dispatchers.Main) {


         val value =   flow {
                for (i in 0..10) {
                    emit(i) // 发射元素的索引
                }
            }.first()
            Log.d(TAG,"value:$value")
        }
```

###### 3、状态操作符

状态操作符不做任何修改，可以理解为流各种状态的监听

- onStart：在上游生产数据前调用
- onCompletion：在流完成或者取消时调用，同时onCompletion也可以监听异常。
- catch：对上游中的异常进行捕获。
- onEach：上游每次发射数据前调用，没将要发射数据时就回调一次。
- retry、retryWhen：在发生异常时进行重试，retry 操作符最终调用的是 retryWhen 操作符。

（1）onStart

```
flow {
                for (i in 0..10) {
                    emit(i) 
                }
            }.onStart {
                Log.d(TAG, "onStart") // 调用一次
            }.collect { // 注释之后不会产生数据，onStart 也不会回调
                Log.d(TAG, "value:$it")
            }
```

（2）onCompletion

```
flow {
                for (i in 0..10) {
                    emit(i)
                }
            }.onCompletion {
                Log.d(TAG, "onCompletion") // 在末端操作符内逻辑执行完成后这里进行一次回调
            }.collect { // 注释之后不会产生数据，onStart 也不会回调
                Log.d(TAG, "value:$it")
            }
```

```
flow {
                for (i in 0..10) {
                    emit(i)
                }
            }.onCompletion { e->
                Log.d(TAG, "onCompletion") 
                Log.d(TAG, "exception:$e") // 无异常时这里为空
                e?.printStackTrace()
            }.collect { // 注释之后不会产生数据，onStart 也不会回调
                Log.d(TAG, "value:$it")
            }
```

> 注意这里只能监听到异常，未进行捕获处理（也即当有异常出现时只进行监听还是会crash），捕获处理请参见catch操作符。

（3）catch

```
flow {
                for (i in 0..10) {
                    emit(i) // 发射元素的索引
                }
                throw IllegalArgumentException()

            }.onCompletion { e ->

                e?.let {
                    Log.d(TAG, "监听到异常")
                    e.printStackTrace()
                }

            }.catch { error->
                Log.d(TAG, "同样监听到异常$error")  // 参数就是捕获的异常类
            }.collect {
                Log.d(TAG, "value:$it")
            }
//2021-09-29 09:54:16.296 9864-9864/com.example.algorithm D/CoroutineActivity: value:0
//2021-09-29 09:54:16.296 9864-9864/com.example.algorithm D/CoroutineActivity: value:1
//2021-09-29 09:54:16.296 9864-9864/com.example.algorithm D/CoroutineActivity: value:2
//2021-09-29 09:54:16.296 9864-9864/com.example.algorithm D/CoroutineActivity: value:3
//2021-09-29 09:54:16.296 9864-9864/com.example.algorithm D/CoroutineActivity: value:4
//2021-09-29 09:54:16.296 9864-9864/com.example.algorithm D/CoroutineActivity: value:5
//2021-09-29 09:54:16.296 9864-9864/com.example.algorithm D/CoroutineActivity: value:6
//2021-09-29 09:54:16.297 9864-9864/com.example.algorithm D/CoroutineActivity: value:7
//2021-09-29 09:54:16.297 9864-9864/com.example.algorithm D/CoroutineActivity: value:8
//2021-09-29 09:54:16.297 9864-9864/com.example.algorithm D/CoroutineActivity: value:9
//2021-09-29 09:54:16.297 9864-9864/com.example.algorithm D/CoroutineActivity: value:10
//2021-09-29 09:54:16.297 9864-9864/com.example.algorithm D/CoroutineActivity: 监听到异常
//2021-09-29 09:54:16.298 9864-9864/com.example.algorithm W/System.err:     at com.example.algorithm.CoroutineActivity$onCreate$1$1.invokeSuspend(CoroutineActivity.kt:28)
//2021-09-29 09:54:16.298 9864-9864/com.example.algorithm W/System.err:     at com.example.algorithm.CoroutineActivity$onCreate$1$1.invoke(Unknown Source:10)
//2021-09-29 09:54:16.299 9864-9864/com.example.algorithm W/System.err:     at com.example.algorithm.CoroutineActivity$onCreate$1.invokeSuspend(CoroutineActivity.kt:48)
//2021-09-29 09:54:16.300 9864-9864/com.example.algorithm D/CoroutineActivity: 同样监听到异常java.lang.IllegalArgumentException
```

（4）onEach

```
flow {
                for (i in 0..10) {
                    emit(i) // 发射元素的索引
                }
            
            }.onEach {
                Log.d(TAG, "onEach:$it")
            }.collect { // 注释之后不会产生数据
                Log.d(TAG, "value:$it")
            }
```

（5）retry、retryWhen

```
flow {
                for (i in 0..10) {
                    emit(i) // 发射元素的索引
                }

            }.onEach {
                if (it == 3) throw IllegalArgumentException("Error value:$it") // 当发射的数值为3时主动抛出异常
            }.retry(2) { //指定重试次数
                return@retry true // 返回值代表是否重试。
            }.collect{
                Log.d(TAG,"$it")
            }
```

retryWhen中可以拿到异常和当前重试的次数

```
flow {
                for (i in 0..10) {
                    emit(i) // 发射元素的索引
                }

            }.onEach {
                if (it == 3) throw IllegalArgumentException("Error value:$it") // 当发射的数值为3时主动抛出异常
            }.retryWhen { cause, count ->
                cause.printStackTrace() // 打印异常日志
                // true:无限重试
                //false：不重试
                count<=3  // count 布尔表达式控制重试的次数，这里代表重试3次
            }.collect {
                Log.d(TAG, "$it")
            }
```

###### 4、转换操作符

转换操作符就是对数据进行变换的，主要有map系列与transform系列，map是一对一的转换，transform操作符与map操作符有点一样，但又不完全一样，transform则可以完全控制流的数据，进行过滤、 重组等等操作都可以。

（1）map系列

- map
- mapLatest
- mapNotNull

```
flow {
                for (i in 0..10) {
                    emit(i) // 发射元素的索引
                }
            }.map {
                it * it  // 对发射的每个数据进行平方
            }.collect {
                Log.d(TAG, "$it")
            }
```

（2）transform系列

- transform
- transformLatest
- transformWhile

```
flow {
                for (i in 0..10) {
                    emit(i) // 发射int元素
                }
            }.transform { //方法体内部 emit 方法必须重新调用
                if (it==1){
                    emit("int to value:$it") //数据转化为string
                }
            }.collect {
                Log.d(TAG, it)
            }
// 只打印出符合筛选的，重新发射的数据
//2021-09-29 13:42:47.836 16065-16065/com.example.algorithm D/CoroutineActivity: int to value:1
```

###### 5、过滤操作符

过滤操作符用于过滤流中的数据

（1）filter系列

- filter
- filterInstance
- filterNot
- filterNotNull

```
flow {
                for (i in 0..10) {
                    emit(i) // 发射int元素
                }
            }.filter {
                it ==1  // 过滤筛选满足条件的，其他的丢弃，如这里筛选值为1的元素

            }.collect {
                Log.d(TAG, "$it")
            }
```

（2）drop/take系列

这类操作符可以丢弃前n个数据，或者是只拿前n个数据。带while后缀的，则表示按条件进行判断。

- drop
- take
- dropWhile
- takeWhile

```
flow {
                for (i in 0..10) {
                    emit(i) // 发射int元素
                }
            }.drop(3) //丢弃前三个元素
                    .collect {
                Log.d(TAG, "$it")
            }
```

（3）debounce：用于防抖，指定时间内的值只接收最新的一个

```
flow {
                for (i in 0..10) {
                    emit(i) // 发射int元素
                }
            }.debounce(1000) // 指定时间内只接收最新一个数据
                    .collect {
                Log.d(TAG, "$it") // print 10
            }
```

sample操作符与debounce操作符有点像，但是却限制了一个周期性时间，sample操作符获取的是一个周期内的最新的数据，可以理解为debounce操作符增加了周期的限制。

（4）distinctUntilChanged：过滤相等的元素。默认规则以对象作为筛选。

```
flow {
                for (i in 0..10) {
                    emit(i) // 发射int元素
                }
            }.distinctUntilChanged()
                    .collect {
                        Log.d(TAG, "$it")   // 元素都不同：打印0-10
                    }
```

```
flow {
                for (i in 0..10) {
                    emit(3) // 发射11个3
                }
            }.distinctUntilChanged() 
                    .collect {
                        Log.d(TAG, "$it")   // 元素都同：打印1个
                    }
```

```
data class Person(val age: Int, val name: String)

flow {
                emit(Person(20, "韩信"))
                emit(Person(21, "妲己"))
                emit(Person(21, "安其拉"))
                emit(Person(22, "亚瑟"))
            }.distinctUntilChanged { old, new ->
                old.age == new.age // 定义规则，age相同的视为同一个
            }
                    .collect {
                        Log.d(TAG, "$it")  
                    }
2021-09-29 14:18:29.005 17522-17522/com.example.algorithm D/CoroutineActivity: Person(age=20, name=韩信)
2021-09-29 14:18:29.005 17522-17522/com.example.algorithm D/CoroutineActivity: Person(age=21, name=妲己)
2021-09-29 14:18:29.005 17522-17522/com.example.algorithm D/CoroutineActivity: Person(age=22, name=亚瑟)
```

###### 6、组合操作符

（1）combine：连接两个不同的Flow

```
val fl1 = flowOf(1, 2, 3)

            val fl2 = flowOf("a", "b", "c")

            fl1.combine(fl2) { it, str -> // 参数代表各自内元素类型
                "$it:$str" // 每个元素最终以string形式结合
            }.collect {
                Log.d(TAG, it)
            }
2021-09-29 14:26:13.357 17722-17722/com.example.algorithm D/CoroutineActivity: 1:a
2021-09-29 14:26:13.359 17722-17722/com.example.algorithm D/CoroutineActivity: 2:a
2021-09-29 14:26:13.359 17722-17722/com.example.algorithm D/CoroutineActivity: 2:b
2021-09-29 14:26:13.360 17722-17722/com.example.algorithm D/CoroutineActivity: 3:b
2021-09-29 14:26:13.361 17722-17722/com.example.algorithm D/CoroutineActivity: 3:c
```

# Flow的线程切换

线程的切换使用flowOn操作符，flowOn会对上游，以及flowOn方法之前的所有操作符生效。

```
flow {
                Log.d(TAG, "current Thread:${Thread.currentThread()}")
                emit(2)

            }.map {
                Log.d(TAG, "current Thread:${Thread.currentThread()}")
                it * it
            }.flowOn(Dispatchers.IO).map { // flowOn之前指定为IO线程，之后默认主线程。
                Log.d(TAG, "current Thread:${Thread.currentThread()}")
                it * 10
            }.collect {
                        Log.d(TAG, "current Thread:${Thread.currentThread()}")
                    }
2021-09-29 14:50:54.034 18358-18392/com.example.algorithm D/CoroutineActivity: current Thread:Thread[DefaultDispatcher-worker-1,5,main]
2021-09-29 14:50:54.034 18358-18392/com.example.algorithm D/CoroutineActivity: current Thread:Thread[DefaultDispatcher-worker-1,5,main]
2021-09-29 14:50:54.037 18358-18358/com.example.algorithm D/CoroutineActivity: current Thread:Thread[main,5,main]
2021-09-29 14:50:54.037 18358-18358/com.example.algorithm D/CoroutineActivity: current Thread:Thread[main,5,main]
```

```
flow {
                Log.d(TAG, "current Thread:${Thread.currentThread()}") // 子线程
                emit(2)

            }.map {
                Log.d(TAG, "current Thread:${Thread.currentThread()}") // 子线程
                it * it
            }.flowOn(Dispatchers.IO).map {// flowOn之前指定为IO线程
                Log.d(TAG, "current Thread:${Thread.currentThread()}") // 主线程
                it * 10
            }.flowOn(Dispatchers.Main).map {
                        Log.d(TAG, "current Thread:${Thread.currentThread()}") // 子线程
                        it / 10
                    }.flowOn(Dispatchers.IO).collect {
                        Log.d(TAG, "current Thread:${Thread.currentThread()}") // 主线程（默认）
                    }
2021-09-29 14:55:11.190 18518-18554/com.example.algorithm D/CoroutineActivity: current Thread:Thread[DefaultDispatcher-worker-1,5,main]
2021-09-29 14:55:11.191 18518-18554/com.example.algorithm D/CoroutineActivity: current Thread:Thread[DefaultDispatcher-worker-1,5,main]
2021-09-29 14:55:11.223 18518-18518/com.example.algorithm D/CoroutineActivity: current Thread:Thread[main,5,main]
2021-09-29 14:55:11.224 18518-18555/com.example.algorithm D/CoroutineActivity: current Thread:Thread[DefaultDispatcher-worker-2,5,main]
2021-09-29 14:55:11.268 18518-18518/com.example.algorithm D/CoroutineActivity: current Thread:Thread[main,5,main]
```

# 取消Flow

最常用的方式就是通过withTimeoutOrNull来取消Flow，Flow的取消，实际上就是依赖于协程的取消。

```
val job = Job()
        val scope = CoroutineScope(job)
        scope.launch(Dispatchers.Main) {

            withTimeoutOrNull(3000) { // 指定时间取消协程：Flow的取消，实际上就是依赖于协程的取消
                flow {
                    for (i in 0..10) {
                        delay(1000)
                        emit(i)
                    }
                }.collect {
                    Log.i(TAG, "value:$it")
                }
            }

        }
        //2021-09-29 15:01:21.000 18708-18708/com.example.algorithm I/CoroutineActivity: value:0
        //2021-09-29 15:01:22.002 18708-18708/com.example.algorithm I/CoroutineActivity: value:1
```

# Flow的同步非阻塞模型

Flow的同步：默认场景下，Flow在没有切换线程的时候，运行在协程作用域指定的线程，这就是同步。

Flow的非阻塞：emit和collect都是suspend函数，所谓suspend函数，就是会挂起，将CPU资源让出去，这就是非阻塞，因为suspend了就可以让一让，让给谁呢？让给其它需要执行的函数，执行完毕后，再把资源还给我

```
flow {
                for (i in 0..3) {
                    emit(i)
                }
            }.onEach {
                Log.i(TAG, "onEach:$it")
            }
                    .collect {
                        Log.i(TAG, "collect:$it")
                    }
            // 可见：emit一个，collect拿一个，这就是同步非阻塞，互相谦让，这样谁都可以执行，
            // 看上去flow中的代码和collect中的代码，就是同步执行的。
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: onEach:0
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: collect:0
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: onEach:1
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: collect:1
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: onEach:2
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: collect:2
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: onEach:3
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: collect:3
```

# Flow的异步非阻塞模型

假如我们给Flow增加一个线程切换，让Flow执行在子线程上述代码Flow就变成了异步非阻塞模型了。

不同的线程肯定是异步的。

非阻塞，就没什么意义了，由于flow代码先执行，而这里的代码由于没有delay，所以是同步执行的，执行的同时，collect在主线程进行监听。

```
flow {
                for (i in 0..3) {
                   // delay(1000)  这里加上这句，可实现同步非阻塞模型
                    emit(i)
                }
            }.onEach {
                Log.i(TAG, "onEach:$it")
            }.flowOn(Dispatchers.IO)
                    .collect {
                        Log.i(TAG, "collect:$it")
                    }
            // 2021-09-29 15:22:37.962 19509-19543/com.example.algorithm I/CoroutineActivity: onEach:0
            //2021-09-29 15:22:37.962 19509-19543/com.example.algorithm I/CoroutineActivity: onEach:1
            //2021-09-29 15:22:37.962 19509-19543/com.example.algorithm I/CoroutineActivity: onEach:2
            //2021-09-29 15:22:37.963 19509-19543/com.example.algorithm I/CoroutineActivity: onEach:3
            //2021-09-29 15:22:37.964 19509-19509/com.example.algorithm I/CoroutineActivity: collect:0
            //2021-09-29 15:22:37.964 19509-19509/com.example.algorithm I/CoroutineActivity: collect:1
            //2021-09-29 15:22:37.964 19509-19509/com.example.algorithm I/CoroutineActivity: collect:2
            //2021-09-29 15:22:37.964 19509-19509/com.example.algorithm I/CoroutineActivity: collect:3
```

注意：除了使用flowOn来切换线程，使用channelFlow也可以实现异步非阻塞模型。

[参考1](https://mp.weixin.qq.com/s/hpLTj8SiirGvw2hsPLL-1g)

[参考2](https://www.kotlincn.net/docs/reference/coroutines/flow.html)
