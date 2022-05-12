/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;

import java.net.URI;

/**
 * @author Julius Lee
 */
public class URIBuilder {

	public static URIBuilder create(String uri) {
		return new URIBuilder(uri);
	}

	public static URIBuilder create(URI uri) {
		return new URIBuilder(uri.toString());
	}

	public URIBuilder addParameter(String name, boolean value) {
		return addParameter(name, String.valueOf(value));
	}

	public URIBuilder addParameter(String name, double value) {
		return addParameter(name, String.valueOf(value));
	}

	public URIBuilder addParameter(String name, int value) {
		return addParameter(name, String.valueOf(value));
	}

	public URIBuilder addParameter(String name, long value) {
		return addParameter(name, String.valueOf(value));
	}

	public URIBuilder addParameter(String name, short value) {
		return addParameter(name, String.valueOf(value));
	}

	public URIBuilder addParameter(String name, String value) {
		if (Validator.isNull(_uri) || Validator.isNull(value)) {
			return this;
		}

		String[] urlArray = PortalUtil.stripURLAnchor(_uri, StringPool.POUND);

		_uri = urlArray[0];

		String anchor = urlArray[1];

		StringBundler sb = new StringBundler(6);

		sb.append(_uri);

		if (_uri.indexOf(CharPool.QUESTION) == -1) {
			sb.append(StringPool.QUESTION);
		}
		else if (!_uri.endsWith(StringPool.QUESTION) &&
				 !_uri.endsWith(StringPool.AMPERSAND)) {

			sb.append(StringPool.AMPERSAND);
		}

		sb.append(name);
		sb.append(StringPool.EQUAL);
		sb.append(URLCodec.encodeURL(value));
		sb.append(anchor);

		_uri = HttpComponentsUtil.shortenURL(sb.toString());

		return this;
	}

	public String build() {
		if (Validator.isNull(_uri)) {
			return null;
		}

		return _uri;
	}

	public URIBuilder encodeParameters() {
		if (Validator.isNull(_uri)) {
			return this;
		}

		String queryString = HttpComponentsUtil.getQueryString(_uri);

		if (Validator.isNull(queryString)) {
			return this;
		}

		String encodedQueryString = HttpComponentsUtil.parameterMapToString(
			HttpComponentsUtil.parameterMapFromString(queryString), false);

		_uri = StringUtil.replace(_uri, queryString, encodedQueryString);

		return this;
	}

	public URIBuilder removeParameter(String name) {
		if (Validator.isNull(_uri) || Validator.isNull(name)) {
			return this;
		}

		int pos = _uri.indexOf(CharPool.QUESTION);

		if (pos == -1) {
			return this;
		}

		String[] array = PortalUtil.stripURLAnchor(_uri, StringPool.POUND);

		_uri = array[0];

		String anchor = array[1];

		StringBundler sb = new StringBundler();

		sb.append(_uri.substring(0, pos + 1));

		String[] parameters = StringUtil.split(
			_uri.substring(pos + 1), CharPool.AMPERSAND);

		for (String parameter : parameters) {
			if (parameter.length() > 0) {
				String[] kvp = StringUtil.split(parameter, CharPool.EQUAL);

				String key = kvp[0];

				String value = StringPool.BLANK;

				if (kvp.length > 1) {
					value = kvp[1];
				}

				if (!key.equals(name)) {
					sb.append(key);
					sb.append(StringPool.EQUAL);
					sb.append(value);
					sb.append(StringPool.AMPERSAND);
				}
			}
		}

		_uri = StringUtil.replace(
			sb.toString(), StringPool.AMPERSAND + StringPool.AMPERSAND,
			StringPool.AMPERSAND);

		if (_uri.endsWith(StringPool.AMPERSAND)) {
			_uri = _uri.substring(0, _uri.length() - 1);
		}

		if (_uri.endsWith(StringPool.QUESTION)) {
			_uri = _uri.substring(0, _uri.length() - 1);
		}

		_uri = _uri + anchor;

		return this;
	}

	public URIBuilder setParameter(String name, boolean value) {
		return setParameter(name, String.valueOf(value));
	}

	public URIBuilder setParameter(String name, double value) {
		return setParameter(name, String.valueOf(value));
	}

	public URIBuilder setParameter(String name, int value) {
		return setParameter(name, String.valueOf(value));
	}

	public URIBuilder setParameter(String name, long value) {
		return setParameter(name, String.valueOf(value));
	}

	public URIBuilder setParameter(String name, short value) {
		return setParameter(name, String.valueOf(value));
	}

	public URIBuilder setParameter(String name, String value) {
		if (Validator.isNull(_uri) || Validator.isNull(name)) {
			return this;
		}

		removeParameter(name);

		return addParameter(name, value);
	}

	private URIBuilder(String uri) {
		_uri = uri;
	}

	private String _uri;

}