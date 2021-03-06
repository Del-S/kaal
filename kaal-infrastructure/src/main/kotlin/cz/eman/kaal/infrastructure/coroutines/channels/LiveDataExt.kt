package cz.eman.kaal.infrastructure.coroutines.channels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import kotlinx.coroutines.channels.Channel

/**
 *  Extension functions for converting [LiveData] to coroutines [Channel]
 *
 *
 *  @param lifecycleOwner for managing data flow and closing streams on destroy - NULL value
 *  ATTENTION!! Don't forget to close [Channel] when there is no need for data updates
 *  @author [eMan a.s.](mailto:info@eman.cz)
 *  @since 1.0.0
 */
fun <T> LiveData<T>.observeChannel(lifecycleOwner: LifecycleOwner? = null): Channel<T?> {
    val liveDataChannel = LiveDataChannel(this)

    lifecycleOwner?.let {
        observe(it, liveDataChannel)
        it.lifecycle.addObserver(liveDataChannel)
    } ?: run {
        observeForever(liveDataChannel)
    }
    return liveDataChannel.channel
}
