@file:Suppress("RedundantVisibilityModifier", "unused")
@file:JvmName("ReflectionFragmentViewBindings")

package com.mbg.module.ui.viewbinding

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding



private class DialogFragmentViewBindingProperty<in F : DialogFragment, out T : ViewBinding>(viewBinder: (F) -> T) : LifecycleViewBindingProperty<F, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F): LifecycleOwner {
       return if (thisRef.showsDialog) {
             thisRef
        } else {
            try {
                 thisRef.viewLifecycleOwner
            } catch (ignored: IllegalStateException) {
                error("Fragment doesn't have view associated with it or the view has been destroyed")
            }
        }

    }
}

private class FragmentViewBindingProperty<in F : Fragment, out T : ViewBinding>(viewBinder: (F) -> T) : LifecycleViewBindingProperty<F, T>(viewBinder) {

    override fun getLifecycleOwner(thisRef: F): LifecycleOwner {
        try {
            return thisRef.viewLifecycleOwner
        } catch (ignored: IllegalStateException) {
            error("Fragment doesn't have view associated with it or the view has been destroyed")
        }
    }
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 */
@Suppress("UNCHECKED_CAST")
@JvmName("viewBindingFragment")
public fun <F : Fragment, T : ViewBinding> Fragment.viewBinding(viewBinder: (F) -> T): ViewBindingProperty<F, T> {
    return when (this) {
        is DialogFragment -> DialogFragmentViewBindingProperty(viewBinder) as ViewBindingProperty<F, T>
        else -> FragmentViewBindingProperty(viewBinder)
    }
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewProvider Provide a [View] from the Fragment. By default call [Fragment.requireView]
 */
@JvmName("viewBindingFragment")
public inline fun <F : Fragment, T : ViewBinding> Fragment.viewBinding(
        crossinline vbFactory: (View) -> T,
        crossinline viewProvider: (F) -> View = Fragment::requireView
): ViewBindingProperty<F, T> {
    return viewBinding { fragment: F -> vbFactory(viewProvider(fragment)) }
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param vbFactory Function that create new instance of [ViewBinding]. `MyViewBinding::bind` can be used
 * @param viewBindingRootId Root view's id that will be used as root for the view binding
 */
@Suppress("UNCHECKED_CAST")
@JvmName("viewBindingFragment")
public inline fun <F : Fragment, T : ViewBinding> Fragment.viewBinding(
        crossinline vbFactory: (View) -> T,
        @IdRes viewBindingRootId: Int
): ViewBindingProperty<F, T> {
    return when (this) {
        is DialogFragment -> {
            viewBinding<DialogFragment, T>(vbFactory) { fragment ->
                fragment.getRootView(viewBindingRootId)
            } as ViewBindingProperty<F, T>
        }
        else -> {
            viewBinding(vbFactory) { fragment: F ->
                fragment.requireView().requireViewByIdCompat(viewBindingRootId)
            }
        }
    }
}


@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> Fragment.viewBinding(
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<Fragment, T> {
    return viewBinding(T::class.java, viewBindingRootId)
}

@JvmName("viewBindingFragment")
public fun <T : ViewBinding> Fragment.viewBinding(
    viewBindingClass: Class<T>,
    @IdRes viewBindingRootId: Int
): ViewBindingProperty<Fragment, T> {
    return when (this) {
        is DialogFragment -> {
            viewBinding { dialogFragment ->
                require(dialogFragment is DialogFragment)
                ViewBindingCache.getBind(viewBindingClass).bind(dialogFragment.getRootView(viewBindingRootId))
            }
        }
        else -> {
            viewBinding {
                ViewBindingCache.getBind(viewBindingClass).bind(requireView().requireViewByIdCompat(viewBindingRootId))
            }
        }
    }
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param T Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public inline fun <reified T : ViewBinding> Fragment.viewBinding(
    createMethod: CreateMethod = CreateMethod.BIND
): ViewBindingProperty<Fragment, T> {
    return viewBinding(T::class.java, createMethod)
}

/**
 * Create new [ViewBinding] associated with the [Fragment]
 *
 * @param viewBindingClass Class of expected [ViewBinding] result class
 */
@JvmName("viewBindingFragment")
public fun <T : ViewBinding> Fragment.viewBinding(viewBindingClass: Class<T>, createMethod: CreateMethod = CreateMethod.BIND): ViewBindingProperty<Fragment, T> = when (createMethod) {
    CreateMethod.BIND -> viewBinding {
        ViewBindingCache.getBind(viewBindingClass).bind(requireView())
    }
    CreateMethod.INFLATE -> viewBinding {
        ViewBindingCache.getInflateWithLayoutInflater(viewBindingClass).inflate(layoutInflater, null, false)
    }
}
