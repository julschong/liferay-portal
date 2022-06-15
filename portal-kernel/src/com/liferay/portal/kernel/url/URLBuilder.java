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

package com.liferay.portal.kernel.url;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Julius Lee
 */
public class URLBuilder {

	public static URLBuilder create(String url) {
		return new URLBuilder(url);
	}

	public URLBuilder addParameter(String name, boolean value) {
		addParameter(name, String.valueOf(value));

		return this;
	}

	public URLBuilder addParameter(String name, double value) {
		addParameter(name, String.valueOf(value));

		return this;
	}

	public URLBuilder addParameter(String name, int value) {
		addParameter(name, String.valueOf(value));

		return this;
	}

	public URLBuilder addParameter(String name, long value) {
		addParameter(name, String.valueOf(value));

		return this;
	}

	public URLBuilder addParameter(String name, short value) {
		addParameter(name, String.valueOf(value));

		return this;
	}

	public URLBuilder addParameter(String name, String value) {
		if (url == null) {
			return null;
		}

		String[] urlArray = PortalUtil.stripURLAnchor(url, StringPool.POUND);

		url = urlArray[0];

		String anchor = urlArray[1];

		StringBundler sb = new StringBundler(6);

		sb.append(url);

		if (url.indexOf(CharPool.QUESTION) == -1) {
			sb.append(StringPool.QUESTION);
		}
		else if (!url.endsWith(StringPool.QUESTION) &&
				 !url.endsWith(StringPool.AMPERSAND)) {

			sb.append(StringPool.AMPERSAND);
		}

		sb.append(name);
		sb.append(StringPool.EQUAL);
		sb.append(URLCodec.encodeURL(value));
		sb.append(anchor);

		return shortenURL(sb.toString());
	}

	public String build() {
		return _url;
	}

	public URLBuilder removeParameter(String name) {
		if (Validator.isNull(url) || Validator.isNull(name)) {
			return url;
		}

		int pos = url.indexOf(CharPool.QUESTION);

		if (pos == -1) {
			return url;
		}

		String[] array = PortalUtil.stripURLAnchor(url, StringPool.POUND);

		url = array[0];

		String anchor = array[1];

		StringBundler sb = new StringBundler();

		sb.append(url.substring(0, pos + 1));

		String[] parameters = StringUtil.split(
			url.substring(pos + 1), CharPool.AMPERSAND);

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

		url = StringUtil.replace(
			sb.toString(), StringPool.AMPERSAND + StringPool.AMPERSAND,
			StringPool.AMPERSAND);

		if (url.endsWith(StringPool.AMPERSAND)) {
			url = url.substring(0, url.length() - 1);
		}

		if (url.endsWith(StringPool.QUESTION)) {
			url = url.substring(0, url.length() - 1);
		}

		return url + anchor;
	}

	public URLBuilder setParameter(String name, boolean value) {
		return setParameter(name, String.valueOf(value));
	}

	public URLBuilder setParameter(String name, double value) {
		return setParameter(name, String.valueOf(value));
	}

	public URLBuilder setParameter(String name, int value) {
		return setParameter(name, String.valueOf(value));
	}

	public URLBuilder setParameter(String name, long value) {
		return setParameter(name, String.valueOf(value));
	}

	public URLBuilder setParameter(String name, short value) {
		return setParameter(name, String.valueOf(value));
	}

	public URLBuilder setParameter(String name, String value) {
		if (Validator.isNull(url) || Validator.isNull(name)) {
			return url;
		}

		url = removeParameter(url, name);

		return addParameter(url, name, value);
	}

	public String toString() {
		return _url;
	}

	private URLBuilder(String url) {
		_url = url;
	}

	private final String _url;

}