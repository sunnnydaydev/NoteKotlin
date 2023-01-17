package sam

sealed class SealedClass {
    data class LoadingState(val state: String):SealedClass()
    object Login:SealedClass()
}

fun test(sealedClass:  SealedClass){
    when(sealedClass){
        is SealedClass.LoadingState ->{
            val loadingState = sealedClass.state
            if (loadingState=="Loading"){
                // show progress bar
            }else{
                // hide progress bar
            }
        }
        is SealedClass.Login ->{
            // do login
        }
        // 这里不用else 所有的case 已经覆盖
    }
}