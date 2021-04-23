@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionActivityViewBindings")
package com.mbg.module.ui.viewbinding


import android.app.Activity
import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.annotation.RestrictTo
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding



@RestrictTo(RestrictTo.Scope.LIBRARY)
private class ActivityViewBindingProperty<in A : ComponentActivity, out T : ViewBinding>(
        viewBinder: (A) -> T
) : LifecycleViewBindingProperty<A, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: A): LifecycleOwner {
        return thisRef
    }
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity] and allow customize how
 * a [View] will be bounded to the view binding.
 */
@JvmName("viewBindingActivity")
public fun <A : ComponentActivity, T : ViewBinding> ComponentActivity.viewBinding(
        viewBinder: (A) -> T
): ViewBindingProperty<A, T> {
    return ActivityViewBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity] and allow customize how
 * a [View] will be bounded to the view binding.
 */
@JvmName("viewBindingActivity")
public inline fun <A : ComponentActivity, T : ViewBinding> ComponentActivity.viewBinding(
        crossinline vbFactory: (View) -> T,
        crossinline viewProvider: (A) -> View = ::findRootView
): ViewBindingProperty<A, T> {
    return viewBinding { activity -> vbFactory(viewProvider(activity)) }
}

/**
 * Create new [ViewBinding] associated with the [Activity][this] and allow customize how
 * a [View] will be bounded to the view binding.
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@Suppress("unused")
@JvmName("viewBindingActivity")
public inline fun <T : ViewBinding> ComponentActivity.viewBinding(
        crossinline vbFactory: (View) -> T,
        @IdRes viewBindingRootId: Int
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding { activity -> vbFactory(activity.requireViewByIdCompat(viewBindingRootId)) }
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity]
 *
 * @param T Class of expected [ViewBinding] result class
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@JvmName("viewBindingActivity")
public inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(@IdRes viewBindingRootId: Int) =
    viewBinding(T::class.java, viewBindingRootId)

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@JvmName("viewBindingActivity")
public fun <T : ViewBinding> ComponentActivity.viewBinding(viewBindingClass: Class<T>, @IdRes viewBindingRootId: Int): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding { activity ->
        val rootView = ActivityCompat.requireViewById<View>(activity, viewBindingRootId)
        ViewBindingCache.getBind(viewBindingClass).bind(rootView)
    }
}

/**
 * Create new [ViewBinding] associated with the [Activity]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 * @param rootViewProvider Provider of root view for the [ViewBinding] from the [Activity][this]
 */
@JvmName("viewBindingActivity")
public fun <T : ViewBinding> ComponentActivity.viewBinding(
    viewBindingClass: Class<T>,
    rootViewProvider: (ComponentActivity) -> View
): ViewBindingProperty<ComponentActivity, T> {
    return viewBinding { activity ->
        ViewBindingCache.getBind(viewBindingClass).bind(rootViewProvider(activity))
    }
}

/**
 * Create new [ViewBinding] associated with the [Activity][ComponentActivity].
 * You need to set [ViewBinding.getRoot] as content view using [Activity.setContentView].
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("inflateViewBindingActivity")
public inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(
    createMethod: CreateMethod = CreateMethod.BIND
) = viewBinding(T::class.java, createMethod)

@JvmName("inflateViewBindingActivity")
public fun <T : ViewBinding> ComponentActivity.viewBinding(viewBindingClass: Class<T>, createMethod: CreateMethod = CreateMethod.BIND): ViewBindingProperty<ComponentActivity, T> = when (createMethod) {
    CreateMethod.BIND -> viewBinding(viewBindingClass, ::findRootView)
    CreateMethod.INFLATE -> viewBinding { ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass).inflate(layoutInflater, null, false) }
}
