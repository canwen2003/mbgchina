/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.mbg.module.ui.image.cache.engine.imageloader;


import com.mbg.module.ui.image.cache.engine.factory.DefaultConfigurationFactory;
import com.mbg.module.ui.image.cache.engine.task.LoadAndDisplayImageTask;
import com.mbg.module.ui.image.cache.engine.task.ProcessAndDisplayImageTask;
import com.mbg.module.ui.image.cache.model.ImageAware;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * { ImageLoader} engine which responsible for { LoadAndDisplayImageTask display task} execution.
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.7.1
 */
public class ImageLoaderEngine {

	private final ImageLoaderConfiguration configuration;

	private Executor taskExecutor;
	private Executor taskExecutorForCachedImages;
	private Executor taskDistributor;

	private final Map<Integer, String> cacheKeysForImageAwares = Collections
			.synchronizedMap(new HashMap<Integer, String>());
	private final Map<String, ReentrantLock> uriLocks = new WeakHashMap<String, ReentrantLock>();

	private final AtomicBoolean paused = new AtomicBoolean(false);
	private final AtomicBoolean networkDenied = new AtomicBoolean(false);
	private final AtomicBoolean slowNetwork = new AtomicBoolean(false);

	private final Object pauseLock = new Object();

	ImageLoaderEngine(ImageLoaderConfiguration configuration) {
		this.configuration = configuration;

		taskExecutor = configuration.getTaskExecutor();
		taskExecutorForCachedImages = configuration.getTaskExecutorForCachedImages();

		taskDistributor = DefaultConfigurationFactory.createTaskDistributor();
	}

	/** Submits task to execution pool */
	void submit(final LoadAndDisplayImageTask task) {
		taskDistributor.execute(new Runnable() {
			@Override
			public void run() {
				File image = configuration.getDiskCache().get(task.getLoadingUri());
				boolean isImageCachedOnDisk = image != null && image.exists();
				initExecutorsIfNeed();
				if (isImageCachedOnDisk) {
					taskExecutorForCachedImages.execute(task);
				} else {
					taskExecutor.execute(task);
				}
			}
		});
	}

	/** Submits task to execution pool */
	void submit(ProcessAndDisplayImageTask task) {
		initExecutorsIfNeed();
		taskExecutorForCachedImages.execute(task);
	}

	private void initExecutorsIfNeed() {
		if (!configuration.isCustomExecutor() && ((ExecutorService) taskExecutor).isShutdown()) {
			taskExecutor = createTaskExecutor();
		}
		if (!configuration.isCustomExecutorForCachedImages() && ((ExecutorService) taskExecutorForCachedImages)
				.isShutdown()) {
			taskExecutorForCachedImages = createTaskExecutor();
		}
	}

	private Executor createTaskExecutor() {
		return DefaultConfigurationFactory
				.createExecutor(configuration.getThreadPoolSize(), configuration.getThreadPriority(),
				configuration.getTasksProcessingType());
	}

	/**
	 * Returns URI of image which is loading at this moment into passed { com.nostra13.universalimageloader.core.imageaware.ImageAware}
	 */
	public String getLoadingUriForView(ImageAware imageAware) {
		return cacheKeysForImageAwares.get(imageAware.getId());
	}

	/**
	 * Associates <b>memoryCacheKey</b> with <b>imageAware</b>. Then it helps to define image URI is loaded into View at
	 * exact moment.
	 */
	void prepareDisplayTaskFor(ImageAware imageAware, String memoryCacheKey) {
		cacheKeysForImageAwares.put(imageAware.getId(), memoryCacheKey);
	}

	/**
	 * Cancels the task of loading and displaying image for incoming <b>imageAware</b>.
	 *
	 * @param imageAware { com.nostra13.universalimageloader.core.imageaware.ImageAware} for which display task
	 *                   will be cancelled
	 */
	public void cancelDisplayTaskFor(ImageAware imageAware) {
		cacheKeysForImageAwares.remove(imageAware.getId());
	}

	/**
	 * Denies or allows engine to download images from the network.<br /> <br /> If downloads are denied and if image
	 * isn't cached then { ImageLoadingListener#onLoadingFailed(String, View, FailReason)} callback will be fired
	 * with { FailReason.FailType#NETWORK_DENIED}
	 *
	 * @param denyNetworkDownloads pass <b>true</b> - to deny engine to download images from the network; <b>false</b> -
	 *                             to allow engine to download images from network.
	 */
	void denyNetworkDownloads(boolean denyNetworkDownloads) {
		networkDenied.set(denyNetworkDownloads);
	}

	/**
	 * Sets option whether ImageLoader will use { FlushedInputStream} for network downloads to handle <a
	 * href="http://code.google.com/p/android/issues/detail?id=6066">this known problem</a> or not.
	 *
	 * @param handleSlowNetwork pass <b>true</b> - to use { FlushedInputStream} for network downloads; <b>false</b>
	 *                          - otherwise.
	 */
	void handleSlowNetwork(boolean handleSlowNetwork) {
		slowNetwork.set(handleSlowNetwork);
	}

	/**
	 * Pauses engine. All new "load&display" tasks won't be executed until ImageLoader is { #resume() resumed}.<br
	 * /> Already running tasks are not paused.
	 */
	void pause() {
		paused.set(true);
	}

	/** Resumes engine work. Paused "load&display" tasks will continue its work. */
	void resume() {
		paused.set(false);
		synchronized (pauseLock) {
			pauseLock.notifyAll();
		}
	}

	/**
	 * Stops engine, cancels all running and scheduled display image tasks. Clears internal data.
	 * <br />
	 * <b>NOTE:</b> This method doesn't shutdown
	 * { com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder#taskExecutor(Executor)
	 * custom task executors} if you set them.
	 */
	void stop() {
		if (!configuration.isCustomExecutor()) {
			((ExecutorService) taskExecutor).shutdownNow();
		}
		if (!configuration.isCustomExecutorForCachedImages()) {
			((ExecutorService) taskExecutorForCachedImages).shutdownNow();
		}

		cacheKeysForImageAwares.clear();
		uriLocks.clear();
	}

	public void fireCallback(Runnable r) {
		taskDistributor.execute(r);
	}

	ReentrantLock getLockForUri(String uri) {
		ReentrantLock lock = uriLocks.get(uri);
		if (lock == null) {
			lock = new ReentrantLock();
			uriLocks.put(uri, lock);
		}
		return lock;
	}

	public AtomicBoolean getPause() {
		return paused;
	}

	public Object getPauseLock() {
		return pauseLock;
	}

	public boolean isNetworkDenied() {
		return networkDenied.get();
	}

	public boolean isSlowNetwork() {
		return slowNetwork.get();
	}

	public ImageLoaderConfiguration getConfiguration() {
		return configuration;
	}
}
