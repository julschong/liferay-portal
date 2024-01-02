/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.key;

import com.liferay.petra.nio.CharsetEncoderUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.key.CacheKeyGenerator;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;

import java.security.MessageDigest;

/**
 * @author Julius Lee
 */
public enum CacheKeyGeneratorType implements CacheKeyGenerator {

	HASH_CODE {

		@Override
		public Serializable getCacheKey(String key) {
			return StringUtil.toHexString(key.hashCode());
		}

		@Override
		public Serializable getCacheKey(String[] keys) {
			int hashCode = 0;
			int weight = 1;

			for (int i = keys.length - 1; i >= 0; i--) {
				String s = keys[i];

				hashCode = (s.hashCode() * weight) + hashCode;

				for (int j = s.length(); j > 0; j--) {
					weight *= 31;
				}
			}

			return StringUtil.toHexString(hashCode);
		}

		@Override
		public Serializable getCacheKey(StringBundler sb) {
			return getCacheKey(sb.getStrings());
		}

	},
	MESSAGE_DIGEST {

		@Override
		public Serializable getCacheKey(String key) {
			return _getCacheKey(new String[] {key}, 1);
		}

		@Override
		public Serializable getCacheKey(String[] keys) {
			return _getCacheKey(keys, keys.length);
		}

		@Override
		public Serializable getCacheKey(StringBundler sb) {
			return _getCacheKey(sb.getStrings(), sb.index());
		}

		private Serializable _getCacheKey(String[] keys, int length) {
			try {
				ThreadLocalCache<MessageDigest> threadLocalCache =
					ThreadLocalCacheManager.getThreadLocalCache(
						Lifecycle.ETERNAL,
						CacheKeyGeneratorType.class.getName() +
							"#MESSAGE_DIGEST");

				String algorithm = "SHA-1";

				MessageDigest messageDigest = threadLocalCache.get(algorithm);

				if (messageDigest == null) {
					messageDigest = MessageDigest.getInstance(algorithm);

					threadLocalCache.put(algorithm, messageDigest);
				}

				CharsetEncoder charsetEncoder =
					CharsetEncoderUtil.getCharsetEncoder(StringPool.UTF8);

				for (int i = 0; i < length; i++) {
					messageDigest.update(
						charsetEncoder.encode(CharBuffer.wrap(keys[i])));
				}

				return StringUtil.bytesToHexString(messageDigest.digest());
			}
			catch (Exception exception) {
				throw new SystemException(exception);
			}
		}

	},
	SIMPLE {

		@Override
		public Serializable getCacheKey(String key) {
			return key;
		}

		@Override
		public Serializable getCacheKey(String[] keys) {
			StringBundler sb = new StringBundler(keys);

			return sb.toString();
		}

		@Override
		public Serializable getCacheKey(StringBundler sb) {
			return sb.toString();
		}

	},

}