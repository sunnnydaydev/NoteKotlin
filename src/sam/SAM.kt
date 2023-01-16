package sam



fun main() {
    val (aName,aAge) = Animal("",1)
    println("name:$aName")
    println("age:$aAge")
}
data class Animal(val name: String,val age:Int)




