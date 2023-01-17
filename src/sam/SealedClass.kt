package sam

sealed class SealedClass {
    data class LoadingState(val state: String):SealedClass()
    object Login:SealedClass()
}