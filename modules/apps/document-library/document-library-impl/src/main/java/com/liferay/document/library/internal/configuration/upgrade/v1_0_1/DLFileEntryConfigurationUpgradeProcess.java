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

package com.liferay.document.library.internal.configuration.upgrade.v1_0_1;

import com.liferay.document.library.internal.configuration.DLFileEntryConfiguration;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.KeyValuePair;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	properties = "upgrade.class.name=com.liferay.document.library.internal.configuration.upgrade.v1_0_1.DLFileEntryConfigurationUpgradeProcess",
	service = UpgradeProcess.class
)
public class DLFileEntryConfigurationUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_upgradeConfiguration();
	}

	private void _upgradeConfiguration() throws Exception {
		_prefsPropsToConfigurationUpgradeHelper.mapConfigurations(
			DLFileEntryConfiguration.class,
			new KeyValuePair(
				"dl.file.entry.previewable.processor.max.size",
				"previewableProcessorMaxSize"));
	}

	@Reference
	private PrefsPropsToConfigurationUpgradeHelper
		_prefsPropsToConfigurationUpgradeHelper;

}