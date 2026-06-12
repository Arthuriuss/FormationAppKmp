package com.mma.formationappkmp.api

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.DarwinClientEngineConfig
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import platform.Foundation.NSURLAuthenticationMethodServerTrust
import platform.Foundation.NSURLCredential
import platform.Foundation.NSURLSessionAuthChallengePerformDefaultHandling
import platform.Foundation.NSURLSessionAuthChallengeUseCredential
import platform.Foundation.credentialForTrust
import platform.Foundation.serverTrust

@OptIn(ExperimentalForeignApi::class)
actual fun HttpClientConfig<*>.configurePlatformSsl() {
    (this as? HttpClientConfig<DarwinClientEngineConfig>)?.engine {
        handleChallenge { _, _, challenge, completionHandler ->
            val serverTrust = challenge.protectionSpace.serverTrust
            if (challenge.protectionSpace.authenticationMethod == NSURLAuthenticationMethodServerTrust && serverTrust != null) {
                completionHandler(
                    NSURLSessionAuthChallengeUseCredential.convert(),
                    NSURLCredential.credentialForTrust(serverTrust)
                )
            } else {
                completionHandler(NSURLSessionAuthChallengePerformDefaultHandling.convert(), null)
            }
        }
    }
}