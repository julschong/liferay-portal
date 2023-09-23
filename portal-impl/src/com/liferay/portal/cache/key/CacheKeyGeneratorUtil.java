/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.key;

import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class CacheKeyGeneratorUtil {

	public static void addCacheKeyGenerator(
		String name, CacheKeyGenerator cacheKeyGenerator) {

		if (cacheKeyGenerator == null) {
			throw new IllegalArgumentException(
				"Cache key generator cannot be null");
		}

		if (_cacheKeyGeneratorsMap.containsKey(name)) {
			throw new IllegalArgumentException(
				"Cache key generator with name: " + name + " already exists");
		}

		_cacheKeyGeneratorsMap.put(name, cacheKeyGenerator);
	}

	public static CacheKeyGenerator getCacheKeyGenerator(String name) {
		if (!_cacheKeyGeneratorsMap.containsKey(name)) {
			throw new IllegalArgumentException(
				"Cannot get cache key generator with name: " + name);
		}

		return _cacheKeyGeneratorsMap.get(name);
	}

	private static final Map<String, CacheKeyGenerator> _cacheKeyGeneratorsMap =
		HashMapBuilder.<String, CacheKeyGenerator>put(
			CacheKeyGenerator.DIGEST_CACHE_GENERATOR_NAME,
			new MessageDigestCacheKeyGenerator(Digester.SHA_1)
		).put(
			CacheKeyGenerator.HASH_CODE_CACHE_GENERATOR_NAME,
			new HashCodeHexStringCacheKeyGenerator()
		).put(
			CacheKeyGenerator.SIMPLE_CACHE_GENERATOR_NAME,
			new SimpleCacheKeyGenerator()
		).build();

}