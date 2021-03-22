package com.mbg.module.common.liveeventbus.core;

/**
 * Created by liaohailiang on 2020-12-12.
 */
public class ObservableConfig {

    Boolean lifecycleObserverAlwaysActive = null;
    Boolean autoClear = null;

    /**
     * lifecycleObserverAlwaysActive
     * set if then observer can always receive message
     * true: observer can always receive message
     * false: observer can only receive message when resumed
     *
     * @param active
     * @return
     */
    public ObservableConfig lifecycleObserverAlwaysActive(boolean active) {
        lifecycleObserverAlwaysActive = active;
        return this;
    }

    /**
     * @param clear
     * @return true: clear livedata when no observer observe it
     * false: not clear livedata unless app was killed
     */
    public ObservableConfig autoClear(boolean clear) {
        autoClear = clear;
        return this;
    }
}
