package com.example.phonenetworkinfo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.*
import android.util.Log.d
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_about)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_PHONE_STATE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                1
            )
        }

        val IMEI = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tm.getImei()
        } else {
            "Cant get IMEI (low API version)"
        }

        val PLMN = tm.getSimOperator()

        val IMSI = tm.subscriberId

        val networkTypeInt = tm.networkType

        val networkTypeStr = getNetworkType(networkTypeInt)

        val ci = tm.cellLocation
        // TODO: CI might be wrong, need to find another way to obtain it
        val bundle = Bundle()

        d("IME-IMS-PLM-TYP-CI", "$IMEI - $IMSI - $PLMN - $networkTypeStr - $ci")
    }

    private fun getNetworkType(type: Int): String {
        when (type) {
            TelephonyManager.NETWORK_TYPE_1xRTT -> return "1xRTT"
            TelephonyManager.NETWORK_TYPE_CDMA -> return "CDMA"
            TelephonyManager.NETWORK_TYPE_EDGE -> return "EDGE"
            TelephonyManager.NETWORK_TYPE_EVDO_0 -> return "EVDO_0"
            TelephonyManager.NETWORK_TYPE_EVDO_A -> return "EVDO_A"
            TelephonyManager.NETWORK_TYPE_GPRS -> return "GPRS"
            TelephonyManager.NETWORK_TYPE_HSDPA -> return "HSDPA"
            TelephonyManager.NETWORK_TYPE_HSPA -> return "HSPA"
            TelephonyManager.NETWORK_TYPE_HSUPA -> return "HSUPA"
            TelephonyManager.NETWORK_TYPE_UMTS -> return "UMTS"
            TelephonyManager.NETWORK_TYPE_EHRPD -> return "EHRPD"
            TelephonyManager.NETWORK_TYPE_EVDO_B -> return "EVDO_B"
            TelephonyManager.NETWORK_TYPE_HSPAP -> return "HSPAP"
            TelephonyManager.NETWORK_TYPE_IDEN -> return "IDEN"
            TelephonyManager.NETWORK_TYPE_LTE -> return "LTE"
            TelephonyManager.NETWORK_TYPE_UNKNOWN -> return "UNKNOWN"
            else -> return "FAULT"
        }
    }
}
