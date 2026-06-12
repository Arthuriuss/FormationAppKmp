package com.mma.formationappkmp.api

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

actual fun HttpClientConfig<*>.configurePlatformSsl() {
    (this as? HttpClientConfig<OkHttpConfig>)?.engine {
        config {
            val trustAllCerts = object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
            val sslContext = SSLContext.getInstance("SSL").apply {
                init(null, arrayOf(trustAllCerts), SecureRandom())
            }
            sslSocketFactory(sslContext.socketFactory, trustAllCerts)
            hostnameVerifier { _, _ -> true }
        }
    }
}