package com.zaneschepke.wireguardautotunnel.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.zaneschepke.wireguardautotunnel.ui.common.BackgroundGradient
import com.zaneschepke.wireguardautotunnel.ui.common.LottieBackground
import com.zaneschepke.wireguardautotunnel.ui.common.dialog.VPNConnectDialog


@Composable
fun LoginScreen(navController: NavController) {

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(brush = BackgroundGradient),
	) {
		// Lottie Background Animation
		LottieBackground(lowOpacity = false)

		VPNConnectDialog {

		}
	}
}
