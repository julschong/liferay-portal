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

package com.liferay.portal.search.internal.index;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.internal.index.configuration.IndexStatusManagerInternalConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Julius Lee
 */
@Component(
	configurationPid = "com.liferay.portal.search.internal.index.configuration.IndexStatusManagerInternalConfiguration",
	service = IndexStatusInternalConfigurationProvider.class
)
public class IndexStatusInternalConfigurationProvider {

	public boolean getSuppressIndexReadOnly() {
		return _indexStatusManagerInternalConfiguration.suppressIndexReadOnly();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_indexStatusManagerInternalConfiguration =
			ConfigurableUtil.createConfigurable(
				IndexStatusManagerInternalConfiguration.class, properties);
	}

	private volatile IndexStatusManagerInternalConfiguration
		_indexStatusManagerInternalConfiguration;

}