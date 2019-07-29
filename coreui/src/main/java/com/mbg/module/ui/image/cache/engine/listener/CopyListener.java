package com.mbg.module.ui.image.cache.engine.listener;


/** Listener and controller for copy process */
public interface CopyListener {
    /**
     * @param current Loaded bytes
     * @param total   Total bytes for loading
     * @return <b>true</b> - if copying should be continued; <b>false</b> - if copying should be interrupted
     */
    boolean onBytesCopied(int current, int total);
}
