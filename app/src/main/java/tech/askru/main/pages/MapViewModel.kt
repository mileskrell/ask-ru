package tech.askru.main.pages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
    var numClicks = MutableLiveData(0)
}
