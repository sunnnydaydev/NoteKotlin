# StateFlow与SharedFlow

- StateFlow
- SharedFlow

Flow涉及到三个概念

    producer  ->  Intermediary  -> consumer

    数据提供方      中间可修改数据     数据使用方

# 一、StateFlow

StateFlow是一个状态容器可观察数据流，可向其收集器发出当前状态更新和新状态更新。在 Android 中，StateFlow 非常适合需要让可变状态保持可观察的类。

```kotlin
/**
 * Create by SunnyDay /11/27 21:22:12
 * StateFlow是热数据流
 */
class LatestNewsViewModel : ViewModel() {
    private var count = 0

    
    private val _uiState: MutableStateFlow<String> = MutableStateFlow("default value")

    // 数据使用方 consumer
    //LatestNewsViewModel 公开 StateFlow，以便 View 能够监听界面状态更新，并自行使屏幕状态在配置更改后继续有效。
    val uiState: StateFlow<String> = _uiState

    init {
        // 数据提供方 producer
        viewModelScope.launch {
            while (count <= 100) {
                _uiState.value = count.toString()
                count += 2
            }
        }
    }

    fun updateValue() {
        viewModelScope.launch {
            for (i in 0..3) {
                _uiState.value = _uiState.value + i
            }
        }
    }
}
```

```kotlin
class MainActivity : AppCompatActivity() {

    private val latestNewsViewModel: LatestNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * init 中_uiState.value的最后值是100
         * */
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                latestNewsViewModel.uiState.collect {
                    Log.d("MainActivity", "UI层拿到数据:${it}")
                }
            }
        }
        /**
         *activity页面跑起来后点击下按钮：
         *这里_uiState.value在100的基础上拼接0,1,2,3 只输出最终的值1000123
         * */
        findViewById<View>(R.id.btnTest).setOnClickListener {
            latestNewsViewModel.updateValue()
        }
    }
}
```

负责更新 MutableStateFlow 的类是提供方，从 StateFlow 收集的所有类都是使用方。与使用 flow 构建器构建的冷数据流不同，StateFlow 是热数据流：
从数据流收集数据不会触发任何提供方代码。StateFlow 始终处于活跃状态并存于内存中，而且只有在垃圾回收根中未涉及对它的其他引用时，它才符合垃圾回收条件。

当新使用方开始从数据流中收集数据时，它将接收信息流中的最近一个状态及任何后续状态


如果需要更新界面，切勿使用 launch 或 launchIn 扩展函数从界面直接收集数据流。即使 View 不可见，这些函数也会处理事件。此行为可能会导致应用崩溃。
为避免这种情况，请使用 repeatOnLifecycle API。

可以使用stateIn api 把普通的flow转化为StateFlow

# 二、SharedFlow


# 参考

[官方文档](https://developer.android.google.cn/kotlin/flow/stateflow-and-sharedflow?hl=ru)



