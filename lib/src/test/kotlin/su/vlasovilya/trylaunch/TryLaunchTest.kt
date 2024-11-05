package su.vlasovilya.trylaunch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import su.vlasilya.trylaunch.and
import su.vlasilya.trylaunch.catch
import su.vlasilya.trylaunch.launchDefault
import su.vlasilya.trylaunch.launchIO
import su.vlasilya.trylaunch.launchMain
import su.vlasilya.trylaunch.launchSafe
import su.vlasilya.trylaunch.launchUnconfined
import su.vlasilya.trylaunch.on

class TryLaunchTest {

    @Test
    fun test() = runBlocking {
        supervisorScope {
            launchMain {
                assert(Thread.currentThread().name.startsWith("Main Thread"))
                delay(400)
                assert(Thread.currentThread().name.startsWith("Main Thread"))
            } catch {
                assert(Thread.currentThread().name.startsWith("Main Thread"))
            }

            launchIO {
                println("#1 launchIO runs, ${Thread.currentThread().name}")
                delay(400)
                error("#1 launchIO error, ${Thread.currentThread().name}")
            } and launchDefault {
                println("#2 launchDefault runs, ${Thread.currentThread().name}")
                delay(500)
                error("#2 launchDefault error, ${Thread.currentThread().name}")
            } and launchMain {
                println("#3 launchMain runs, ${Thread.currentThread().name}")
                delay(600)
                error("#3 launchMain error, ${Thread.currentThread().name}")
            } and launchUnconfined {
                println("#4 launchUnconfined runs, ${Thread.currentThread().name}")
                delay(700)
                error("#4 launchUnconfined error, ${Thread.currentThread().name}")
            } and launchSafe(Dispatchers.Unconfined) {
                println("#5 launchSafe(Dispatchers.Unconfined) runs, ${Thread.currentThread().name}")
                delay(800)
                error("#5 launchSafe(Dispatchers.IO) error, ${Thread.currentThread().name}")
            } catch on(Dispatchers.Default) {
                println("catch on(Dispatchers.Default) [$it], ${Thread.currentThread().name}")
            }
        }

        delay(1000)
    }

    companion object {
        @OptIn(ExperimentalCoroutinesApi::class)
        private val mainThreadSurrogate = newSingleThreadContext("Main Thread")

        @JvmStatic
        @AfterAll
        @OptIn(ExperimentalCoroutinesApi::class)
        fun tearDown() {
            Dispatchers.resetMain()
        }

        @JvmStatic
        @BeforeAll
        @OptIn(ExperimentalCoroutinesApi::class)
        fun setup() {
            Dispatchers.setMain(mainThreadSurrogate)
        }
    }
}

