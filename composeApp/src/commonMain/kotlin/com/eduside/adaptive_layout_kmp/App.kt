package com.eduside.adaptive_layout_kmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import adaptivelayoutkmp.composeapp.generated.resources.Res
import adaptivelayoutkmp.composeapp.generated.resources.compose_multiplatform
import com.eduside.adaptive_layout_kmp.utils.DeviceType
import com.eduside.adaptive_layout_kmp.utils.getDeviceType

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.eduside.adaptive_layout_kmp.utils.greaterThanOrEqual
import com.eduside.adaptive_layout_kmp.utils.isLandscapePhone

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}

@Composable
fun AdaptiveLayout() {
    // Ambil info layar
    val windowInfo = currentWindowAdaptiveInfo()
    val deviceType = getDeviceType(windowInfo)

    Scaffold(
        bottomBar = {
            // ✅ Compact → pakai BottomBar
            if (deviceType is DeviceType.Compact) {
                BottomNavigationBar()
            }
        },
        content = { innerPadding ->
            Row(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                when {
                    // ✅ Expanded → tampil 3 panel (List + Detail + Analytics)
                    deviceType greaterThanOrEqual DeviceType.Expanded &&
                            !deviceType.isLandscapePhone -> {
                        ListPanel(modifier = Modifier.weight(1f))
                        DetailPanel(modifier = Modifier.weight(2f))
                        AnalyticsPanel(modifier = Modifier.weight(1f))
                    }

                    // ✅ Medium → tampil 2 panel (List + Detail)
                    deviceType greaterThanOrEqual DeviceType.Medium -> {
                        ListPanel(modifier = Modifier.weight(1f))
                        DetailPanel(modifier = Modifier.weight(2f))
                    }

                    // ✅ Compact → tampil 1 panel (List)
                    else -> {
                        ListPanel(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    )
}

@Composable
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { },
            label = { Text("Home") },
            icon = { Icon(painter = painterResource(Res.drawable.ic_home), contentDescription = null) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            label = { Text("Search") },
            icon = { Icon(Icons.Default.Search, contentDescription = null) }
        )
    }
}

@Composable
fun ListPanel(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(Color(0xFFE3F2FD))
    ) {
        Text("📋 List Screen", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun DetailPanel(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(Color(0xFFC8E6C9))
    ) {
        Text("📄 Detail Screen", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun AnalyticsPanel(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(Color(0xFFFFF9C4))
    ) {
        Text("📊 Analytics Panel", modifier = Modifier.padding(16.dp))
    }
}
