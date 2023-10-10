package dataclass

/**
 * Create by SunnyDay 2023/10/10 21:08:33
 **/
fun main() {
   val password = Password("12345678Aa")
    println("password-value:${password.value}")
    password.yourLength()
}


class Test()

@JvmInline
value class Password(val value: String){
    init {
        println("value:${value}")
    }
    val length:Int get()   = value.length
    fun yourLength(){
        println("yourLength -> password-value-length:${length}")
    }
}