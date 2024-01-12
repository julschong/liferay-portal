/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.web.internal.util;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.security.auth.ScreenNameValidator;

import java.util.Locale;

/**
 * @author Julius Lee
 */
public class ScreenNameValidatorUtil {

	public static String getAUIValidatorJS() {
		return _getScreenNameValidator().getAUIValidatorJS();
	}

	public static String getDescription(Locale locale) {
		return _getScreenNameValidator().getDescription(locale);
	}

	private static ScreenNameValidator _getScreenNameValidator() {
		return _screenNameValidatorSnapshot.get();
	}

	private static final Snapshot<ScreenNameValidator>
		_screenNameValidatorSnapshot = new Snapshot<>(
			ScreenNameValidatorUtil.class, ScreenNameValidator.class, null,
			true);

}