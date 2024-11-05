package su.vlasilya.trylaunch

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Безопасно запустить корутину на выбранном диспатчере
 *
 * ```
 * launchSafe(Dispatchers.Main) {
 *     println("#0 launchSafe(Dispatchers.Main) runs, ${Thread.currentThread().name}")
 *     delay(400)
 *     error("#0 launchSafe(Dispatchers.Main) error, ${Thread.currentThread().name}")
 * } catch {
 *     println("catch on(Dispatchers.Main) [$it], ${Thread.currentThread().name}")
 * }
 * ```
 *
 * @param dispatcher Диспатчер, по-умолчанию Dispatchers.Unconfined
 * @param block Лямбда, содержащая логику корутины
 *
 * @return обертка, инкапсулирующая логику безопасного запуска корутины
 *
 * @see catch
 */
fun CoroutineScope.launchSafe(
    dispatcher: CoroutineDispatcher = Dispatchers.Unconfined,
    block: suspend () -> Unit
) = LaunchBlock(this, dispatcher, block)

/**
 * Утилитный метод для запуска корутины на Dispatchers.Main
 *
 * ```
 * launchMain {
 *     println("#0 launchMain runs, ${Thread.currentThread().name}")
 *     delay(400)
 *     error("#0 launchMain error, ${Thread.currentThread().name}")
 * } catch {
 *     println("catch on(Dispatchers.Main) [$it], ${Thread.currentThread().name}")
 * }
 * ```
 *
 * @see CoroutineScope.launchSafe
 */
fun CoroutineScope.launchMain(block: suspend () -> Unit) = launchSafe(Dispatchers.Main, block)

/**
 * Утилитный метод для запуска корутины на Dispatchers.IO
 *
 * ```
 * launchIO {
 *     println("#0 launchIO runs, ${Thread.currentThread().name}")
 *     delay(400)
 *     error("#0 launchIO error, ${Thread.currentThread().name}")
 * } catch {
 *     println("catch on(Dispatchers.Main) [$it], ${Thread.currentThread().name}")
 * }
 * ```
 *
 * @see CoroutineScope.launchSafe
 */
fun CoroutineScope.launchIO(block: suspend () -> Unit) = launchSafe(Dispatchers.IO, block)

/**
 * Утилитный метод для запуска корутины на Dispatchers.Default
 *
 * ```
 * launchDefault {
 *     println("#0 launchDefault runs, ${Thread.currentThread().name}")
 *     delay(400)
 *     error("#0 launchDefault error, ${Thread.currentThread().name}")
 * } catch {
 *     println("catch on(Dispatchers.Main) [$it], ${Thread.currentThread().name}")
 * }
 * ```
 *
 * @see CoroutineScope.launchSafe
 */
fun CoroutineScope.launchDefault(block: suspend () -> Unit) = launchSafe(Dispatchers.Default, block)

/**
 * Утилитный метод для запуска корутины на Dispatchers.Unconfined
 *
 * ```
 * launchUnconfined {
 *     println("#0 launchUnconfined runs, ${Thread.currentThread().name}")
 *     delay(400)
 *     error("#0 launchUnconfined error, ${Thread.currentThread().name}")
 * } catch {
 *     println("catch on(Dispatchers.Main) [$it], ${Thread.currentThread().name}")
 * }
 * ```
 *
 * @see CoroutineScope.launchSafe
 */
fun CoroutineScope.launchUnconfined(block: suspend () -> Unit) = launchSafe(Dispatchers.Unconfined, block)
