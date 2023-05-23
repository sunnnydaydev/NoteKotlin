package base

/**
 * Create by SunnyDay 2023/04/26 20:54:41
 **/




private var Animal.type: String
    get() = "fish" //1、get 相当于赋值
    set(value) {
       // field = value //2、field 不能用， type 无法通过field 更改
        test = "hahha" // 可以更改其他属性值
    }




class Animal {
    var test =""
    private var name = ""


    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String {
        return name
    }
}
