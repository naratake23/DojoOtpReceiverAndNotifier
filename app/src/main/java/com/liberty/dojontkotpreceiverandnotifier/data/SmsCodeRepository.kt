package com.liberty.dojontkotpreceiverandnotifier.data

import kotlinx.coroutines.flow.Flow


interface SmsCodeRepository{
    val lastCodeFlow: Flow<String>
    suspend fun saveCode(code: String)
}