# Flow

###### 1�����еĸ���

> 1�� ������֮��, Kotlin ���ṩ����һ���������� -[����](http://kotlin.liying-cn.net/docs/reference_zh/sequences.html) (`Sequence<T>`. �����ṩ�ĺ����� `Iterable` һ��, ���Զಽ��ļ��ϴ����ṩ��һ��ʵ�ַ�ʽ.
> 2�����кͼ��ϵ���������ǲ�����ִ��˳��Ҳ��ͬ: `Sequence` ��ÿ��Ԫ������ִ�����еĴ�����. �� `Iterable` �����������ִ�е���������, Ȼ���ٶԽ������ִ����һ��������.
> 3�����п���ͬ����ִ������ֵ�ļ�������

###### 2��Flow����

> �����������첽�ķ��ص���ֵ��ʹ�� List ������ͣ���ζ������ֻ��һ�η�������ֵ�����Ǹ�����첽���ض������õ�ֵ�أ������� Kotlin ����Flow��������֮�ء�

###### 3�����ĸ���

�ڽ���Flowʱ������ܵ��������ĸ����������������֮��ġ�

��1�����Ĺ���

> һ���첽��������ͨ�����������֣�

- ����
- ������������������flow����ķ���ʮ����Ҫ��
- ����

��2������

> ������������Ϊʱ�����β���������ݣ�ֻ�����ο�ʼ���ѣ����βŴӿ�ʼ�������ݡ�

��3������

> ���������Ƿ���������Ϊ�����ζ����Լ��������ݡ�

# Flow������

FlowĬ��Ϊ������������������ʱ����ִ���������������ԣ�������Ҳ����Ϊ���ࡪ���м��������ĩ�˲��������м�������������������Ϊ��������ȻΪFlow����ĩ�˲������������������Ϊ����������������������ĩ�˲������ķ���ֵ���ܲ�ͬ��

###### 1��Flow�Ĵ���

��1��flow{}

> ͨ��flow{}�����������Կ��ٴ���Flow����flow�У�����ʹ��emit���������ݻ���emitAll������������.
> ps��emit����emitAllӦ����flow{}�б����ã�����ᱨ��ģ�����ע�͡�

```
// emit should happen strictly in the dispatchers of the block in order to preserve the flow context.
        // For example, the following code will result in an IllegalStateException:
        flow {
            for (i in 0..10) {
                emit(i) 
            }
        }
```

��2��flowOf

> ��listOf���ƣ�Flow����ͨ��flowOf���������޵���֪���ݡ�

```
flowOf(1, 2, 3) // ��������˷�װ�ڲ�������emit����Դ����

public fun <T> flowOf(vararg elements: T): Flow<T> = flow {
for (element in elements) {
emit(element)
 }
}
```

��3��list.asFlow��asFlow���ڽ�Listת��ΪFlow��

��4��emptyFlow:����һ������

###### 2��ĩ�˲�����

��������Flow���ǲ���ִ��Flow�е��κδ���ģ�ĩ�˲������ڵ���֮�󣬴���Flow�Ĵ���Ż�ִ�С�

ĩ�˲���������suspend������������Ҫ������Э���������С�

��1��collect����õ�ĩ�˲�����

```
        val job = Job()
        val scope = CoroutineScope(job)
        scope.launch(Dispatchers.Main) {

            flow {
                for (i in 0..10) {
                    emit(i) // Ĭ������·����ֵɶ���ͣ�ĩ�˲������ռ����ľ���ɶ���͡�
                }
            }.collect {
                  Log.d(TAG,"value:$it")
            }
        }
```

��2��collectIndexed�����±��collect���±���Flow��Ԫ�ص�������

```
        val job = Job()
        val scope = CoroutineScope(job)
        scope.launch(Dispatchers.Main) {

            flow {
                for (i in 0..10) {
                    emit(i) // ����Ԫ�ص�����
                }
            }.collectIndexed{ index, value ->
                  Log.d(TAG,"index:$index value:$value")
            }
        }
```

��3��toCollection��toSet��toList����Flowת��ΪCollection��Set��List

```
                val list =     flow {
                for (i in 0..10) {
                    emit(i) // ����Ԫ�ص�����
                }
            }.toList()
```

��4��launchIn����ָ����Э����������ֱ��ִ��Flow��

```
            val job =   flow {
            for (i in 0..10) {
                emit(i) // ����Ԫ�ص�����
            }
        }.launchIn(MainScope())
```

��5��last��lastOrNull��first��firstOrNull

last��lastOrNull������Flow�����һ��ֵ��

first��firstOrNull������Flow�ĵ�һ��ֵ��

xxxOrNull Ԫ�ؿ�Ϊ�գ��������쳣��

```
        val job = Job()
        val scope = CoroutineScope(job)
        scope.launch(Dispatchers.Main) {


         val value =   flow {
                for (i in 0..10) {
                    emit(i) // ����Ԫ�ص�����
                }
            }.first()
            Log.d(TAG,"value:$value")
        }
```

###### 3��״̬������

״̬�����������κ��޸ģ��������Ϊ������״̬�ļ���

- onStart����������������ǰ����
- onCompletion��������ɻ���ȡ��ʱ���ã�ͬʱonCompletionҲ���Լ����쳣��
- catch���������е��쳣���в���
- onEach������ÿ�η�������ǰ���ã�û��Ҫ��������ʱ�ͻص�һ�Ρ�
- retry��retryWhen���ڷ����쳣ʱ�������ԣ�retry ���������յ��õ��� retryWhen ��������

��1��onStart

```
flow {
                for (i in 0..10) {
                    emit(i) 
                }
            }.onStart {
                Log.d(TAG, "onStart") // ����һ��
            }.collect { // ע��֮�󲻻�������ݣ�onStart Ҳ����ص�
                Log.d(TAG, "value:$it")
            }
```

��2��onCompletion

```
flow {
                for (i in 0..10) {
                    emit(i)
                }
            }.onCompletion {
                Log.d(TAG, "onCompletion") // ��ĩ�˲��������߼�ִ����ɺ��������һ�λص�
            }.collect { // ע��֮�󲻻�������ݣ�onStart Ҳ����ص�
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
                Log.d(TAG, "exception:$e") // ���쳣ʱ����Ϊ��
                e?.printStackTrace()
            }.collect { // ע��֮�󲻻�������ݣ�onStart Ҳ����ص�
                Log.d(TAG, "value:$it")
            }
```

> ע������ֻ�ܼ������쳣��δ���в�����Ҳ�������쳣����ʱֻ���м������ǻ�crash������������μ�catch��������

��3��catch

```
flow {
                for (i in 0..10) {
                    emit(i) // ����Ԫ�ص�����
                }
                throw IllegalArgumentException()

            }.onCompletion { e ->

                e?.let {
                    Log.d(TAG, "�������쳣")
                    e.printStackTrace()
                }

            }.catch { error->
                Log.d(TAG, "ͬ���������쳣$error")  // �������ǲ�����쳣��
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
//2021-09-29 09:54:16.297 9864-9864/com.example.algorithm D/CoroutineActivity: �������쳣
//2021-09-29 09:54:16.298 9864-9864/com.example.algorithm W/System.err:     at com.example.algorithm.CoroutineActivity$onCreate$1$1.invokeSuspend(CoroutineActivity.kt:28)
//2021-09-29 09:54:16.298 9864-9864/com.example.algorithm W/System.err:     at com.example.algorithm.CoroutineActivity$onCreate$1$1.invoke(Unknown Source:10)
//2021-09-29 09:54:16.299 9864-9864/com.example.algorithm W/System.err:     at com.example.algorithm.CoroutineActivity$onCreate$1.invokeSuspend(CoroutineActivity.kt:48)
//2021-09-29 09:54:16.300 9864-9864/com.example.algorithm D/CoroutineActivity: ͬ���������쳣java.lang.IllegalArgumentException
```

��4��onEach

```
flow {
                for (i in 0..10) {
                    emit(i) // ����Ԫ�ص�����
                }
            
            }.onEach {
                Log.d(TAG, "onEach:$it")
            }.collect { // ע��֮�󲻻��������
                Log.d(TAG, "value:$it")
            }
```

��5��retry��retryWhen

```
flow {
                for (i in 0..10) {
                    emit(i) // ����Ԫ�ص�����
                }

            }.onEach {
                if (it == 3) throw IllegalArgumentException("Error value:$it") // ���������ֵΪ3ʱ�����׳��쳣
            }.retry(2) { //ָ�����Դ���
                return@retry true // ����ֵ�����Ƿ����ԡ�
            }.collect{
                Log.d(TAG,"$it")
            }
```

retryWhen�п����õ��쳣�͵�ǰ���ԵĴ���

```
flow {
                for (i in 0..10) {
                    emit(i) // ����Ԫ�ص�����
                }

            }.onEach {
                if (it == 3) throw IllegalArgumentException("Error value:$it") // ���������ֵΪ3ʱ�����׳��쳣
            }.retryWhen { cause, count ->
                cause.printStackTrace() // ��ӡ�쳣��־
                // true:��������
                //false��������
                count<=3  // count �������ʽ�������ԵĴ����������������3��
            }.collect {
                Log.d(TAG, "$it")
            }
```

###### 4��ת��������

ת�����������Ƕ����ݽ��б任�ģ���Ҫ��mapϵ����transformϵ�У�map��һ��һ��ת����transform��������map�������е�һ�������ֲ���ȫһ����transform�������ȫ�����������ݣ����й��ˡ� ����ȵȲ��������ԡ�

��1��mapϵ��

- map
- mapLatest
- mapNotNull

```
flow {
                for (i in 0..10) {
                    emit(i) // ����Ԫ�ص�����
                }
            }.map {
                it * it  // �Է����ÿ�����ݽ���ƽ��
            }.collect {
                Log.d(TAG, "$it")
            }
```

��2��transformϵ��

- transform
- transformLatest
- transformWhile

```
flow {
                for (i in 0..10) {
                    emit(i) // ����intԪ��
                }
            }.transform { //�������ڲ� emit �����������µ���
                if (it==1){
                    emit("int to value:$it") //����ת��Ϊstring
                }
            }.collect {
                Log.d(TAG, it)
            }
// ֻ��ӡ������ɸѡ�ģ����·��������
//2021-09-29 13:42:47.836 16065-16065/com.example.algorithm D/CoroutineActivity: int to value:1
```

###### 5�����˲�����

���˲��������ڹ������е�����

��1��filterϵ��

- filter
- filterInstance
- filterNot
- filterNotNull

```
flow {
                for (i in 0..10) {
                    emit(i) // ����intԪ��
                }
            }.filter {
                it ==1  // ����ɸѡ���������ģ������Ķ�����������ɸѡֵΪ1��Ԫ��

            }.collect {
                Log.d(TAG, "$it")
            }
```

��2��drop/takeϵ��

������������Զ���ǰn�����ݣ�������ֻ��ǰn�����ݡ���while��׺�ģ����ʾ�����������жϡ�

- drop
- take
- dropWhile
- takeWhile

```
flow {
                for (i in 0..10) {
                    emit(i) // ����intԪ��
                }
            }.drop(3) //����ǰ����Ԫ��
                    .collect {
                Log.d(TAG, "$it")
            }
```

��3��debounce�����ڷ�����ָ��ʱ���ڵ�ֵֻ�������µ�һ��

```
flow {
                for (i in 0..10) {
                    emit(i) // ����intԪ��
                }
            }.debounce(1000) // ָ��ʱ����ֻ��������һ������
                    .collect {
                Log.d(TAG, "$it") // print 10
            }
```

sample��������debounce�������е��񣬵���ȴ������һ��������ʱ�䣬sample��������ȡ����һ�������ڵ����µ����ݣ��������Ϊdebounce���������������ڵ����ơ�

��4��distinctUntilChanged��������ȵ�Ԫ�ء�Ĭ�Ϲ����Զ�����Ϊɸѡ��

```
flow {
                for (i in 0..10) {
                    emit(i) // ����intԪ��
                }
            }.distinctUntilChanged()
                    .collect {
                        Log.d(TAG, "$it")   // Ԫ�ض���ͬ����ӡ0-10
                    }
```

```
flow {
                for (i in 0..10) {
                    emit(3) // ����11��3
                }
            }.distinctUntilChanged() 
                    .collect {
                        Log.d(TAG, "$it")   // Ԫ�ض�ͬ����ӡ1��
                    }
```

```
data class Person(val age: Int, val name: String)

flow {
                emit(Person(20, "����"))
                emit(Person(21, "槼�"))
                emit(Person(21, "������"))
                emit(Person(22, "��ɪ"))
            }.distinctUntilChanged { old, new ->
                old.age == new.age // �������age��ͬ����Ϊͬһ��
            }
                    .collect {
                        Log.d(TAG, "$it")  
                    }
2021-09-29 14:18:29.005 17522-17522/com.example.algorithm D/CoroutineActivity: Person(age=20, name=����)
2021-09-29 14:18:29.005 17522-17522/com.example.algorithm D/CoroutineActivity: Person(age=21, name=槼�)
2021-09-29 14:18:29.005 17522-17522/com.example.algorithm D/CoroutineActivity: Person(age=22, name=��ɪ)
```

###### 6����ϲ�����

��1��combine������������ͬ��Flow

```
val fl1 = flowOf(1, 2, 3)

            val fl2 = flowOf("a", "b", "c")

            fl1.combine(fl2) { it, str -> // �������������Ԫ������
                "$it:$str" // ÿ��Ԫ��������string��ʽ���
            }.collect {
                Log.d(TAG, it)
            }
2021-09-29 14:26:13.357 17722-17722/com.example.algorithm D/CoroutineActivity: 1:a
2021-09-29 14:26:13.359 17722-17722/com.example.algorithm D/CoroutineActivity: 2:a
2021-09-29 14:26:13.359 17722-17722/com.example.algorithm D/CoroutineActivity: 2:b
2021-09-29 14:26:13.360 17722-17722/com.example.algorithm D/CoroutineActivity: 3:b
2021-09-29 14:26:13.361 17722-17722/com.example.algorithm D/CoroutineActivity: 3:c
```

# Flow���߳��л�

�̵߳��л�ʹ��flowOn��������flowOn������Σ��Լ�flowOn����֮ǰ�����в�������Ч��

```
flow {
                Log.d(TAG, "current Thread:${Thread.currentThread()}")
                emit(2)

            }.map {
                Log.d(TAG, "current Thread:${Thread.currentThread()}")
                it * it
            }.flowOn(Dispatchers.IO).map { // flowOn֮ǰָ��ΪIO�̣߳�֮��Ĭ�����̡߳�
                Log.d(TAG, "current Thread:${Thread.currentThread()}")
                it * 10
            }.collect {
                        Log.d(TAG, "current Thread:${Thread.currentThread()}")
                    }
2021-09-29 14:50:54.034 18358-18392/com.example.algorithm D/CoroutineActivity: current Thread:Thread[DefaultDispatcher-worker-1,5,abs.main]
2021-09-29 14:50:54.034 18358-18392/com.example.algorithm D/CoroutineActivity: current Thread:Thread[DefaultDispatcher-worker-1,5,abs.main]
2021-09-29 14:50:54.037 18358-18358/com.example.algorithm D/CoroutineActivity: current Thread:Thread[abs.main,5,abs.main]
2021-09-29 14:50:54.037 18358-18358/com.example.algorithm D/CoroutineActivity: current Thread:Thread[abs.main,5,abs.main]
```

```
flow {
                Log.d(TAG, "current Thread:${Thread.currentThread()}") // ���߳�
                emit(2)

            }.map {
                Log.d(TAG, "current Thread:${Thread.currentThread()}") // ���߳�
                it * it
            }.flowOn(Dispatchers.IO).map {// flowOn֮ǰָ��ΪIO�߳�
                Log.d(TAG, "current Thread:${Thread.currentThread()}") // ���߳�
                it * 10
            }.flowOn(Dispatchers.Main).map {
                        Log.d(TAG, "current Thread:${Thread.currentThread()}") // ���߳�
                        it / 10
                    }.flowOn(Dispatchers.IO).collect {
                        Log.d(TAG, "current Thread:${Thread.currentThread()}") // ���̣߳�Ĭ�ϣ�
                    }
2021-09-29 14:55:11.190 18518-18554/com.example.algorithm D/CoroutineActivity: current Thread:Thread[DefaultDispatcher-worker-1,5,abs.main]
2021-09-29 14:55:11.191 18518-18554/com.example.algorithm D/CoroutineActivity: current Thread:Thread[DefaultDispatcher-worker-1,5,abs.main]
2021-09-29 14:55:11.223 18518-18518/com.example.algorithm D/CoroutineActivity: current Thread:Thread[abs.main,5,abs.main]
2021-09-29 14:55:11.224 18518-18555/com.example.algorithm D/CoroutineActivity: current Thread:Thread[DefaultDispatcher-worker-2,5,abs.main]
2021-09-29 14:55:11.268 18518-18518/com.example.algorithm D/CoroutineActivity: current Thread:Thread[abs.main,5,abs.main]
```

# ȡ��Flow

��õķ�ʽ����ͨ��withTimeoutOrNull��ȡ��Flow��Flow��ȡ����ʵ���Ͼ���������Э�̵�ȡ����

```
val job = Job()
        val scope = CoroutineScope(job)
        scope.launch(Dispatchers.Main) {

            withTimeoutOrNull(3000) { // ָ��ʱ��ȡ��Э�̣�Flow��ȡ����ʵ���Ͼ���������Э�̵�ȡ��
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

# Flow��ͬ��������ģ��

Flow��ͬ����Ĭ�ϳ����£�Flow��û���л��̵߳�ʱ��������Э��������ָ�����̣߳������ͬ����

Flow�ķ�������emit��collect����suspend��������νsuspend���������ǻ���𣬽�CPU��Դ�ó�ȥ������Ƿ���������Ϊsuspend�˾Ϳ�����һ�ã��ø�˭�أ��ø�������Ҫִ�еĺ�����ִ����Ϻ��ٰ���Դ������

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
            // �ɼ���emitһ����collect��һ���������ͬ��������������ǫ�ã�����˭������ִ�У�
            // ����ȥflow�еĴ����collect�еĴ��룬����ͬ��ִ�еġ�
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: onEach:0
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: collect:0
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: onEach:1
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: collect:1
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: onEach:2
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: collect:2
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: onEach:3
            //2021-09-29 15:15:09.312 19078-19078/com.example.algorithm I/CoroutineActivity: collect:3
```

# Flow���첽������ģ��

�������Ǹ�Flow����һ���߳��л�����Flowִ�������߳���������Flow�ͱ�����첽������ģ���ˡ�

��ͬ���߳̿϶����첽�ġ�

����������ûʲô�����ˣ�����flow������ִ�У�������Ĵ�������û��delay��������ͬ��ִ�еģ�ִ�е�ͬʱ��collect�����߳̽��м�����

```
flow {
                for (i in 0..3) {
                   // delay(1000)  ���������䣬��ʵ��ͬ��������ģ��
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

ע�⣺����ʹ��flowOn���л��̣߳�ʹ��channelFlowҲ����ʵ���첽������ģ�͡�

[�ο�1](https://mp.weixin.qq.com/s/hpLTj8SiirGvw2hsPLL-1g)

[�ο�2](https://www.kotlincn.net/docs/reference/coroutines/flow.html)
