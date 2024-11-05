package su.vlasilya.trylaunch

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

/**
 * Утилитный класс для инкапсуляци информации, необходимой для запуска корутины
 */
class LaunchBlock internal constructor(
    val coroutineScope: CoroutineScope,
    val dispatcher: CoroutineDispatcher,
    val safeAction: suspend () -> Unit
)

/**
 * Утилитный класс для инкапсуляци информации, необходимой для обработки ошибок корутины
 */
class ErrorBlock internal constructor(
    val dispatcher: CoroutineDispatcher,
    val onError: suspend (Throwable) -> Unit,
)
