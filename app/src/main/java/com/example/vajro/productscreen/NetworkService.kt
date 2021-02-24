package com.example.vajro.productscreen

import android.os.Build
import android.util.Log
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.HashSet
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class NetworkService {

    private val client = OkHttpClient()


    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })
    }
    companion object {
        lateinit var httpClientAPI: Retrofit

        val instance: Retrofit by lazy {



            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.mocky.io/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getCustomOkHttpClient())
                .build()
            retrofit

        }


        var BASE_URL_ADDRESS = "https://www.mocky.io/v2/"

        fun getRetrofitClient(): Retrofit? {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL_ADDRESS)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }


        fun getCustomOkHttpClient(): OkHttpClient? {
            return try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()
                builder.connectTimeout(2, TimeUnit.MINUTES)
                builder.writeTimeout(2, TimeUnit.MINUTES)
                builder.readTimeout(2, TimeUnit.MINUTES)
                enableTls12OnPreLollipop(builder)!!.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder? {
            if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
                try {
                    val sc = SSLContext.getInstance("TLSv1.2")
                    sc.init(null, null, null)
//                    client.sslSocketFactory(Tls12SocketFactory(sc.socketFactory))
                    val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build()
                    val specs: MutableList<ConnectionSpec> = ArrayList()
                    specs.add(cs)
                    specs.add(ConnectionSpec.COMPATIBLE_TLS)
                    specs.add(ConnectionSpec.CLEARTEXT)
                    client.connectionSpecs(specs)
                } catch (exc: java.lang.Exception) {
                    Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
                }
            }
            return client
        }
    }



}



