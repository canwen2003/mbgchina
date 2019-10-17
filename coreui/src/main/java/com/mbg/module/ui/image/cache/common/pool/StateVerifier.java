package com.mbg.module.ui.image.cache.common.pool;


import androidx.annotation.NonNull;

/** Verifies that the job is not in the recycled state. */
public abstract class StateVerifier {

  private static final boolean DEBUG = false;

  /** Creates a new {@link StateVerifier} instance. */
  @NonNull
  public static StateVerifier newInstance() {
    if (DEBUG) {
      return new DebugStateVerifier();
    } else {
      return new DefaultStateVerifier();
    }
  }

  protected StateVerifier() {}

  /**
   * Throws an exception if we believe our object is recycled and inactive (i.e. is currently in an
   * object pool).
   */
  public abstract void throwIfRecycled();

  /** Sets whether or not our object is recycled. */
  abstract void setRecycled(boolean isRecycled);
}
