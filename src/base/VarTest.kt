package base

/**
 * Create by SunnyDay 2023/04/26 20:54:41
 **/



fun Animal.desc(){
    // this.getName()
    // this.setName("")
    println("i am animal:")
}

class VarTest{
    /**
     * 为Animal定义一个扩展函数
     * */

    /**
     * 使用扩展函数
     * */
    fun test(){
        Animal().desc()
    }
}


class Person{
    fun test(){
        Animal().desc()
    }
}


class Animal {
    private var name = ""
    private var type = ""

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String {
        return name
    }

    fun setType(type: String) {
        this.type = type
    }

    fun getType(): String {
        return type
    }
}
