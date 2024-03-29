/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FastDateFormatConstants;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class FastDateFormatFactoryImpl implements FastDateFormatFactory {

	@Override
	public Format getDate(int style, Locale locale, TimeZone timeZone) {
		DateOrTimeCacheKey dateOrTimeCacheKey = new DateOrTimeCacheKey(
			style, locale, timeZone);

		Format format = _dateFormats.get(dateOrTimeCacheKey);

		if (format == null) {
			format = new ClassicFormatWrapper(
				DateTimeFormatter.ofLocalizedDate(
					_formatStyles[style]
				).withLocale(
					locale
				).withZone(
					timeZone.toZoneId()
				).toFormat());

			_dateFormats.put(dateOrTimeCacheKey, format);
		}

		return format;
	}

	@Override
	public Format getDate(Locale locale) {
		return getDate(locale, null);
	}

	@Override
	public Format getDate(Locale locale, TimeZone timeZone) {
		return getDate(FastDateFormatConstants.SHORT, locale, timeZone);
	}

	@Override
	public Format getDate(TimeZone timeZone) {
		return getDate(LocaleUtil.getDefault(), timeZone);
	}

	@Override
	public Format getDateTime(
		int dateStyle, int timeStyle, Locale locale, TimeZone timeZone) {

		DateAndTimeCacheKey dateAndTimeCacheKey = new DateAndTimeCacheKey(
			dateStyle, timeStyle, locale, timeZone);

		Format format = _dateTimeFormats.get(dateAndTimeCacheKey);

		if (format == null) {
			format = new ClassicFormatWrapper(
				DateTimeFormatter.ofLocalizedDateTime(
					_formatStyles[dateStyle], _formatStyles[timeStyle]
				).withLocale(
					locale
				).withZone(
					timeZone.toZoneId()
				).toFormat());

			_dateTimeFormats.put(dateAndTimeCacheKey, format);
		}

		return format;
	}

	@Override
	public Format getDateTime(Locale locale) {
		return getDateTime(locale, null);
	}

	@Override
	public Format getDateTime(Locale locale, TimeZone timeZone) {
		return getDateTime(
			FastDateFormatConstants.SHORT, FastDateFormatConstants.SHORT,
			locale, timeZone);
	}

	@Override
	public Format getDateTime(TimeZone timeZone) {
		return getDateTime(LocaleUtil.getDefault(), timeZone);
	}

	@Override
	public Format getSimpleDateFormat(String pattern) {
		return getSimpleDateFormat(pattern, LocaleUtil.getDefault(), null);
	}

	@Override
	public Format getSimpleDateFormat(String pattern, Locale locale) {
		return getSimpleDateFormat(pattern, locale, null);
	}

	@Override
	public Format getSimpleDateFormat(
		String pattern, Locale locale, TimeZone timeZone) {

		SimpleDateCacheKey simpleDateCacheKey = new SimpleDateCacheKey(
			pattern, locale, timeZone);

		Format format = _simpleDateFormats.get(simpleDateCacheKey);

		if (format == null) {
			format = new ClassicFormatWrapper(
				DateTimeFormatter.ofPattern(
					pattern
				).withLocale(
					locale
				).withZone(
					timeZone.toZoneId()
				).toFormat());

			_simpleDateFormats.put(simpleDateCacheKey, format);
		}

		return format;
	}

	@Override
	public Format getSimpleDateFormat(String pattern, TimeZone timeZone) {
		return getSimpleDateFormat(pattern, LocaleUtil.getDefault(), timeZone);
	}

	@Override
	public Format getTime(int style, Locale locale, TimeZone timeZone) {
		DateOrTimeCacheKey dateOrTimeCacheKey = new DateOrTimeCacheKey(
			style, locale, timeZone);

		Format format = _timeFormats.get(dateOrTimeCacheKey);

		if (format == null) {
			format = new ClassicFormatWrapper(
				DateTimeFormatter.ofLocalizedTime(
					_formatStyles[style]
				).withLocale(
					locale
				).withZone(
					timeZone.toZoneId()
				).toFormat());

			_timeFormats.put(dateOrTimeCacheKey, format);
		}

		return format;
	}

	@Override
	public Format getTime(Locale locale) {
		return getTime(locale, null);
	}

	@Override
	public Format getTime(Locale locale, TimeZone timeZone) {
		return getTime(FastDateFormatConstants.SHORT, locale, timeZone);
	}

	@Override
	public Format getTime(TimeZone timeZone) {
		return getTime(LocaleUtil.getDefault(), timeZone);
	}

	protected String getKey(Object... arguments) {
		StringBundler sb = new StringBundler((arguments.length * 2) - 1);

		for (int i = 0; i < arguments.length; i++) {
			sb.append(arguments[i]);

			if ((i + 1) < arguments.length) {
				sb.append(StringPool.UNDERLINE);
			}
		}

		return sb.toString();
	}

	private static final FormatStyle[] _formatStyles = FormatStyle.values();

	private final Map<DateOrTimeCacheKey, Format> _dateFormats =
		new ConcurrentHashMap<>();
	private final Map<DateAndTimeCacheKey, Format> _dateTimeFormats =
		new ConcurrentHashMap<>();
	private final Map<SimpleDateCacheKey, Format> _simpleDateFormats =
		new ConcurrentHashMap<>();
	private final Map<DateOrTimeCacheKey, Format> _timeFormats =
		new ConcurrentHashMap<>();

	private static class ClassicFormatWrapper extends Format {

		@Override
		public StringBuffer format(
			Object object, StringBuffer toAppendTo, FieldPosition pos) {

			if (object instanceof Date) {
				if (!(object instanceof TemporalAccessor)) {
					Date date = (Date)object;

					object = LocalDate.ofEpochDay(date.getTime());
				}
			}
			else if (object instanceof Number) {
				Number number = (Number)object;

				object = LocalDate.ofEpochDay(number.longValue());
			}
			else {
				throw new IllegalArgumentException(
					"Cannot format given Object as a Date: " + object);
			}

			return _classicFormat.format(object, toAppendTo, pos);
		}

		@Override
		public Object parseObject(String source, ParsePosition pos) {
			return _classicFormat.parseObject(source, pos);
		}

		private ClassicFormatWrapper(Format classicFormat) {
			_classicFormat = classicFormat;
		}

		private final Format _classicFormat;

	}

	private static class DateAndTimeCacheKey {

		@Override
		public boolean equals(Object object) {
			DateAndTimeCacheKey dateAndTimeCacheKey =
				(DateAndTimeCacheKey)object;

			if ((dateAndTimeCacheKey._dateStyle == _dateStyle) &&
				(dateAndTimeCacheKey._timeStyle == _timeStyle) &&
				Objects.equals(dateAndTimeCacheKey._locale, _locale) &&
				Objects.equals(dateAndTimeCacheKey._timeZone, _timeZone)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _dateStyle);

			hashCode = HashUtil.hash(hashCode, _timeStyle);
			hashCode = HashUtil.hash(hashCode, _locale);

			return HashUtil.hash(hashCode, _timeZone);
		}

		private DateAndTimeCacheKey(
			int dateStyle, int timeStyle, Locale locale, TimeZone timeZone) {

			_dateStyle = dateStyle;
			_timeStyle = timeStyle;
			_locale = locale;
			_timeZone = timeZone;
		}

		private final int _dateStyle;
		private final Locale _locale;
		private final int _timeStyle;
		private final TimeZone _timeZone;

	}

	private static class DateOrTimeCacheKey {

		@Override
		public boolean equals(Object object) {
			DateOrTimeCacheKey dateOrTimeCacheKey = (DateOrTimeCacheKey)object;

			if ((dateOrTimeCacheKey._style == _style) &&
				Objects.equals(dateOrTimeCacheKey._locale, _locale) &&
				Objects.equals(dateOrTimeCacheKey._timeZone, _timeZone)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _style);

			hashCode = HashUtil.hash(hashCode, _locale);

			return HashUtil.hash(hashCode, _timeZone);
		}

		private DateOrTimeCacheKey(
			int style, Locale locale, TimeZone timeZone) {

			_style = style;
			_locale = locale;
			_timeZone = timeZone;
		}

		private final Locale _locale;
		private final int _style;
		private final TimeZone _timeZone;

	}

	private static class SimpleDateCacheKey {

		@Override
		public boolean equals(Object object) {
			SimpleDateCacheKey simpleDateCacheKey = (SimpleDateCacheKey)object;

			if (Objects.equals(simpleDateCacheKey._pattern, _pattern) &&
				Objects.equals(simpleDateCacheKey._locale, _locale) &&
				Objects.equals(simpleDateCacheKey._timeZone, _timeZone)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _pattern);

			hashCode = HashUtil.hash(hashCode, _locale);

			return HashUtil.hash(hashCode, _timeZone);
		}

		private SimpleDateCacheKey(
			String pattern, Locale locale, TimeZone timeZone) {

			_pattern = pattern;
			_locale = locale;
			_timeZone = timeZone;
		}

		private final Locale _locale;
		private final String _pattern;
		private final TimeZone _timeZone;

	}

}