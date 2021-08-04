package ir.nwise.app.common

/**
 *  Exception thrown in case there's no internet connection
 * */
data class NoInternetConnectionException(override val message: String = "There is no internet connection") : Exception()