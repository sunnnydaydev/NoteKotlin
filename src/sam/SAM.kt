package sam



fun main() {

}


open class User(open val userName:String)
class Person(val name:String):User(name){
    // 重写父类的userName字段
    override val userName: String
        get() = name
}



