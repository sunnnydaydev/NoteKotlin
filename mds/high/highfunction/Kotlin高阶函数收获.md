# 有关::符号的用法总结

###### 1、回顾下函数类型

```kotlin
fun getString(n:Int):Int{
   //todo
}
```
如上就是一个普通的kotlin方法，其函数类型为：（Int）->Int

###### 2、进入正题

首先定义个回调

```kotlin
class ARCoreSessionLifecycleHelper(
    private val activity: Activity,
    private val features: Set<Session.Feature> = setOf()
) : DefaultLifecycleObserver {
    // 定义个回调，类型是函数类型。
    // 1、函数类型的参数类型是Session
    // 2、函数类型的返回值是Unit类型（空类型）
    // 3、外层的括号是符号，内层的括号是固定写法
    var beforeSessionResume:((Session)->Unit)? = null
}
```

::符号调用

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var arCoreSessionLifecycleHelper: ARCoreSessionLifecycleHelper
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arCoreSessionLifecycleHelper = ARCoreSessionLifecycleHelper(this)
        //::直接调用函数名即可，这样当回调触发时（beforeSessionResume.invoke(xxx)），参数自动传入configSession方法。
        arCoreSessionLifecycleHelper.beforeSessionResume = ::configSession
    }

    /**
     * 一个普通的方法其函数类型：  (session)->Unit
     * */
    private fun configSession(session: Session){

    } 
}
```

过程分析

其实完整的写法应该是这样的
```kotlin
        arCoreSessionLifecycleHelper.beforeSessionResume = { it: Session -> {
            
            }
        }
```
因为kotlin的lambda种有一个参数时是可以省略的，默认是相应的参数所以也可以简写为这样：

```kotlin
        // 假若高阶函数（beforeSessionResume）有多个参数时，这里就不能省略了，需要一一列出参数
        arCoreSessionLifecycleHelper.beforeSessionResume = { 
            // 这具有一个变量 it 类型就是Session类型
        }
```

::的写法应该是kt的语法糖，为了更加方便调用者使用~