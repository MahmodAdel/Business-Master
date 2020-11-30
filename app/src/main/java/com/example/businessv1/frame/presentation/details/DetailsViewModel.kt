package com.example.businessv1.frame.presentation.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.businessv1.business.domain.model.Business
import com.example.businessv1.business.domain.model.BusinessDetails
import com.example.businessv1.business.domain.state.DataState
import com.example.businessv1.business.interactors.GetBusinessList
import com.example.businessv1.frame.presentation.business.BusinessListAdapter
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

    private val _dataState: MutableLiveData<BusinessDetails> = MutableLiveData()

    val dataState: LiveData<BusinessDetails>
        get() = _dataState
    val error: LiveData<DataState<Any>>
        get() = _error
    private val _error: MutableLiveData<DataState<Any>> = MutableLiveData()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    fun setStateEvent(mainStateEvent: BusinessEvent,id:String){
        viewModelScope.launch {
            when(mainStateEvent){
                is BusinessEvent.GetBusinessEvent -> {
                    getBusiness.getBusinessDetails(id)
                        .onEach { dataState ->
                            when (dataState) {
                                is DataState.Success<BusinessDetails> -> {
                                    _dataState.postValue(dataState.data)
                                    _isLoading.value = false
                                }
                                is DataState.Error -> {
                                    _error.postValue(dataState)
                                    _isLoading.value = false
                                }
                                is DataState.Loading -> {
                                    _isLoading.value = true
                                }
                            }
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
    fun onErrorMsgDisplay() {
        _error.value = null
    }

}