package com.mma.formationappkmp.api

import io.ktor.client.HttpClientConfig

// Cette fonction sera implémentée différemment sur Android/JVM et iOS
expect fun HttpClientConfig<*>.configurePlatformSsl()