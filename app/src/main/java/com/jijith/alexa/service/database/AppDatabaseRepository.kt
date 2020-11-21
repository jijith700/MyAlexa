package com.jijith.alexa.service.database

import com.jijith.alexa.vo.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class AppDatabaseRepository(private val userDao: UserDao) {

    fun setRefreshToken(refreshToken: String?) {
        GlobalScope.launch (Dispatchers.Main) {
            val user = User(1, "", "", refreshToken);
            userDao.insert(user)
            Timber.d("user %s", user);
        }
    }

    fun getRefreshToken() = runBlocking {
        userDao.getRefreshToken(1)
    }

    fun clearRefreshToken() {
        GlobalScope.launch (Dispatchers.Main) {
//            userDao.delete(userDao.get(1))
        }
    }
}