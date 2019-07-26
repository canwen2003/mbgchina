package com.mbg.module.ui.image.cache.common.pool;


public class DefaultStateVerifier extends StateVerifier {
    private volatile boolean isReleased;

    public DefaultStateVerifier() {}

    @Override
    public void throwIfRecycled() {
        if (isReleased) {
            throw new IllegalStateException("Already released");
        }
    }

    @Override
    public void setRecycled(boolean isRecycled) {
        this.isReleased = isRecycled;
    }

}
