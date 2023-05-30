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

package com.liferay.configuration.admin.display;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge Ferrer
 */
public abstract class BaseConfigurationFormRenderer
	implements ConfigurationFormRenderer {

	protected Map<String, Object> getConfigurationsMap(
			HttpServletRequest httpServletRequest, Class<?>... classes)
		throws IOException {

		Map<String, Object> configurationsMap = new HashMap<>();

		if (Objects.equals(
				PortalUtil.getPortletId(httpServletRequest),
				ConfigurationAdminPortletKeys.INSTANCE_SETTINGS)) {

			for (Class<?> clazz : classes) {
				try {
					configurationsMap.put(
						clazz.getName(),
						ConfigurationProviderUtil.getCompanyConfiguration(
							clazz,
							PortalUtil.getCompanyId(httpServletRequest)));
				}
				catch (ConfigurationException configurationException) {
					throw new RuntimeException(configurationException);
				}
			}
		}
		else {
			for (Class<?> clazz : classes) {
				try {
					configurationsMap.put(
						clazz.getName(),
						ConfigurationProviderUtil.getSystemConfiguration(
							clazz));
				}
				catch (ConfigurationException configurationException) {
					throw new RuntimeException(configurationException);
				}
			}
		}

		return configurationsMap;
	}

}