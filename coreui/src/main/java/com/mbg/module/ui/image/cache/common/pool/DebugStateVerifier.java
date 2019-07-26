package com.mbg.module.ui.image.cache.common.pool;

import com.mbg.module.ui.image.cache.common.Synthetic;

public class DebugStateVerifier extends StateVerifier {

    // Keeps track of the stack trace where our state was set to recycled.
    private volatile RuntimeException recycledAtStackTraceException;

    @Synthetic
    public DebugStateVerifier() {}

    @Override
    public void throwIfRecycled() {
        if (recycledAtStackTraceException != null) {
            throw new IllegalStateException("Already released", recycledAtStackTraceException);
        }
    }

    @Override
    public void setRecycled(boolean isRecycled) {
        if (isRecycled) {
            recycledAtStackTraceException = new RuntimeException("Released");
        } else {
            recycledAtStackTraceException = null;
        }
    }
}
