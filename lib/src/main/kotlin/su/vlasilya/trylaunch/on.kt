package su.vlasilya.trylaunch

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Функция позволяет указать диспатчер для обработки корутины
 *
 * @param dispatcher
 * @param onError обработчик ошибки
 *
 * @return ErrorBlock информация для запуска корутины
 *
 * @see ErrorBlock
 */
fun on(
    dispatcher: CoroutineDispatcher,
    onError: suspend (Throwable) -> Unit,
) = ErrorBlock(dispatcher, onError)
