package com.pankaj.halodoc.model

data class Response<T, U>(
    var obj: T? = null,
    var failure: U? = null
)