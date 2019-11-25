package com.padmitriy.srserdc

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.android.internal.app.IBatteryStats

class LogService : Service() {
    var TAG = "LogJavaService"
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        getServiceManager()
    }

    // 1 printing all running services
    private fun getServiceManager() {
        try {
            // 1 printing all running services
            val listServicesMethod =
                Class.forName("android.os.ServiceManager")
                    .getMethod("listServices")
            val list =
                listServicesMethod.invoke(null) as Array<String>
            list.forEach { runningServiceName ->
                Log.i(TAG, runningServiceName)
            }

            // 2 getting BatterryStats service
            val getServiceMethod =
                Class.forName("android.os.ServiceManager")
                    .getMethod("getService", String::class.java)
            val batterystats = getServiceMethod.invoke(null, "batterystats") as IBinder

            // 3 AIDL not working
            binder = IBatteryStats.Stub.asInterface(batterystats)

            Log.i(TAG, binder.computeBatteryTimeRemaining().toString() + "")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private var binder: IBatteryStats = object : IBatteryStats.Stub() {
        override fun asBinder(): IBinder? {
            return null
        }

        override fun computeBatteryTimeRemaining(): Long {
            var remainingTime: Long = 0
            try {
                val getServiceMethod =
                    Class.forName("android.internal.os.BatteryStatsImpl")
                        .getMethod("isCharging")
                remainingTime = getServiceMethod.invoke(null) as Long
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return remainingTime
        }

        override fun computeChargeTimeRemaining(): Long {
            return 0
        }
    }
}