/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
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



import com.mbg.module.ui.image.cache.engine.DisplayImageOptions;
import com.mbg.module.ui.image.cache.engine.listener.ImageLoadingListener;
import com.mbg.module.ui.image.cache.engine.listener.ImageLoadingProgressListener;
import com.mbg.module.ui.image.cache.model.ImageAware;
import com.mbg.module.ui.image.cache.model.ImageSize;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Information for load'n'display image task
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 *  com.nostra13.universalimageloader.utils.MemoryCacheUtils
 *  DisplayImageOptions
 *  ImageLoadingListener
 *  com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener
 * @since 1.3.1
 */
public final class ImageLoadingInfo {

	public final String uri;
	public final String memoryCacheKey;
	public final ImageAware imageAware;
	public final ImageSize targetSize;
	public final DisplayImageOptions options;
	public final ImageLoadingListener listener;
	public final ImageLoadingProgressListener progressListener;
	public final ReentrantLock loadFromUriLock;

	public ImageLoadingInfo(String uri, ImageAware imageAware, ImageSize targetSize, String memoryCacheKey,
							DisplayImageOptions options, ImageLoadingListener listener,
							ImageLoadingProgressListener progressListener, ReentrantLock loadFromUriLock) {
		this.uri = uri;
		this.imageAware = imageAware;
		this.targetSize = targetSize;
		this.options = options;
		this.listener = listener;
		this.progressListener = progressListener;
		this.loadFromUriLock = loadFromUriLock;
		this.memoryCacheKey = memoryCacheKey;
	}
}
