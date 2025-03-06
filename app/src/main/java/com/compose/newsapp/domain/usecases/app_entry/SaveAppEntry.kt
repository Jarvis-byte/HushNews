package com.compose.newsapp.domain.usecases.app_entry

import com.compose.newsapp.domain.manager.LocalUserManger


class SaveAppEntry(
    private val localUserManger: LocalUserManger
) {

    suspend operator fun invoke(){
        localUserManger.saveAppEntry()
    }

}