package abs

import java.util.ResourceBundle


fun main() {
    val uiState = object : UIState {
        override fun isSelected(): Boolean {
            return true
        }
    }

    val uiState2 = UIState {
       true
    }
}


fun interface UIState {
    fun isSelected(): Boolean
}





