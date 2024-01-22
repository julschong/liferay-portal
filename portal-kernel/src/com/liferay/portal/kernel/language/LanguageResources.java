/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.language;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Shuyang Zhou
 * @author Kamesh Sampath
 */
public interface LanguageResources {

	public String getMessage(Locale locale, String key);

	public ResourceBundle getResourceBundle(Locale locale);

	public Locale getSuperLocale(Locale locale);

}