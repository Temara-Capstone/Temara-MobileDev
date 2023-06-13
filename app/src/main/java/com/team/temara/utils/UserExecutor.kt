package com.team.temara.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class UserExecutor {
    val diskIO: Executor = Executors.newSingleThreadExecutor()
}