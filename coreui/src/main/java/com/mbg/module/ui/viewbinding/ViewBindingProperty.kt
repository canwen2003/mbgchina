@file:Suppress("RedundantVisibilityModifier")

package com.mbg.module.ui.viewbinding

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private const val TAG = "ViewBindingProperty"
interface ViewBindingProperty<in R : Any, out T : ViewBinding> : ReadOnlyProperty<R, T> {

    @MainThread
    fun clear()
}

@RestrictTo(LIBRARY_GROUP)
public open class LazyViewBindingProperty<in R : Any, out T : ViewBinding>(protected val viewBinder: (R) -> T) : ViewBindingProperty<R, T> {

    private var viewBinding: Any? = null

    @Suppress("UNCHECKED_CAST")
    @MainThread
    public override fun getValue(thisRef: R, property: KProperty<*>): T {
        return viewBinding as? T ?: viewBinder(thisRef).also { viewBinding ->
            this.viewBinding = viewBinding
        }
    }

    @MainThread
    public override fun clear() {
        viewBinding = null
    }
}

@RestrictTo(LIBRARY_GROUP)
public abstract class LifecycleViewBindingProperty<in R : Any, out T : ViewBinding>(private val viewBinder: (R) -> T) : ViewBindingProperty<R, T> {

    private var viewBinding: T? = null

    protected abstract fun getLifecycleOwner(thisRef: R): LifecycleOwner

    @MainThread
    public override fun getValue(thisRef: R, property: KProperty<*>): T {
        //如果存在则返回viewBinding对象
        viewBinding?.let { return it }

        val lifecycle = getLifecycleOwner(thisRef).lifecycle
        val viewBinding = viewBinder(thisRef)
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            Log.w(TAG, "Access to viewBinding after Lifecycle is destroyed or hasn't created yet. " + "The instance of viewBinding will be not cached.")
            // We can access to ViewBinding after Fragment.onDestroyView(), but don't save it to prevent memory leak
        } else {
            //根据生命周期添加View回收
            lifecycle.addObserver(ClearOnDestroyLifecycleObserver())
            this.viewBinding = viewBinding
        }
        return viewBinding
    }

    @MainThread
    public override fun clear() {
        mUIHandler.post { viewBinding = null }
    }

    private inner class ClearOnDestroyLifecycleObserver : DefaultLifecycleObserver {
        @MainThread
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
        }

        @MainThread
        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
        }

        @MainThread
        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
        }

        @MainThread
        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)
        }

        @MainThread
        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
        }

        @MainThread
        override fun onDestroy(owner: LifecycleOwner){
            clear()
        }
    }

    private companion object {
        private val mUIHandler = Handler(Looper.getMainLooper())
    }
}
