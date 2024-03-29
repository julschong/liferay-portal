/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.text.DateFormat;
import java.text.Format;

import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author Roberto Díaz
 */
@NewEnv(type = NewEnv.Type.JVM)
@NewEnv.JVMArgsLine("-Djava.locale.providers=JRE,COMPAT,CLDR")
public class FastDateFormatFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDifferentLocale() {
		Format format = _fastDateFormatFactory.getDateTime(
			DateFormat.SHORT, DateFormat.SHORT, LocaleUtil.BRAZIL,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals("28/03/24 10:22", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.MEDIUM, DateFormat.MEDIUM, LocaleUtil.BRAZIL,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals("28/03/2024 10:22:45", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.LONG, DateFormat.LONG, LocaleUtil.BRAZIL,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals(
			"28 de Mar\u00E7o de 2024 10h22min45s PDT", format.format(_date));
	}

	@Test
	public void testDifferentTimeZone() {
		Format format = _fastDateFormatFactory.getDateTime(
			DateFormat.SHORT, DateFormat.SHORT, LocaleUtil.BRAZIL,
			TimeZone.getTimeZone("Brazil/West"));

		Assert.assertEquals("28/03/24 13:22", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.MEDIUM, DateFormat.MEDIUM, LocaleUtil.BRAZIL,
			TimeZone.getTimeZone("Brazil/West"));

		Assert.assertEquals("28/03/2024 13:22:45", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.LONG, DateFormat.LONG, LocaleUtil.BRAZIL,
			TimeZone.getTimeZone("Brazil/West"));

		Assert.assertEquals(
			"28 de Mar\u00E7o de 2024 13h22min45s AMT", format.format(_date));
	}

	@Test
	public void testGetDate() {
		Format format = _fastDateFormatFactory.getDate(
			DateFormat.SHORT, LocaleUtil.US, TimeZone.getTimeZone("PST"));

		Assert.assertEquals("3/28/24", format.format(_date));

		format = _fastDateFormatFactory.getDate(
			DateFormat.MEDIUM, LocaleUtil.US, TimeZone.getTimeZone("PST"));

		Assert.assertEquals("Mar 28, 2024", format.format(_date));

		format = _fastDateFormatFactory.getDate(
			DateFormat.LONG, LocaleUtil.US, TimeZone.getTimeZone("PST"));

		Assert.assertEquals("March 28, 2024", format.format(_date));

		format = _fastDateFormatFactory.getDate(
			DateFormat.FULL, LocaleUtil.US, TimeZone.getTimeZone("PST"));

		Assert.assertEquals("Thursday, March 28, 2024", format.format(_date));
	}

	@Test
	public void testGetDateTime() {
		Format format = _fastDateFormatFactory.getDateTime(
			DateFormat.SHORT, DateFormat.SHORT, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals("3/28/24 10:22 AM", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.SHORT, DateFormat.MEDIUM, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals("3/28/24 10:22:45 AM", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.SHORT, DateFormat.LONG, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals("3/28/24 10:22:45 AM PDT", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.MEDIUM, DateFormat.SHORT, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals("Mar 28, 2024 10:22 AM", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.MEDIUM, DateFormat.MEDIUM, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals("Mar 28, 2024 10:22:45 AM", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.MEDIUM, DateFormat.LONG, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals(
			"Mar 28, 2024 10:22:45 AM PDT", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.LONG, DateFormat.SHORT, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals("March 28, 2024 10:22 AM", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.LONG, DateFormat.MEDIUM, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals("March 28, 2024 10:22:45 AM", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.LONG, DateFormat.LONG, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals(
			"March 28, 2024 10:22:45 AM PDT", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.FULL, DateFormat.SHORT, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals(
			"Thursday, March 28, 2024 10:22 AM", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.FULL, DateFormat.MEDIUM, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals(
			"Thursday, March 28, 2024 10:22:45 AM", format.format(_date));

		format = _fastDateFormatFactory.getDateTime(
			DateFormat.FULL, DateFormat.LONG, LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals(
			"Thursday, March 28, 2024 10:22:45 AM PDT", format.format(_date));
	}

	@Test
	public void testGetSimpleDateFormat() {
		Format format = _fastDateFormatFactory.getSimpleDateFormat(
			"yyyy MMM dd HH:mm:ss:SSS a zzz", LocaleUtil.US,
			TimeZone.getTimeZone("PST"));

		Assert.assertEquals(
			"2024 Mar 28 10:22:45:496 AM PDT", format.format(_date));
	}

	@Test
	public void testGetTimeDateFormat() {
		Format format = _fastDateFormatFactory.getTime(
			DateFormat.SHORT, LocaleUtil.US, TimeZone.getTimeZone("PST"));

		Assert.assertEquals("10:22 AM", format.format(_date));

		format = _fastDateFormatFactory.getTime(
			DateFormat.MEDIUM, LocaleUtil.US, TimeZone.getTimeZone("PST"));

		Assert.assertEquals("10:22:45 AM", format.format(_date));

		format = _fastDateFormatFactory.getTime(
			DateFormat.LONG, LocaleUtil.US, TimeZone.getTimeZone("PST"));

		Assert.assertEquals("10:22:45 AM PDT", format.format(_date));

		format = _fastDateFormatFactory.getTime(
			DateFormat.FULL, LocaleUtil.US, TimeZone.getTimeZone("PST"));

		Assert.assertEquals("10:22:45 AM PDT", format.format(_date));
	}

	private static final Date _date = new Date(1711646565496L);

	private final FastDateFormatFactory _fastDateFormatFactory =
		new FastDateFormatFactoryImpl();

}