/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.property;

import com.liferay.petra.string.StringPool;
import com.liferay.search.experiences.blueprint.exception.UnresolvedTemplateVariableException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class PropertyValidator {

	public static <T> T validate(T object) {
		String string = object.toString();

		if ((string == null) || string.isEmpty()) {
			return object;
		}

		List<String> templateVariables = new ArrayList<>();

		int pos = 0;

		while (pos < (string.length() - 1)) {
			pos = string.indexOf(StringPool.DOLLAR_AND_OPEN_CURLY_BRACE, pos);

			if (pos == -1) {
				break;
			}

			int closingPos = string.indexOf(
				StringPool.CLOSE_CURLY_BRACE, pos + 2);

			if (closingPos == -1) {
				break;
			}

			templateVariables.add(string.substring(pos + 2, closingPos));

			pos = closingPos + 1;
		}

		if (!templateVariables.isEmpty()) {
			throw UnresolvedTemplateVariableException.with(
				templateVariables.toArray(new String[0]));
		}

		return object;
	}

}