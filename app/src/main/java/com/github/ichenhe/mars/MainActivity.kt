package com.github.ichenhe.mars

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import java.io.File

class MainActivity : AppCompatActivity() {
    companion object {
        init {
            System.loadLibrary("c++_shared")
            System.loadLibrary("marsxlog")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.setLogImp(Xlog())
        val logDir = File(filesDir, "xlog")
        Log.appenderOpen(Log.LEVEL_INFO, Xlog.AppednerModeAsync, "", logDir.absolutePath, "", 3)

        Log.i("MainActivity", "Init xlog successfully")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.appenderClose()
    }
}