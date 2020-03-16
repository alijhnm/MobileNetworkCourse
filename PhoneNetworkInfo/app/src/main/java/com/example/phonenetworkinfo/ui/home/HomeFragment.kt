package com.example.phonenetworkinfo.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phonenetworkinfo.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        getRequiredPermissions()

        val networkData = getNetworkData()

        initRecyclerView(rootView, networkData)

        return rootView
    }

    private fun initRecyclerView(view: View, data: List<Pair<String, String>>) {
        val recyclerView = view.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = ItemAdapter(data)
    }

    private fun getRequiredPermissions() {
        val permission = context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.READ_PHONE_STATE
            )
        }

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                1
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getNetworkData(): List<Pair<String, String>> {
        val tm = activity?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var data = emptyList<Pair<String, String>>()
        val IMEI = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tm.getImei()
        } else {
            "Cant get IMEI (low API version)"
        }
        data = data.plusElement(Pair("IMEI", IMEI.toString()))

        val PLMN = tm.getSimOperator()
        data = data.plusElement(Pair("PLMN", PLMN))

        val IMSI = tm.subscriberId
        data = data.plusElement(Pair("IMSI", IMSI))

        val networkTypeInt = tm.networkType
        val networkTypeStr = getNetworkType(networkTypeInt)
        data = data.plusElement(Pair("Network Type", networkTypeStr))

        val ci = tm.cellLocation
        data = data.plusElement(Pair("Cell Identitiy", ci.toString()))
        return data
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