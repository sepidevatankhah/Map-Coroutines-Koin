package ir.nwise.app.ui.error

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ir.nwise.app.R
import ir.nwise.app.common.NoInternetConnectionException

interface ErrorType {
    companion object {
        /**
         * Helper method that returns the appropriate [ErrorType] depending
         * on the instance of the passed throwable.
         */
        fun fromThrowable(throwable: Throwable?) = when (throwable) {
            is NoInternetConnectionException ->  NoInternet
            else ->  General
        }
    }

    @get:DrawableRes
    val icon: Int

    @get:StringRes
    val title: Int

    @get:StringRes
    val message: Int

    object General : ErrorType {
        override val icon = R.drawable.ic_error
        override val title = R.string.general_error_title
        override val message = R.string.general_error_msg
    }

    object NoInternet : ErrorType {
        override val icon = R.drawable.ic_no_internet
        override val title = R.string.no_internet_error_title
        override val message = R.string.no_internet_error_msg
    }
}