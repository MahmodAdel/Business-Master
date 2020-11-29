package com.example.businessv1.frame.presentation.details

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
class DetailsViewModel
@ViewModelInject
constructor(
    private val getBusiness: GetBusinessList,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _dataState: MutableLiveData<DataState<Business>> = MutableLiveData()

    val dataState: LiveData<DataState<Business>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: BusinessEvent,id:String){
        viewModelScope.launch {
            when(mainStateEvent){
                is BusinessEvent.GetBusinessEvent -> {
                    getBusiness.getBusinessDetails(id)
                        .onEach {dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

}