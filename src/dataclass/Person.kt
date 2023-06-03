package dataclass

data class Person(val age:Int=10,val name: String){}

fun main() {
    val person = Person(name = "Tom")
    val copyPerson = person.copy(name = "Kate")
    println("person[name:${person.name},hashCode:${person.hashCode()}]")
    println("copyPerson[name:${copyPerson.name},hashCode:${copyPerson.hashCode()}]")

    val (test1,test2) = person
    println("test1：$test1")
    println("test2：$test2")
}



