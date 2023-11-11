/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Julius Lee
 */
public class DLStoreFileUtil {

	public static String decodeSafeFileName(String fileName) {
		return StringUtil.replace(
			fileName, _SAFE_FILE_NAME_2, _SAFE_FILE_NAME_1);
	}

	public static String encodeSafeFileName(String fileName) {
		if (fileName == null) {
			return StringPool.BLANK;
		}

		return StringUtil.replace(
			fileName, _SAFE_FILE_NAME_1, _SAFE_FILE_NAME_2);
	}

	private static final String[] _SAFE_FILE_NAME_1 = {
		StringPool.AMPERSAND, StringPool.CLOSE_PARENTHESIS,
		StringPool.OPEN_PARENTHESIS, StringPool.SEMICOLON
	};

	private static final String[] _SAFE_FILE_NAME_2 = {
		PropsUtil.get(PropsKeys.DL_STORE_FILE_IMPL_SAFE_FILE_NAME_2_AMPERSAND),
		PropsUtil.get(
			PropsKeys.DL_STORE_FILE_IMPL_SAFE_FILE_NAME_2_CLOSE_PARENTHESIS),
		PropsUtil.get(
			PropsKeys.DL_STORE_FILE_IMPL_SAFE_FILE_NAME_2_OPEN_PARENTHESIS),
		PropsUtil.get(PropsKeys.DL_STORE_FILE_IMPL_SAFE_FILE_NAME_2_SEMICOLON)
	};

}