/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.key;

import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class CacheKeyGeneratorUtil {

	public static final String DEFAULT = "DEFAULT";

	public static final String HASH_CODE_HEX_STRING = "HASH_CODE";

	public static final String MESSAGE_DIGEST = "MESSAGE_DIGEST";

	public static final String SIMPLE = "SIMPLE";

	public static CacheKeyGenerator getCacheKeyGenerator(String cacheName) {
		CacheKeyGenerator cacheKeyGenerator = _cacheKeyGeneratorsMap.get(
			cacheName);

		if (cacheKeyGenerator != null) {
			return cacheKeyGenerator;
		}

		throw new IllegalArgumentException(
			"Could not get cache key generator named: " + cacheName);
	}

	public void setDefaultCacheKeyGenerator(
		CacheKeyGenerator defaultCacheKeyGenerator) {

		_cacheKeyGeneratorsMap.put(DEFAULT, defaultCacheKeyGenerator);
	}

	private static final Map<String, CacheKeyGenerator> _cacheKeyGeneratorsMap =
		HashMapBuilder.<String, CacheKeyGenerator>put(
			DEFAULT, new SimpleCacheKeyGenerator()
		).put(
			HASH_CODE_HEX_STRING, new HashCodeHexStringCacheKeyGenerator()
		).put(
			MESSAGE_DIGEST, new MessageDigestCacheKeyGenerator("SHA-1")
		).put(
			SIMPLE, new SimpleCacheKeyGenerator()
		).build();

}