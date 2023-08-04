/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.webcache;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class WebCachePoolImpl implements WebCachePool {

	@Override
	public void clearCache(String cacheName) {
		PortalCache<String, ?> portalCache = _portalCacheMap.get(cacheName);

		if (portalCache == null) {
			return;
		}

		portalCache.removeAll();
	}

	@Override
	public Object get(String cacheName, String key, WebCacheItem webCacheItem) {
		PortalCache<String, Object> portalCache =
			_portalCacheMap.computeIfAbsent(
				webCacheItem.getClass(
				).getName(),
				k -> PortalCacheHelperUtil.getPortalCache(
					PortalCacheManagerNames.SINGLE_VM, cacheName));

		Object object = portalCache.get(key);

		if (object != null) {
			return object;
		}

		try {
			object = webCacheItem.convert(key);

			if (object == null) {
				return null;
			}

			int timeToLive = (int)(webCacheItem.getRefreshTime() / Time.SECOND);

			if (timeToLive > 0) {
				portalCache.put(key, object, timeToLive);
			}
		}
		catch (WebCacheException webCacheException) {
			if (_log.isWarnEnabled()) {
				Throwable throwable = webCacheException.getCause();

				if (throwable != null) {
					_log.warn(throwable, throwable);
				}
				else {
					_log.warn(webCacheException);
				}
			}
		}

		return object;
	}

	@Override
	public void remove(String cacheName, String key) {
		PortalCache<String, Object> portalCache = _portalCacheMap.get(
			cacheName);

		if (portalCache == null) {
			return;
		}

		portalCache.remove(key);
	}

	@Override
	public void removeCache(String cacheName) {
		_portalCacheMap.remove(cacheName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebCachePoolImpl.class);

	private final Map<String, PortalCache<String, Object>> _portalCacheMap =
		new HashMap<>();

}