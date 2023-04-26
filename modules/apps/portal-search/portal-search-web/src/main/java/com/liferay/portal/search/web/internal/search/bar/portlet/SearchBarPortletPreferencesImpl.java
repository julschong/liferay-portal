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

package com.liferay.portal.search.web.internal.search.bar.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 */
public class SearchBarPortletPreferencesImpl
	implements SearchBarPortletPreferences {

	public SearchBarPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferences = portletPreferencesOptional.orElse(
			PortletPreferencesFactoryUtil.getEmptyPortletPreferences());
	}

	@Override
	public String getDestination() {
		return StringUtil.trim(
			_portletPreferences.getValue(
				SearchBarPortletPreferences.PREFERENCE_KEY_DESTINATION,
				StringPool.BLANK));
	}

	@Override
	public String getFederatedSearchKey() {
		return StringUtil.trim(
			_portletPreferences.getValue(
				SearchBarPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY,
				StringPool.BLANK));
	}

	@Override
	public String getKeywordsParameterName() {
		return StringUtil.trim(
			_portletPreferences.getValue(
				SearchBarPortletPreferences.
					PREFERENCE_KEY_KEYWORDS_PARAMETER_NAME,
				"q"));
	}

	@Override
	public String getScopeParameterName() {
		return StringUtil.trim(
			_portletPreferences.getValue(
				SearchBarPortletPreferences.PREFERENCE_KEY_SCOPE_PARAMETER_NAME,
				"scope"));
	}

	@Override
	public SearchScopePreference getSearchScopePreference() {
		return SearchScopePreference.getSearchScopePreference(
			StringUtil.trim(
				_portletPreferences.getValue(
					SearchBarPortletPreferences.PREFERENCE_KEY_SEARCH_SCOPE,
					StringPool.BLANK)));
	}

	@Override
	public String getSearchScopePreferenceString() {
		SearchScopePreference searchScopePreference =
			getSearchScopePreference();

		return searchScopePreference.getPreferenceString();
	}

	@Override
	public boolean isInvisible() {
		return GetterUtil.getBoolean(
			_portletPreferences.getValue(
				SearchBarPortletPreferences.PREFERENCE_KEY_INVISIBLE,
				StringPool.BLANK));
	}

	@Override
	public boolean isShowStagedResults() {
		return GetterUtil.getBoolean(
			_portletPreferences.getValue(
				SearchBarPortletPreferences.PREFERENCE_KEY_SHOW_STAGED_RESULTS,
				StringPool.BLANK));
	}

	@Override
	public boolean isSuggestionsEnabled() {
		return GetterUtil.getBoolean(
			_portletPreferences.getValue(
				SearchBarPortletPreferences.PREFERENCE_KEY_SUGGESTIONS_ENABLED,
				StringPool.BLANK),
			true);
	}

	@Override
	public boolean isUseAdvancedSearchSyntax() {
		return GetterUtil.getBoolean(
			_portletPreferences.getValue(
				SearchBarPortletPreferences.
					PREFERENCE_KEY_USE_ADVANCED_SEARCH_SYNTAX,
				StringPool.BLANK));
	}

	private final PortletPreferences _portletPreferences;

}