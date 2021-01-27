package com.vikination.chatapponeononesample.data.model

data class Response<out T>(val status :Status, val data :T?, val message :String?) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object{
        fun <T> success(data :T, message: String? = null) :Response<T> = Response(Status.SUCCESS, data, message)
        fun <T> error(message: String, data :T? = null) :Response<T> = Response(Status.ERROR, data, message)
        fun <T> loading(data :T? = null) :Response<T> = Response(Status.LOADING, data, null)
    }
}