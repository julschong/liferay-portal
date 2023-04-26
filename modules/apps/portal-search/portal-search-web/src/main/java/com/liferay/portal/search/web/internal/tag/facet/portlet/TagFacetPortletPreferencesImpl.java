/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.web.internal.tag.facet.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Lino Alves
 */
public class TagFacetPortletPreferencesImpl
	implements TagFacetPortletPreferences {

	public TagFacetPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferences = portletPreferencesOptional.orElse(
			PortletPreferencesFactoryUtil.getEmptyPortletPreferences());
	}

	@Override
	public String getDisplayStyle() {
		return StringUtil.trim(
			_portletPreferences.getValue(
				TagFacetPortletPreferences.PREFERENCE_KEY_DISPLAY_STYLE,
				"cloud"));
	}

	@Override
	public int getFrequencyThreshold() {
		return GetterUtil.getInteger(
			_portletPreferences.getValue(
				TagFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD,
				StringPool.BLANK),
			1);
	}

	@Override
	public int getMaxTerms() {
		return GetterUtil.getInteger(
			_portletPreferences.getValue(
				TagFacetPortletPreferences.PREFERENCE_KEY_MAX_TERMS,
				StringPool.BLANK),
			10);
	}

	@Override
	public String getOrder() {
		return StringUtil.trim(
			_portletPreferences.getValue(
				TagFacetPortletPreferences.PREFERENCE_KEY_ORDER, "count:desc"));
	}

	@Override
	public String getParameterName() {
		return StringUtil.trim(
			_portletPreferences.getValue(
				TagFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME,
				"tag"));
	}

	@Override
	public boolean isFrequenciesVisible() {
		return GetterUtil.getBoolean(
			_portletPreferences.getValue(
				TagFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE,
				StringPool.BLANK),
			true);
	}

	private final PortletPreferences _portletPreferences;

}