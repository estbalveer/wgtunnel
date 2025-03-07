package com.zaneschepke.wireguardautotunnel.ui.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zaneschepke.wireguardautotunnel.ui.common.Gray
import com.zaneschepke.wireguardautotunnel.ui.common.GrayishBlue
import com.zaneschepke.wireguardautotunnel.ui.common.LightGray
import com.zaneschepke.wireguardautotunnel.ui.common.button.AppButton
import com.zaneschepke.wireguardautotunnel.ui.common.button.AppOutlinedButton

data class DialogSingleActionState(
    val title: String,
    val message: String,
    val confirmText: String = "Okay",
    val onConfirm: () -> Unit = {},
)

data class DialogDoubleActionState(
    val title: String,
    val message: String,
    val confirmText: String = "Confirm",
    val cancelText: String = "Cancel",
    val onConfirm: () -> Unit = {},
    val onCancel: () -> Unit = {}
)

@Composable
fun SingleActionDialog(
    title: String,
    message: String,
    confirmText: String = "Okay",
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = { /* Prevent dismiss on back press & outside click */ },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.DarkGray)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Message
                Text(
                    text = message,
                    fontSize = 16.sp,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Confirm Button
                AppButton(
                    text = confirmText,
                    isLoading = false,
                    isEnabled = true,
                    onClick = { onConfirm() },
                )
            }
        }
    }
}

@Composable
fun DoubleActionDialog(
    title: String,
    message: String,
    confirmText: String = "Confirm",
    cancelText: String = "Cancel",
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Dialog(
        onDismissRequest = { /* Prevent dismiss on back press & outside click */ },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(GrayishBlue)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = LightGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Message
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 30.dp)
                )

                // Buttons Row
                Box(
                    modifier = Modifier.height(44.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Cancel Button
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            AppOutlinedButton(
                                text = cancelText,
                                isLoading = false,
                                isEnabled = true,
                                onClick = { onCancel() },
                            )
                        }

                        Spacer(modifier = Modifier.width(15.dp))

                        // Confirm Button
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            AppButton(
                                text = confirmText,
                                isLoading = false,
                                isEnabled = true,
                                onClick = { onConfirm() },
                            )
                        }
                    }
                }
            }
        }
    }
}
