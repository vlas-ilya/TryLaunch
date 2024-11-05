package su.vlasilya.trylaunch

/**
 * Функция позволяет объединить несколько корутин для одноверенного запуска
 *
 * ```
 * launchIO {
 *     println("#1 launchIO runs, ${Thread.currentThread().name}")
 *     delay(400)
 *     error("#1 launchIO error, ${Thread.currentThread().name}")
 * } and launchDefault {
 *     println("#2 launchDefault runs, ${Thread.currentThread().name}")
 *     delay(500)
 *     error("#2 launchDefault error, ${Thread.currentThread().name}")
 * } catch on(Dispatchers.Default) {
 *     println("catch on(Dispatchers.Default) [$it], ${Thread.currentThread().name}")
 * }
 * ```
 *
 * @throws IllegalArgumentException если корутины запускаются в разных скоупах
 */
infix fun LaunchBlock.and(launchBlock: LaunchBlock): MutableList<LaunchBlock> {
    require(this.coroutineScope === launchBlock.coroutineScope)
    return mutableListOf(this, launchBlock)
}

/**
 * Функция позволяет объединить несколько корутин для одноверенного запуска
 *
 * ```
 * launchIO {
 *     println("#1 launchIO runs, ${Thread.currentThread().name}")
 *     delay(400)
 *     error("#1 launchIO error, ${Thread.currentThread().name}")
 * } and launchDefault {
 *     println("#2 launchDefault runs, ${Thread.currentThread().name}")
 *     delay(500)
 *     error("#2 launchDefault error, ${Thread.currentThread().name}")
 * } and launchMain {
 *     println("#3 launchMain runs, ${Thread.currentThread().name}")
 *     delay(600)
 *     error("#3 launchMain error, ${Thread.currentThread().name}")
 * } and launchUnconfined {
 *     println("#4 launchUnconfined runs, ${Thread.currentThread().name}")
 *     delay(700)
 *     error("#4 launchUnconfined error, ${Thread.currentThread().name}")
 * } and launchSafe(Dispatchers.Unconfined) {
 *     println("#5 launchSafe(Dispatchers.Unconfined) runs, ${Thread.currentThread().name}")
 *     delay(800)
 *     error("#5 launchSafe(Dispatchers.IO) error, ${Thread.currentThread().name}")
 * } catch on(Dispatchers.Default) {
 *     println("catch on(Dispatchers.Default) [$it], ${Thread.currentThread().name}")
 * }
 *
 * @throws IllegalArgumentException если корутины запускаются в разных скоупах
 * ```
 */
infix fun MutableList<LaunchBlock>.and(launchBlock: LaunchBlock): MutableList<LaunchBlock> {
    require(this.isNotEmpty())
    require(this[0].coroutineScope === launchBlock.coroutineScope)
    return this.apply { add(launchBlock) }
}
