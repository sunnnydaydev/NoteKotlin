package inline;

import dataclass.Password;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/**
 * Create by SunnyDay 2023/10/10 21:44:04
 **/
public class InlineClassDemoKt {
    public static final void main() {
        String password = Password.constructor-impl("12345678Aa");
        String var1 = "password-value:" + password;
        System.out.println(var1);
        Password.yourLength-impl(password);
    }

    public static void main(String[] var0) {
        main();
    }

    public final class Password {

        @NotNull
        private final String value;

        @NotNull
        public final String getValue() {
            return this.value;
        }

        private Password(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            super();
            this.value = value;
        }

        /**
         * 1、创建内联类对象时，会执行这段代码的调用。返回的是被包装类的对象
         */
        @NotNull
        public static String constructor_impl(@NotNull String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            String var1 = "value:" + value;
            System.out.println(var1);
            return value;
        }

        /**
         * 2、内联类的普通成员（非主构造函数中的）会被编译为静态成员
         * 调用时就是进行的静态调用。
         * */
        public static final int getLength_impl(String $this) {
            return $this.length();
        }

        public static final void yourLength_impl(String $this) {
            String var1 = "yourLength -> password-value-length:" + getLength-impl($this);
            System.out.println(var1);
        }

        public static final dataclass.Password box_impl(String v) {
            Intrinsics.checkNotNullParameter(v, "v");
            return new dataclass.Password(v);
        }

        public static String toString_impl(String var0) {
            return "Password(value=" + var0 + ")";
        }

        public static int hashCode_impl(String var0) {
            return var0 != null ? var0.hashCode() : 0;
        }

        public static boolean equals_impl(String var0, Object var1) {
            if (var1 instanceof dataclass.Password) {
                String var2 = ((dataclass.Password)var1).unbox-impl();
                if (Intrinsics.areEqual(var0, var2)) {
                    return true;
                }
            }

            return false;
        }

        public static final boolean equals_impl0(String p1, String p2) {
            return Intrinsics.areEqual(p1, p2);
        }

        public final String unbox_impl() {
            return this.value;
        }

        public static final dataclass.Password box_impl(String v) {
            Intrinsics.checkNotNullParameter(v, "v");
            return new dataclass.Password(v);
        }

        /**
         * 3、toString、hashCode、equals也是运行时静态调用
         * */
        public String toString() {
            return toString-impl(this.value);
        }

        public int hashCode() {
            return hashCode-impl(this.value);
        }

        public boolean equals(Object var1) {
            return equals-impl(this.value, var1);
        }
    }
}
