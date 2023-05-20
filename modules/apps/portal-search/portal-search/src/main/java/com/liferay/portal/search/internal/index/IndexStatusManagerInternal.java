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
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.search.IndexStatusManagerThreadLocal;
import com.liferay.portal.search.index.IndexStatusManager;
import com.liferay.portal.search.internal.index.configuration.IndexStatusManagerInternalConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julius Lee
 */
@Component(
	configurationPid = "com.liferay.portal.search.internal.index.configuration.IndexStatusManagerInternalConfiguration",
	service = IndexStatusManagerInternal.class
)
public class IndexStatusManagerInternal {

	public boolean isIndexReadOnly() {
		if (_suppressIndexReadOnly) {
			return false;
		}

		if (IndexStatusManagerThreadLocal.isIndexReadOnly() ||
			_indexStatusManager.isIndexReadOnly() ||
			StartupHelperUtil.isUpgrading()) {

			return true;
		}

		return false;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		IndexStatusManagerInternalConfiguration
			indexStatusManagerInternalConfiguration =
				ConfigurableUtil.createConfigurable(
					IndexStatusManagerInternalConfiguration.class, properties);

		_suppressIndexReadOnly =
			indexStatusManagerInternalConfiguration.suppressIndexReadOnly();
	}

	@Reference
	private IndexStatusManager _indexStatusManager;

	private volatile boolean _suppressIndexReadOnly;

}