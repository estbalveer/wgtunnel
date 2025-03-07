package com.zaneschepke.wireguardautotunnel.ui.common.dialog

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zaneschepke.wireguardautotunnel.R
import com.zaneschepke.wireguardautotunnel.ui.common.Gray
import com.zaneschepke.wireguardautotunnel.ui.common.White
import com.zaneschepke.wireguardautotunnel.ui.common.button.AppButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun VPNConnectDialog(
	onSubmit: () -> Unit
) {
    val context = LocalContext.current
    var textFieldValue by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() } // Focus requester
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    Text(
                        text = "Secure VPN Connection",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = White,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Description
                    Text(
                        text = "Enter your VPN server key to establish a secure and private connection.",
                        fontSize = 14.sp,
                        color = Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Input Field with Prefix Icon
                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it },
                        leadingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.vpn_lock),
                                contentDescription = "VPN",
                                tint = Gray
                            )
                        },
                        placeholder = { Text("Enter VPN Key", color = Gray) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            handleVPNConnect(
                                context,
                                textFieldValue = textFieldValue,
                                onSubmit = {
                                    Toast.makeText(
                                        context,
                                        "VPN Key activated.",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    onSubmit()
                                    showDialog = false
                                },
                                setLoading = { isLoading = it },
                            )
                        })
                    )

                    // Ensure focus request happens AFTER composition
                    LaunchedEffect(Unit) {
                        delay(150)
                        focusRequester.requestFocus()
                    }

                    AppButton("Connect",
                        loadingText = "Connecting",
                        isLoading = isLoading,
                        isEnabled = true,
                        onClick = {
                            handleVPNConnect(
                                context,
                                textFieldValue = textFieldValue,
                                onSubmit = {
                                    Toast.makeText(
                                        context,
                                        "VPN Key activated.",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    onSubmit()
                                    showDialog = false
                                },
                                setLoading = { isLoading = it },
                            )
                        })
                }
            }
        }
    }
}

private fun handleVPNConnect(
    context: Context,
    textFieldValue: String,
    onSubmit: () -> Unit,
    setLoading: (Boolean) -> Unit,
) {
    setLoading(true)

    if (textFieldValue.isBlank()) {
        Toast.makeText(context, "Please enter a valid key.", Toast.LENGTH_SHORT).show()
        setLoading(false)
        return
    }

    // Launch coroutine in a proper scope
    CoroutineScope(Dispatchers.IO).launch {
        try {
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                setLoading(false)
                Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
