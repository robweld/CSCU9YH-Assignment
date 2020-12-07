package uk.ac.stir.cs.UnitConversionApp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConversionViewModel : ViewModel() {
    /**
     * creates a mutable liveData for ConversionInfo for the methods that
     * observers of this to update whenever it is changed
     */
    val selectedConversion: MutableLiveData<ConversionInfo> by lazy {
        MutableLiveData<ConversionInfo>()
    }
}