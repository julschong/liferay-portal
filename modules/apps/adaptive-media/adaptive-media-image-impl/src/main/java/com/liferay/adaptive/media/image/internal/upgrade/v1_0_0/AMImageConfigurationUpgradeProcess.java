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

package com.liferay.adaptive.media.image.internal.upgrade.v1_0_0;

import com.liferay.adaptive.media.image.internal.configuration.AMImageConfiguration;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.io.IOException;

import java.lang.reflect.Method;

import java.sql.SQLException;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Julius Lee
 */
public class AMImageConfigurationUpgradeProcess extends UpgradeProcess {

	public AMImageConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade()
		throws InvalidSyntaxException, IOException, SQLException {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(", Constants.SERVICE_PID, "=",
				AMImageConfiguration.class.getName(), ")"));

		if (configurations == null) {
			return;
		}

		Configuration configuration = configurations[0];

		Dictionary<String, Object> dictionary = configuration.getProperties();

		dictionary.remove("imageMaxSize");

		configuration.updateIfDifferent(dictionary);

		Set<String> properties = new HashSet<>(
			TransformUtil.transformToList(
				AMImageConfiguration.class.getMethods(), Method::getName));

		Enumeration<String> enumeration = dictionary.keys();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			if (properties.contains(key)) {
				return;
			}
		}

		configuration.delete();
	}

	private final ConfigurationAdmin _configurationAdmin;

}