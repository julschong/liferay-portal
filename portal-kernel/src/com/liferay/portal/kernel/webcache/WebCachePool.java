/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.webcache;

/**
 * @author Brian Wing Shun Chan
 */
public interface WebCachePool {

	public void clearCache(String cacheName);

	public Object get(String cacheName, String key, WebCacheItem webCacheItem);

	public void remove(String cacheName, String key);

	public void removeCache(String cacheName);

}