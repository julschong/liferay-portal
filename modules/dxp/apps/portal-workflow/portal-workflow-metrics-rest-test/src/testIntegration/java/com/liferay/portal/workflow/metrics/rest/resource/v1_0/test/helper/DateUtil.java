/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Julius Lee
 */
public class DateUtil {

	public static Date truncateTime(Date date, int calendarField) {
		if (ArrayUtil.contains(_TIMES, calendarField)) {
			throw new IllegalArgumentException(
				"truncate does not support calendar field: " + calendarField);
		}

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		for (int time : _TIMES) {
			if (calendarField == time) {
				return calendar.getTime();
			}

			calendar.set(time, 0);
		}

		return calendar.getTime();
	}

	private static final int[] _TIMES = {
		Calendar.MILLISECOND, Calendar.SECOND, Calendar.MINUTE, Calendar.HOUR
	};

}