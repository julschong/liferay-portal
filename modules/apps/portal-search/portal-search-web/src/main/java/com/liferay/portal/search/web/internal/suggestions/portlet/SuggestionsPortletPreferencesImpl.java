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

package com.liferay.portal.search.web.internal.suggestions.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Adam Brandizzi
 */
public class SuggestionsPortletPreferencesImpl
	implements SuggestionsPortletPreferences {

	public SuggestionsPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferences = portletPreferencesOptional.orElse(
			PortletPreferencesFactoryUtil.getEmptyPortletPreferences());
	}

	@Override
	public int getQueryIndexingThreshold() {
		return GetterUtil.getInteger(
			_portletPreferences.getValue(
				PREFERENCE_KEY_QUERY_INDEXING_THRESHOLD, StringPool.BLANK),
			50);
	}

	@Override
	public int getRelatedQueriesSuggestionsDisplayThreshold() {
		return GetterUtil.getInteger(
			_portletPreferences.getValue(
				PREFERENCE_KEY_RELATED_QUERIES_SUGGESTIONS_DISPLAY_THRESHOLD,
				StringPool.BLANK),
			50);
	}

	@Override
	public int getRelatedQueriesSuggestionsMax() {
		return GetterUtil.getInteger(
			_portletPreferences.getValue(
				PREFERENCE_KEY_RELATED_QUERIES_SUGGESTIONS_MAX,
				StringPool.BLANK),
			10);
	}

	@Override
	public int getSpellCheckSuggestionDisplayThreshold() {
		return GetterUtil.getInteger(
			_portletPreferences.getValue(
				PREFERENCE_KEY_SPELL_CHECK_SUGGESTION_DISPLAY_THRESHOLD,
				StringPool.BLANK),
			50);
	}

	@Override
	public boolean isQueryIndexingEnabled() {
		return GetterUtil.getBoolean(
			_portletPreferences.getValue(
				PREFERENCE_KEY_QUERY_INDEXING_ENABLED, StringPool.BLANK));
	}

	@Override
	public boolean isRelatedQueriesSuggestionsEnabled() {
		return GetterUtil.getBoolean(
			_portletPreferences.getValue(
				PREFERENCE_KEY_RELATED_QUERIES_SUGGESTIONS_ENABLED,
				StringPool.BLANK));
	}

	@Override
	public boolean isSpellCheckSuggestionEnabled() {
		return GetterUtil.getBoolean(
			_portletPreferences.getValue(
				PREFERENCE_KEY_SPELL_CHECK_SUGGESTION_ENABLED,
				StringPool.BLANK));
	}

	private final PortletPreferences _portletPreferences;

}