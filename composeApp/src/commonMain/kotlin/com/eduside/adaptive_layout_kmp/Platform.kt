package com.eduside.adaptive_layout_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform