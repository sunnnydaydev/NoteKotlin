# 密封类 sealed class

密封类的功能与枚举的功能十分相似，都是用来表示受限的类继承结构。但每个枚举常量只存在一个实例，而密封类的一个子类可以有可包含状态的多个实例。

###### 定义

```kotlin
sealed class SealedClass {
    data class LoadingState(val state: String):SealedClass()
    object Login:SealedClass()
}
```

如何理这句话呢 ？ -> "但每个枚举常量只存在一个实例，而密封类的一个子类可以有可包含状态的多个实例。" 看个栗子🌰

每个枚举常量成员就是一个枚举实例，这是一个对象。而密封类的成员是一个密封子类，既然是类就可通过构造接受参数，相对来说密封类可以有多种状态。

```kotlin
enum class EnumClass {
   LOADING_START,
   LOADING,
   LOADING_FINISH,
}
```
如上这个代码我们只能简单实用下枚举实例，想要动态传参时，我们可能需要这样修改代码：

```kotlin
enum class EnumClass(private val id: Int) {
   TEST(-1),
   LOADING_START(1),
   LOADING(2),
   LOADING_FINISH(3),
}
```
这时会有如下问题：
- id参数我们用不了
- 仅仅TEST需要个参数，结果其他实例也要被迫传递一个

再回顾下上文的SealedClass你便会发现kotlin 的密封了再这点强大多了~

###### 观察下生成的密封类java代码

```java
// 1、abstract类
public abstract class SealedClass {
    
    //2、构造私有了，不允许直接创建密封类的实例
   private SealedClass() {
   }

   // $FF: synthetic method
   public SealedClass(DefaultConstructorMarker $constructor_marker) {
      this();
   }
   
   // 3、子类 final class
   public static final class LoadingState extends SealedClass {
      @NotNull
      private final String state;

      @NotNull
      public final String getState() {
         return this.state;
      }

      public LoadingState(@NotNull String state) {
         Intrinsics.checkNotNullParameter(state, "state");
         super((DefaultConstructorMarker)null);
         this.state = state;
      }

      @NotNull
      public final String component1() {
         return this.state;
      }

      @NotNull
      public final LoadingState copy(@NotNull String state) {
         Intrinsics.checkNotNullParameter(state, "state");
         return new LoadingState(state);
      }

      // $FF: synthetic method
      public static LoadingState copy$default(LoadingState var0, String var1, int var2, Object var3) {
         if ((var2 & 1) != 0) {
            var1 = var0.state;
         }

         return var0.copy(var1);
      }

      /**
       * 4、自动生成toString、hashCode、equals
       * */
      @NotNull
      public String toString() {
         return "LoadingState(state=" + this.state + ")";
      }

      public int hashCode() {
         String var10000 = this.state;
         return var10000 != null ? var10000.hashCode() : 0;
      }

      public boolean equals(@Nullable Object var1) {
         if (this != var1) {
            if (var1 instanceof LoadingState) {
               LoadingState var2 = (LoadingState)var1;
               if (Intrinsics.areEqual(this.state, var2.state)) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      }
   }
    // 3、子类final
   public static final class Login extends SealedClass {
      @NotNull
      public static final Login INSTANCE;

      private Login() {
         super((DefaultConstructorMarker)null);
      }

      static {
         Login var0 = new Login();
         INSTANCE = var0;
      }
   }
}
```

###### 注意

- 要声明一个密封类，需要在类名前面添加 sealed 修饰符。
- 虽然密封类也可以有子类，但是所有子类都必须在与密封类自身相同的文件中声明。 在 Kotlin 1.1 之前， 该规则更加严格：子类必须嵌套在密封类声明的内部。
- 扩展密封类子类的类（间接继承者）可以放在任何位置，而无需在同一个文件中
- 使用密封类的关键好处在于使用 when 表达式 的时候，如果能够验证语句覆盖了所有情况，就不需要为该语句再添加一个 else 子句了。当然，这只有当你用 when 作为表达式（使用结果）而不是作为语句时才有用


###### when 应用

```kotlin
sealed class SealedClass {
    data class LoadingState(val state: String):SealedClass()
    object Login:SealedClass()
}

fun test(sealedClass:  SealedClass){
    when(sealedClass){
        is SealedClass.LoadingState ->{
            val loadingState = sealedClass.state
            if (loadingState=="Loading"){
                // show progress bar
            }else{
                // hide progress bar
            }
        }
        is SealedClass.Login ->{
            // do login
        }
        // 这里不用else 所有的case 已经覆盖
    }
}
```

可以了解下when生成的代码->

```java
public final class SealedClassKt {
   public static final void test(@NotNull SealedClass sealedClass) {
      Intrinsics.checkNotNullParameter(sealedClass, "sealedClass");
      if (sealedClass instanceof SealedClass.LoadingState) {
         String loadingState = ((SealedClass.LoadingState)sealedClass).getState();
         Intrinsics.areEqual(loadingState, "Loading");
      } else if (sealedClass instanceof SealedClass.Login) {
      }

   }
}
```
生成的代码是 if -> else if ,,,,形式，已经覆盖每一种形式!

[参考](https://juejin.cn/post/7044708611544596516)