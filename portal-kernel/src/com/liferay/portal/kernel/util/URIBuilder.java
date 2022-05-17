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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.URI;
import java.net.URISyntaxException;

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
		_uri = HttpComponentsUtil.addParameter(_uri, name, value);

		return this;
	}

	public URI build() {
		if (Validator.isNull(_uri)) {
			return null;
		}

		try {
			return new URI(_uri);
		}
		catch (URISyntaxException uriSyntaxException) {
			_log.error("Unable to form uri from input: " + _uri);
		}

		return null;
	}

	public URIBuilder removeParameter(String name) {
		_uri = HttpComponentsUtil.removeParameter(_uri, name);

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
		_uri = HttpComponentsUtil.setParameter(_uri, name, value);

		return this;
	}

	private URIBuilder(String uri) {
		_uri = uri;
	}

	private static final Log _log = LogFactoryUtil.getLog(URIBuilder.class);

	private String _uri;

}