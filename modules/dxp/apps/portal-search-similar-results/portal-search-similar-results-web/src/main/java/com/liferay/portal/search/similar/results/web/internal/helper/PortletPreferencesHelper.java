/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.similar.results.web.internal.helper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 */
public class PortletPreferencesHelper {

	public PortletPreferencesHelper(PortletPreferences portletPreferences) {
		_portletPreferences = portletPreferences;
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
		return GetterUtil.getString(_getValue(key), defaultValue);
	}

	private String _getValue(String key) {
		if (_portletPreferences == null) {
			return null;
		}

		return _portletPreferences.getValue(key, StringPool.BLANK);
	}

	private final PortletPreferences _portletPreferences;

}