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

package com.liferay.portal.search.web.internal.helper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 */
public class PortletPreferencesHelper {

	public PortletPreferencesHelper(PortletPreferences portletPreferences) {
		_portletPreferences = portletPreferences;
	}

	public Boolean getBoolean(String key) {
		String value = _getValue(key);

		if (value == null) {
			return null;
		}

		return GetterUtil.getBoolean(value);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return GetterUtil.getBoolean(_getValue(key), defaultValue);
	}

	public Integer getInteger(String key) {
		String value = _getValue(key);

		if (value == null) {
			return null;
		}

		return GetterUtil.getInteger(value);
	}

	public int getInteger(String key, int defaultValue) {
		return GetterUtil.getInteger(_getValue(key), defaultValue);
	}

	public String getString(String key) {
		return _getValue(key);
	}

	public String getString(String key, String defaultValue) {
		return GetterUtil.getString(getString(key), defaultValue);
	}

	private String _getValue(String key) {
		if (_portletPreferences == null) {
			return null;
		}

		return StringUtil.trim(
			_portletPreferences.getValue(key, StringPool.BLANK));
	}

	private final PortletPreferences _portletPreferences;

}