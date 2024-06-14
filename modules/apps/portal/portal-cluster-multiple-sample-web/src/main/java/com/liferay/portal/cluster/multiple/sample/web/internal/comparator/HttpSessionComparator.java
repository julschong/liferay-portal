/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.sample.web.internal.comparator;

import com.liferay.portal.kernel.util.StringComparator;

import java.util.Comparator;

import javax.servlet.http.HttpSession;

/**
 * @author Julius Lee
 */
public class HttpSessionComparator implements Comparator<HttpSession> {

	@Override
	public int compare(HttpSession httpSession1, HttpSession httpSession2) {
		StringComparator stringComparator = new StringComparator();

		return stringComparator.compare(
			httpSession1.getId(), httpSession2.getId());
	}

}