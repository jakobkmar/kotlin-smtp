package net.axay.kotlinsmtp.server

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.axay.kotlinsmtp.logging.log
import java.net.InetAddress
import kotlin.coroutines.CoroutineContext

@Suppress("MemberVisibilityCanBePrivate")
class SmtpServer(
    val port: Int,
    val hostname: String = InetAddress.getLocalHost().canonicalHostName,
    val servicename: String? = "kotlin-smtp",
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
    suspend fun start(coroutineContext: CoroutineContext = Dispatchers.IO, wait: Boolean = false): Boolean {
        return if (serverSocket == null) {
            val job = serverSocketMutex.withLock {
                serverSocket = aSocket(ActorSelectorManager(coroutineContext))
                    .tcp()
                    .bind(port = port)

                listen()
            }
            if (wait) job.join()

            true
        } else false
    }

    /**
     * Stop the server.
     *
     * @return true, if the server was stopped - false if no server was running
     */
    suspend fun stop(): Boolean = coroutineScope {
        serverSocketMutex.withLock {
            if (serverSocket != null) {
                launch(Dispatchers.IO) {
                    serverSocket!!.close()
                }.join()
                serverSocket = null

                true
            } else false
        }
    }

    private fun listen(): Job {
        val thisSocket = serverSocket!!
        return serverScope.launch {
            log { "Started smtp server, now listening on port $port" }
            while (!thisSocket.isClosed) {
                val socket = thisSocket.accept()

                launch {
                    SmtpSession(socket, this@SmtpServer).handle()
                }
            }
        }
    }
}
