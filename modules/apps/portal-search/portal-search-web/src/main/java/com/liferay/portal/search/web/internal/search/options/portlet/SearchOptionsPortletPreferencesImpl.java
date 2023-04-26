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

package com.liferay.portal.search.web.internal.search.options.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Wade Cao
 */
public class SearchOptionsPortletPreferencesImpl
	implements SearchOptionsPortletPreferences {

	public SearchOptionsPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferences = portletPreferencesOptional.orElse(
			PortletPreferencesFactoryUtil.getEmptyPortletPreferences());
	}

	@Override
	public String getFederatedSearchKey() {
		return StringUtil.trim(
			_portletPreferences.getValue(
				SearchOptionsPortletPreferences.
					PREFERENCE_KEY_FEDERATED_SEARCH_KEY,
				StringPool.BLANK));
	}

	@Override
	public boolean isAllowEmptySearches() {
		return GetterUtil.getBoolean(
			_portletPreferences.getValue(
				SearchOptionsPortletPreferences.
					PREFERENCE_KEY_ALLOW_EMPTY_SEARCHES,
				StringPool.BLANK));
	}

	@Override
	public boolean isBasicFacetSelection() {
		return GetterUtil.getBoolean(
			_portletPreferences.getValue(
				SearchOptionsPortletPreferences.
					PREFERENCE_KEY_BASIC_FACET_SELECTION,
				StringPool.BLANK));
	}

	private final PortletPreferences _portletPreferences;

}