/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.cache;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.search.experiences.blueprint.exception.InvalidWebCacheItemException;
import com.liferay.search.experiences.ml.embedding.text.TextEmbeddingRetriever;

import java.beans.ExceptionListener;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = false, service = TextEmbeddingProviderCache.class)
public class TextEmbeddingProviderCache {

	public Double[] get(
		ExceptionListener exceptionListener, String providerName,
		long refreshTime, String text,
		TextEmbeddingRetriever textEmbeddingRetriever) {

		try {
			String key = StringBundler.concat(
				TextEmbeddingProviderCache.class.getName(), StringPool.POUND,
				providerName, StringPool.POUND, text);

			Double[] textEmbedding = _portalCache.get(key);

			if (textEmbedding != null) {
				return textEmbedding;
			}

			textEmbedding = _convert(
				providerName, text, textEmbeddingRetriever);

			_portalCache.put(
				key, textEmbedding, (int)(refreshTime / Time.SECOND));

			return textEmbedding;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			exceptionListener.exceptionThrown(exception);

			return new Double[0];
		}
	}

	@Activate
	protected void activate() {
		_portalCache =
			(PortalCache<String, Double[]>)_multiVMPool.getPortalCache(
				TextEmbeddingProviderCache.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(
			TextEmbeddingProviderCache.class.getName());
	}

	private Double[] _convert(
		String providerName, String text,
		TextEmbeddingRetriever textEmbeddingRetriever) {

		try {
			return textEmbeddingRetriever.getTextEmbedding(providerName, text);
		}
		catch (Exception exception) {
			throw new InvalidWebCacheItemException(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TextEmbeddingProviderCache.class);

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<String, Double[]> _portalCache;

}