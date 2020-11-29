package com.example.businessv1.frame.presentation.business

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.state.DataState
import com.example.businessv1.business.interactors.GetBusinessList
import com.example.businessv1.frame.presentation.events.BusinessEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class BusinessViewModel
@ViewModelInject
constructor(
    private val getBusiness: GetBusinessList,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    public var offset:Int = 0
    private val _dataState: MutableLiveData<DataState<List<Business>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Business>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: BusinessEvent,query:String,offset:Int,limit:Int){
        viewModelScope.launch {
            when(mainStateEvent){
                is BusinessEvent.GetBusinessEvent -> {
                    getBusiness.getBusiness(query,offset,limit)
                        .onEach {dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

}