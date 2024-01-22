/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.language;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Shuyang Zhou
 * @author Kamesh Sampath
 */
public class LanguageResourcesUtil {

	public static String getMessage(Locale locale, String key) {
		return _getLanguageResources().getMessage(locale, key);
	}

	public static ResourceBundle getResourceBundle(Locale locale) {
		return _getLanguageResources().getResourceBundle(locale);
	}

	public static Locale getSuperLocale(Locale locale) {
		return _getLanguageResources().getSuperLocale(locale);
	}

	private static LanguageResources _getLanguageResources() {
		return _languageResourcesSnapshot.get();
	}

	private static final Snapshot<LanguageResources>
		_languageResourcesSnapshot = new Snapshot<>(
			LanguageResourcesUtil.class, LanguageResources.class, null);

}