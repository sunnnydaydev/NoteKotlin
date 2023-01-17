# 密封类 sealed class

密封类的功能与枚举的功能十分相似，都是用来表示受限的类继承结构。但每个枚举常量只存在一个实例，而密封类的一个子类可以有可包含状态的多个实例。

###### 定义

```kotlin
sealed class SealedClass {
    data class LoadingState(val state: String):SealedClass()
    object Login:SealedClass()
}
```


###### 观察下生成的密封类java代码

```java
// 1、abstract类
public abstract class SealedClass {
    
   private SealedClass() {
   }

   // $FF: synthetic method
   public SealedClass(DefaultConstructorMarker $constructor_marker) {
      this();
   }
   
   // 2、子类 final class
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
       * 自动生成toString、hashCode、equals
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
    // 2、子类final
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
- 虽然密封类也可以有子类，但是所有子类都必须在与密封类自身相同的文件中声明。
- 在 Kotlin 1.1 之前， 该规则更加严格：子类必须嵌套在密封类声明的内部。