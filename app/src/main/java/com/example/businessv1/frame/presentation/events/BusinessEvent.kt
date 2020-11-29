package com.example.businessv1.frame.presentation.events

sealed class BusinessEvent{

    object GetBusinessEvent: BusinessEvent()

    object None: BusinessEvent()
}