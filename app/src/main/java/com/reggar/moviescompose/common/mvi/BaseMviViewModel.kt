package com.reggar.moviescompose.common.mvi

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<STATE, EVENT>(
    initialState: STATE
) : ViewModel() {

    private val _state = mutableStateOf(initialState)
    val state: State<STATE> = _state

    abstract fun onEvent(event: EVENT)

    protected fun setState(state: STATE) {
        viewModelScope.launch(context = Dispatchers.Main) {
            _state.value = state
        }
    }
}
