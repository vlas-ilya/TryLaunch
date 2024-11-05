# TryLaunch

Набор утилитных методов для более удобной обработки ошибок при запуске корутин

## Пример использования

```kotlin
launchIO {
    // Корутина будет запущена на Dispatchers.IO
    println("#1 launchIO runs, ${Thread.currentThread().name}")
    delay(400)
    error("#1 launchIO error, ${Thread.currentThread().name}")
} catch {
    // Ошибки будут отлавливаться на Dispatchers.Main
    println("catch on(Dispatchers.Main) [$it], ${Thread.currentThread().name}")
}
```

### Более подробный пример

```kotlin
launchIO {
    // Корутина будет запущена на Dispatchers.IO
    println("#1 launchIO runs, ${Thread.currentThread().name}")
    delay(400)
    error("#1 launchIO error, ${Thread.currentThread().name}")
} and launchDefault {
    // Корутина будет запущена на Dispatchers.Default
    println("#2 launchDefault runs, ${Thread.currentThread().name}")
    delay(500)
    error("#2 launchDefault error, ${Thread.currentThread().name}")
} and launchMain {
    // Корутина будет запущена на Dispatchers.Main
    println("#3 launchMain runs, ${Thread.currentThread().name}")
    delay(600)
    error("#3 launchMain error, ${Thread.currentThread().name}")
} and launchUnconfined {
    // Корутина будет запущена на Dispatchers.Unconfined
    println("#4 launchUnconfined runs, ${Thread.currentThread().name}")
    delay(700)
    error("#4 launchUnconfined error, ${Thread.currentThread().name}")
} and launchSafe(Dispatchers.Unconfined) {
    // Корутина будет запущена на Dispatchers.Unconfined
    println("#5 launchSafe(Dispatchers.Unconfined) runs, ${Thread.currentThread().name}")
    delay(800)
    error("#5 launchSafe(Dispatchers.IO) error, ${Thread.currentThread().name}")
} catch on(Dispatchers.Default) {
    // Ошибки будут отлавливаться на Dispatchers.Default
    println("catch on(Dispatchers.Default) [$it], ${Thread.currentThread().name}")
}
```

