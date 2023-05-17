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

package com.liferay.portlet.configuration.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultPortletConfigurationUtil {

	public static void addDefaultPortletConfiguration(
		Layout layout, String portletId, Map<String, String> preferencesMap) {

		_defaultConfigurationsMap.put(
			portletId,
			new DefaultConfiguration(layout, portletId, preferencesMap));
	}

	public static boolean hasDefaultPortletConfiguration(String portletId) {
		return _defaultConfigurationsMap.containsKey(portletId);
	}

	public static PortletPreferences initializeDefaultPortletConfiguration(
		String portletId) {

		if (Validator.isNull(portletId) ||
			hasDefaultPortletConfiguration(portletId)) {

			return null;
		}

		DefaultConfiguration defaultConfiguration =
			_defaultConfigurationsMap.get(portletId);

		try {
			return defaultConfiguration.initialize();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	protected static boolean isUseCustomTitle(PortletPreferences portletSetup) {
		return GetterUtil.getBoolean(
			portletSetup.getValue("portletSetupUseCustomTitle", null));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultPortletConfigurationUtil.class);

	private static final Map<String, DefaultConfiguration>
		_defaultConfigurationsMap = new HashMap<>();

	private static class DefaultConfiguration {

		public PortletPreferences initialize() throws Exception {
			PortletPreferences portletSetup =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					_layout, _portletId);

			for (Map.Entry<String, String> entry : _preferencesMap.entrySet()) {
				portletSetup.setValue(entry.getKey(), entry.getValue());
			}

			portletSetup.store();

			_defaultConfigurationsMap.remove(_portletId);

			return portletSetup;
		}

		private DefaultConfiguration(
			Layout layout, String portletId,
			Map<String, String> preferencesMap) {

			_layout = layout;
			_portletId = portletId;
			_preferencesMap = preferencesMap;
		}

		private final Layout _layout;
		private final String _portletId;
		private final Map<String, String> _preferencesMap;

	}

}