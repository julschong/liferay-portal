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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.web.internal.display.context.SearchScopePreference;
import com.liferay.portal.search.web.internal.helper.PortletPreferencesHelper;

import javax.portlet.PortletPreferences;

/**
 * @author André de Oliveira
 */
public class SearchBarPortletPreferencesImpl
	implements SearchBarPortletPreferences {

	public SearchBarPortletPreferencesImpl(
		PortletPreferences portletPreferences) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferences);
	}

	@Override
	public String getDestination() {
		return _portletPreferencesHelper.getString(
			SearchBarPortletPreferences.PREFERENCE_KEY_DESTINATION);
	}

	@Override
	public String getDestinationString() {
		return GetterUtil.getString(getDestination());
	}

	@Override
	public String getFederatedSearchKey() {
		return _portletPreferencesHelper.getString(
			SearchBarPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY);
	}

	@Override
	public String getFederatedSearchKeyString() {
		return GetterUtil.getString(getFederatedSearchKey());
	}

	@Override
	public String getKeywordsParameterName() {
		return _portletPreferencesHelper.getString(
			SearchBarPortletPreferences.PREFERENCE_KEY_KEYWORDS_PARAMETER_NAME,
			"q");
	}

	@Override
	public String getScopeParameterName() {
		return _portletPreferencesHelper.getString(
			SearchBarPortletPreferences.PREFERENCE_KEY_SCOPE_PARAMETER_NAME,
			"scope");
	}

	@Override
	public SearchScopePreference getSearchScopePreference() {
		return SearchScopePreference.getSearchScopePreference(
			_portletPreferencesHelper.getString(
				SearchBarPortletPreferences.PREFERENCE_KEY_SEARCH_SCOPE));
	}

	@Override
	public String getSearchScopePreferenceString() {
		SearchScopePreference searchScopePreference =
			getSearchScopePreference();

		return searchScopePreference.getPreferenceString();
	}

	@Override
	public boolean isInvisible() {
		return _portletPreferencesHelper.getBoolean(
			SearchBarPortletPreferences.PREFERENCE_KEY_INVISIBLE, false);
	}

	@Override
	public boolean isShowStagedResults() {
		return _portletPreferencesHelper.getBoolean(
			SearchBarPortletPreferences.PREFERENCE_KEY_SHOW_STAGED_RESULTS,
			false);
	}

	@Override
	public boolean isSuggestionsEnabled() {
		return _portletPreferencesHelper.getBoolean(
			SearchBarPortletPreferences.PREFERENCE_KEY_SUGGESTIONS_ENABLED,
			true);
	}

	@Override
	public boolean isUseAdvancedSearchSyntax() {
		return _portletPreferencesHelper.getBoolean(
			SearchBarPortletPreferences.
				PREFERENCE_KEY_USE_ADVANCED_SEARCH_SYNTAX,
			false);
	}

	private final PortletPreferencesHelper _portletPreferencesHelper;

}