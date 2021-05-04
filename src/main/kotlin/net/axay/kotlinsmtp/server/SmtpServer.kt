package net.axay.kotlinsmtp.server

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.CoroutineContext

@Suppress("MemberVisibilityCanBePrivate")
class SmtpServer(
    val hostname: String,
    val port: Int,
) {
    private val serverScope = CoroutineScope(Dispatchers.IO)

    private val serverSocketMutex = Mutex()
    private var serverSocket: ServerSocket? = null

    /**
     * Start the server.
     *
     * @param coroutineContext the context to use for the socket
     *
     * @return true, if the server was started - false if it was already running
     */
    suspend fun start(coroutineContext: CoroutineContext = Dispatchers.IO): Boolean {
        serverSocketMutex.withLock {
            return if (serverSocket != null) {
                serverSocket = aSocket(ActorSelectorManager(coroutineContext))
                    .tcp()
                    .bind(hostname, port)

                listen()

                true
            } else false
        }
    }

    /**
     * Stop the server.
     *
     * @return true, if the server was stopped - false if no server was running
     */
    suspend fun stop(): Boolean = coroutineScope {
        serverSocketMutex.withLock {
            if (serverSocket != null) {
                serverSocket!!.awaitClosed()
                serverSocket = null

                true
            } else false
        }
    }

    private fun listen() {
        serverScope.launch {
            while (serverSocket != null) {
                val currentSocket = serverSocket!!
                val socket = currentSocket.accept()

                launch {
                    socket.openReadChannel()
                }
            }
        }
    }
}
