package com.zaneschepke.wireguardautotunnel.ui.common.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaneschepke.wireguardautotunnel.ui.common.Gray
import com.zaneschepke.wireguardautotunnel.ui.common.GrayishBlue
import com.zaneschepke.wireguardautotunnel.ui.common.LightYellow
import com.zaneschepke.wireguardautotunnel.ui.common.White

@Composable
fun AppButton(
    text: String,
    loadingText: String? = null,
    isLoading: Boolean,
    isEnabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    Button(
        onClick = { if (!isLoading) onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        enabled = isEnabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
        )
    ) {
        if (isLoading) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = Gray, modifier = Modifier.size(16.dp), strokeWidth = 1.5.dp
                )
                Text(
                    text = if (loadingText.isNullOrEmpty()) "" else "$loadingText...",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        } else {
            Text(text = text, fontSize = 16.sp)
        }
    }
}

@Composable
fun AppOutlinedButton(
    text: String,
    loadingText: String? = null,
    isLoading: Boolean,
    isEnabled: Boolean = true,
    borderColor: Color = LightYellow,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = { if (!isLoading) onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        enabled = isEnabled && !isLoading,
        border = BorderStroke(0.4.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = GrayishBlue,
        )
    ) {
        if (isLoading) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = Gray, modifier = Modifier.size(16.dp), strokeWidth = 1.5.dp
                )
                Text(
                    text = if (loadingText.isNullOrEmpty()) "" else "$loadingText...",
                    fontSize = 16.sp,
                    color = White,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        } else {
            Text(text = text, fontSize = 16.sp, color = White)
        }
    }
}
