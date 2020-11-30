package com.example.businessv1.frame.presentation.utils

import android.graphics.Color
import android.view.View
import com.example.businessv1.R
import com.example.businessv1.business.domain.state.DataState
import com.google.android.material.snackbar.Snackbar


fun showErrorSnackBar(view: View, error: DataState<Any>) {
    val context = view.context
    when (error) {
        is DataState.Error -> {
            showErrorSnackBar(
                view,
                error.exception.message ?: context.getString(R.string.something_went_wrong)
            )
        }
        else -> showErrorSnackBar(
            view,
            context.getString(R.string.something_went_wrong)
        )
    }
}


fun showErrorSnackBar(view: View, msg: String) {
    if (msg.isNotBlank()) {
        Snackbar.make(
            view,
            msg,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}