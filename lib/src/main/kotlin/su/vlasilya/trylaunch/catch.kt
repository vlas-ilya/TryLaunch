package su.vlasilya.trylaunch

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Функция отвечающая непосредственно за запуск корутины
 *
 * (Ошибка будет обрабатываться на Dispatchers.Main)
 *
 * @param onError - блок кода обрабатывающий ошибку
 *
 * @return Job
 *
 * @see LaunchBlock
 */
inline infix fun LaunchBlock.catch(crossinline onError: suspend (Throwable) -> Unit): Job {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        coroutineScope.launch(Dispatchers.Main) {
            onError(throwable)
        }
    }
    return coroutineScope.launch(exceptionHandler + dispatcher) {
        safeAction()
    }
}

/**
 * Функция отвечающая непосредственно за запуск нескольких корутин
 * (Ошибка будет обрабатываться на Dispatchers.Main)
 *
 * @param onError - блок кода обрабатывающий ошибку
 *
 * @return Job
 *
 * @throws IllegalArgumentException если список запускаемых корутин - пустой
 *
 * @see LaunchBlock
 * @see and
 */
inline infix fun List<LaunchBlock>.catch(crossinline onError: suspend (Throwable) -> Unit): List<Job> {
    require(this.isNotEmpty())
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        this[0].coroutineScope.launch(Dispatchers.Main) {
            onError(throwable)
        }
    }
    val coroutineScope = CoroutineScope(Job())
    return this.map {
        coroutineScope.launch(exceptionHandler + it.dispatcher) {
            it.safeAction()
        }
    }
}

/**
 * Функция отвечающая непосредственно за запуск корутину,
 * и позваляющая указать диспатчер для обработки ошибки
 *
 * @param onError - блок кода обрабатывающий ошибку
 *
 * @return Job
 *
 * @see LaunchBlock
 * @see ErrorBlock
 * @see on
 */
infix fun LaunchBlock.catch(error: ErrorBlock): Job {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        coroutineScope.launch(error.dispatcher) {
            error.onError(throwable)
        }
    }
    return coroutineScope.launch(exceptionHandler + dispatcher) {
        safeAction()
    }
}

/**
 * Функция отвечающая непосредственно за запуск нескольких корутин,
 * и позваляющая указать диспатчер для обработки ошибки
 *
 * @param onError блок кода обрабатывающий ошибку
 *
 * @return Job
 *
 * @throws IllegalArgumentException если список запускаемых корутин - пустой
 *
 * @see LaunchBlock
 * @see ErrorBlock
 * @see on
 * @see and
 */
infix fun List<LaunchBlock>.catch(error: ErrorBlock): List<Job> {
    require(this.isNotEmpty())
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        this[0].coroutineScope.launch(error.dispatcher) {
            error.onError(throwable)
        }
    }
    val coroutineScope = CoroutineScope(Job())
    return this.map {
        coroutineScope.launch(exceptionHandler + it.dispatcher) {
            it.safeAction()
        }
    }
}
