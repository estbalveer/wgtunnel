package com.zaneschepke.wireguardautotunnel.core.service

import android.app.Service
import android.content.Context
import android.content.Intent
import com.zaneschepke.wireguardautotunnel.core.service.tile.TunnelControlTile
import com.zaneschepke.wireguardautotunnel.di.ApplicationScope
import com.zaneschepke.wireguardautotunnel.di.IoDispatcher
import com.zaneschepke.wireguardautotunnel.domain.entity.TunnelConf
import com.zaneschepke.wireguardautotunnel.domain.repository.AppDataRepository
import com.zaneschepke.wireguardautotunnel.util.extensions.requestTunnelTileServiceStateUpdate
import jakarta.inject.Inject
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber

class ServiceManager @Inject constructor(
	private val context: Context,
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher,
	@ApplicationScope private val applicationScope: CoroutineScope,
	private val appDataRepository: AppDataRepository,
) {

	private val _autoTunnelActive = MutableStateFlow(false)
	val autoTunnelActive = _autoTunnelActive.asStateFlow()

	var backgroundService = CompletableDeferred<TunnelForegroundService>()
	var tunnelControlTile = CompletableDeferred<TunnelControlTile>()

	private fun <T : Service> startService(cls: Class<T>, background: Boolean) {
		runCatching {
			val intent = Intent(context, cls)
			if (background) {
				context.startForegroundService(intent)
			} else {
				context.startService(intent)
			}
		}.onFailure { Timber.e(it) }
	}

	fun startBackgroundService(tunnelConf: TunnelConf) {
		applicationScope.launch(ioDispatcher) {
			if (backgroundService.isCompleted) return@launch
			runCatching {
				backgroundService = CompletableDeferred()
				startService(TunnelForegroundService::class.java, true)
				val service = withTimeoutOrNull(SERVICE_START_TIMEOUT) { backgroundService.await() }
					?: throw IllegalStateException("Background service start timed out")
				service.start(tunnelConf)
			}.onFailure {
				Timber.e(it)
			}
		}
	}

	fun stopBackgroundService() {
		applicationScope.launch(ioDispatcher) {
			if (!backgroundService.isCompleted) return@launch
			runCatching {
				val service = backgroundService.await()
				service.stop()
				backgroundService = CompletableDeferred()
			}.onFailure {
				Timber.e(it)
			}
		}
	}

	suspend fun updateTunnelTile() {
		withContext(ioDispatcher) {
			runCatching {
				val service = withTimeoutOrNull(SERVICE_START_TIMEOUT) { tunnelControlTile.await() }
					?: run {
						context.requestTunnelTileServiceStateUpdate()
						return@withContext
					}
				service.updateTileState()
			}.onFailure {
				Timber.e(it)
			}
		}
	}

	companion object {
		const val SERVICE_START_TIMEOUT = 5_000L
	}
}
