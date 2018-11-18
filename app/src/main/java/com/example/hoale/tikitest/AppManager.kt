package com.example.hoale.tikitest

import android.os.AsyncTask
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.lang.Exception
import java.net.URL


object AppManager {
    @JvmStatic
    fun getUrl(url: String, callback: IResponseResult?) {
        val httpGet = object : AsyncTask<String, Void, String>() {
            override fun doInBackground(vararg params: String?): String {
                try {
                    val newUrl = URL(params[0])
                    val request = Request.Builder()
                        .url(newUrl)
                        .build()
                    val responseRequest = OkHttpClient().newCall(request).execute()
                    return responseRequest.body().string()

                } catch (e: Exception) {
                }
                return ""
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                callback?.onSuccess(result)
            }
        }
        httpGet.execute(url)
    }

    interface IResponseResult {
        fun onSuccess(strJson: String?)
    }
}