package sam


fun main() {

    val map = hashMapOf("userName" to "kate", "userSex" to "girl")

    val (key,value) = map

    for ((key,value) in map){
        println("key:$key value:$value")
    }
}





