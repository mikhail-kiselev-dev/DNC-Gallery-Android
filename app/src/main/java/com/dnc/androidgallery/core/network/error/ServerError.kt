package com.dnc.androidgallery.core.network.error

import java.io.IOException

open class ServerError(val code: Int) : IOException("Unexpected server error with code: $code")
