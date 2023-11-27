/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.util;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.social.kernel.model.SocialActivityDefinition;

import java.util.List;

/**
 * @author Zsolt Berentey
 */
public class SocialConfigurationUtil {

	public static List<String> getActivityCounterNames() {
		return _getSocialConfiguration().getActivityCounterNames();
	}

	public static List<String> getActivityCounterNames(
		boolean transientCounter) {

		return _getSocialConfiguration().getActivityCounterNames(
			transientCounter);
	}

	public static List<String> getActivityCounterNames(int ownerType) {
		return _getSocialConfiguration().getActivityCounterNames(ownerType);
	}

	public static List<String> getActivityCounterNames(
		int ownerType, boolean transientCounter) {

		return _getSocialConfiguration().getActivityCounterNames(
			ownerType, transientCounter);
	}

	public static SocialActivityDefinition getActivityDefinition(
		String modelName, int activityType) {

		return _getSocialConfiguration().getActivityDefinition(
			modelName, activityType);
	}

	public static List<SocialActivityDefinition> getActivityDefinitions(
		String modelName) {

		return _getSocialConfiguration().getActivityDefinitions(modelName);
	}

	public static String[] getActivityModelNames() {
		return _getSocialConfiguration().getActivityModelNames();
	}

	public static List<Object> read(ClassLoader classLoader, String[] xmls)
		throws Exception {

		return _getSocialConfiguration().read(classLoader, xmls);
	}

	public static void removeActivityDefinition(
		SocialActivityDefinition activityDefinition) {

		_getSocialConfiguration().removeActivityDefinition(activityDefinition);
	}

	private static SocialConfiguration _getSocialConfiguration() {
		return _socialConfigurationSnapshot.get();
	}

	private static final Snapshot<SocialConfiguration>
		_socialConfigurationSnapshot = new Snapshot<>(
			SocialConfigurationUtil.class, SocialConfiguration.class);

}