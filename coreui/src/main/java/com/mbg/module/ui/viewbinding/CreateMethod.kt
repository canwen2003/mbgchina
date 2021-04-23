package com.mbg.module.ui.viewbinding

import androidx.viewbinding.ViewBinding

/**
 * Method that will be used to create [ViewBinding].
 */
enum class CreateMethod {
    /**
     * Use `ViewBinding.bind(View)`
     */
    BIND,

    /**
     * Use `ViewBinding.inflate(LayoutInflater, ViewGroup, boolean)`
     */
    INFLATE
}
