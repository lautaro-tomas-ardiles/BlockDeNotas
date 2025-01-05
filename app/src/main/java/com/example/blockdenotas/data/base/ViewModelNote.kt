package com.example.blockdenotas.data.base

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blockdenotas.ui.theme.*

class ViewModelNote: ViewModel() {

    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title

    private val _content = MutableLiveData("")
    val content: LiveData<String> = _content

    private val _backgroundColor = MutableLiveData(black20)
    val backgroundColor: LiveData<Color> = _backgroundColor

    private val _fontSize = MutableLiveData(20)
    val fontSize: LiveData<Int> = _fontSize

    // Métodos para actualizar los valores
    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun updateContent(newContent: String) {
        _content.value = newContent
    }

    fun updateBackgroundColor(newColor: Color) {
        _backgroundColor.value = newColor
    }

    fun updateFontSize(newFontSize: Int) {
        _fontSize.value = newFontSize
    }
}