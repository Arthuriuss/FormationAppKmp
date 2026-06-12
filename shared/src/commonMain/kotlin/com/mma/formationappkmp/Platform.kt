package com.mma.formationappkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform