package com.example.businessv1.frame.presentation.business

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
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
    @Assisted private val state: SavedStateHandle
): ViewModel() {
    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
    val businessFavList: LiveData<List<Business>>
        get() = _businessFavList
    private val _businessFavList: MutableLiveData<List<Business>> = MutableLiveData()

    private val _dataState: MutableLiveData<DataState<List<Business>>> = MutableLiveData()

    val error: LiveData<DataState<Any>>
        get() = _error
    private val _error: MutableLiveData<DataState<Any>> = MutableLiveData()

    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun setStateEvent(mainStateEvent: BusinessEvent, query: String, offset: Int, limit: Int){
        viewModelScope.launch {
            when(mainStateEvent){
                is BusinessEvent.GetBusinessEvent -> {
                    getBusiness.getBusiness(query, offset, limit)
                        .onEach { dataState ->
                            when (dataState) {
                                is DataState.Success<List<Business>> -> {
                                    _dataState.postValue(dataState)
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
                BusinessEvent.None -> TODO()
            }
        }
    }
    fun onErrorMsgDisplay() {
        _error.value = null
    }
    val businessList = currentQuery.switchMap { queryString ->
        getBusiness.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun firstFeach(mainStateEvent: BusinessEvent, query: String) {
        when(mainStateEvent){
            is BusinessEvent.GetBusinessEvent -> {
                currentQuery.value = query

            }
            is BusinessEvent.None -> {

            }
        }
    }
    suspend fun insertBusinessFav(business: Business) {
        getBusiness.insertFavorite(business)
    }

    suspend fun deleteBusinessFav(business: Business) {
        getBusiness.deleteFavorite(business)
    }
    suspend fun getAllBusinessFav() {
        _businessFavList.postValue(getBusiness.getAllFavorite())
    }
    fun getFavoritList(){
        viewModelScope.launch {
            getAllBusinessFav()
        }
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "NewYork"
    }
}