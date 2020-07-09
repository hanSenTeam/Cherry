package com.victor.lib.coremodel.http.locator

import androidx.annotation.VisibleForTesting
import com.victor.lib.coremodel.http.ApiService
import com.victor.lib.coremodel.http.repository.IRepository

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ServiceLocator
 * Author: Victor
 * Date: 2020/7/8 下午 05:14
 * Description: Super simplified service locator implementation to allow us to
 * replace default implementationsfor testing.
 * -----------------------------------------------------------------
 */
interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = NetServiceLocator()
                }
                return instance!!
            }
        }

        /**
         * Allows tests to replace the default implementations.
         */
        @VisibleForTesting
        fun swap(locator: ServiceLocator) {
            instance = locator
        }
    }

    fun getRepository(): IRepository

    fun getRequestApi(): ApiService
}