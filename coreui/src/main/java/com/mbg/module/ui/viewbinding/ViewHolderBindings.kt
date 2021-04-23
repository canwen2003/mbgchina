@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionViewHolderBindings")

package com.mbg.module.ui.viewbinding

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding


/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 */
fun <VH : ViewHolder, T : ViewBinding> VH.viewBinding(viewBinder: (VH) -> T): ViewBindingProperty<VH, T> {
    return LazyViewBindingProperty(viewBinder)
}

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewProvider Provide a [View] from the [ViewHolder]. By default call [ViewHolder.itemView]
 */
inline fun <VH : ViewHolder, T : ViewBinding> VH.viewBinding(
        crossinline vbFactory: (View) -> T,
        crossinline viewProvider: (VH) -> View = ViewHolder::itemView,
): ViewBindingProperty<VH, T> {
    return LazyViewBindingProperty { viewHolder: VH -> viewProvider(viewHolder).let(vbFactory) }
}

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
inline fun <VH : ViewHolder, T : ViewBinding> VH.viewBinding(
        crossinline vbFactory: (View) -> T,
        @IdRes viewBindingRootId: Int,
): ViewBindingProperty<VH, T> {
    return LazyViewBindingProperty { viewHolder: VH ->
        vbFactory(viewHolder.itemView.requireViewByIdCompat(viewBindingRootId))
    }
}


/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> ViewHolder.viewBinding() = viewBinding(T::class.java)

/**
 * Create new [ViewBinding] associated with the [ViewHolder]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public fun <T : ViewBinding> ViewHolder.viewBinding(
    viewBindingClass: Class<T>,
): ViewBindingProperty<ViewHolder, T> {
    return viewBinding { ViewBindingCache.getBind(viewBindingClass).bind(itemView) }
}
