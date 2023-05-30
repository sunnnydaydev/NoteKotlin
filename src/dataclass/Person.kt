package dataclass

data class Person(val name: String)

fun main() {
    val person = Person("Tom")
    val copyPerson = person.copy(name = "Kate")
    println("person[name:${person.name},hashCode:${person.hashCode()}]")
    println("copyPerson[name:${copyPerson.name},hashCode:${copyPerson.hashCode()}]")
}

data class ViewState(
    val loadingState: String = "empty",//主页加载状态
    val jsonString: String = "{}", // 主页数据
    val commitButtonEnable: Boolean = false // 主页按钮状态
)

class MainActivity {

    /**
     * 模拟回调，当ViewModel中的ViewState更改时这里会收到回调。
     * */
    fun onViewStateUpdate(state: ViewState) {
        // todo 数据驱动UI
        // 0、进度条处理（根据loadingState）
        // 1、填充主页数据（根据jsonString）
        // 2、处理按钮状态（根据commitButtonEnable）
    }
}

class MainViewModel {

    var viewState: ViewState = ViewState()

    /**
     * 1、模拟网络请求，请求主页面数据。从网路拿到数据，回流给UI层
     * */
    fun handleNetData() {
        viewState = viewState.copy(
            loadingState = "data", // 加载状态改为data，代表请求网络成功拿到数据
            jsonString = "{\"firstName\": \"Brett\"}" // 从网络获取到的数据
        )
    }

    /**
     * 模拟按钮状态的处理，当用户输入的内容大于8位时按钮可用。
     * 2、注意这里未更改json值，因为根据业务json不应该改，所以copy时不用重新赋值即可代表使用上次最新的值。
     * 我们更新数据copy时只copy我们想要更新的即可
     * */
    fun changeButtonStateByContent(userInput: String){
        viewState = viewState.copy(loadingState = "empty", commitButtonEnable =  userInput.length > 8)
    }

}
