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

package com.liferay.portal.search.web.internal.search.bar.portlet.configuration;

import com.liferay.configuration.admin.display.ConfigurationFormRenderer;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.rest.configuration.SearchSuggestionsCompanyConfiguration;
import com.liferay.portal.search.web.internal.search.bar.portlet.display.context.SearchBarPortletInstanceConfigurationDisplayContext;

import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = ConfigurationFormRenderer.class)
public class SearchBarPortletInstanceConfigurationRenderer
	implements ConfigurationFormRenderer {

	@Override
	public String getPid() {
		return SearchBarPortletInstanceConfiguration.class.getName();
	}

	@Override
	public Map<String, Object> getRequestParameters(
		HttpServletRequest httpServletRequest) {

		return HashMapBuilder.<String, Object>put(
			"displayStyle",
			ParamUtil.getString(httpServletRequest, "displayStyle")
		).put(
			"displayStyleGroupId",
			ParamUtil.getLong(httpServletRequest, "displayStyleGroupId")
		).put(
			"enableSuggestions",
			ParamUtil.getBoolean(httpServletRequest, "enableSuggestions")
		).put(
			"suggestionsContributorConfigurations",
			StringUtil.split(
				ParamUtil.getString(
					httpServletRequest, "suggestionsContributorConfigurations"),
				CharPool.PIPE)
		).put(
			"suggestionsDisplayThreshold",
			ParamUtil.getInteger(
				httpServletRequest, "suggestionsDisplayThreshold")
		).build();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		SearchBarPortletInstanceConfiguration
			searchBarPortletInstanceConfiguration = null;

		SearchSuggestionsCompanyConfiguration
			searchSuggestionsCompanyConfiguration = null;

		String portletId = GetterUtil.getString(
			httpServletRequest.getAttribute(WebKeys.PORTLET_ID));

		try {
			if (portletId.endsWith("SystemSettingsPortlet")) {
				searchBarPortletInstanceConfiguration =
					ConfigurationProviderUtil.getSystemConfiguration(
						SearchBarPortletInstanceConfiguration.class);

				searchSuggestionsCompanyConfiguration =
					ConfigurationProviderUtil.getSystemConfiguration(
						SearchSuggestionsCompanyConfiguration.class);
			}
			else if (portletId.endsWith("InstanceSettingsPortlet")) {
				ThemeDisplay themeDisplay =
					(ThemeDisplay)httpServletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				searchBarPortletInstanceConfiguration =
					ConfigurationProviderUtil.getCompanyConfiguration(
						SearchBarPortletInstanceConfiguration.class,
						themeDisplay.getCompanyId());

				searchSuggestionsCompanyConfiguration =
					ConfigurationProviderUtil.getCompanyConfiguration(
						SearchSuggestionsCompanyConfiguration.class,
						themeDisplay.getCompanyId());
			}
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}

		SearchBarPortletInstanceConfigurationDisplayContext
			searchBarPortletInstanceConfigurationDisplayContext =
				new SearchBarPortletInstanceConfigurationDisplayContext();

		searchBarPortletInstanceConfigurationDisplayContext.setDisplayStyle(
			searchBarPortletInstanceConfiguration.displayStyle());
		searchBarPortletInstanceConfigurationDisplayContext.
			setDisplayStyleGroupId(
				searchBarPortletInstanceConfiguration.displayStyleGroupId());
		searchBarPortletInstanceConfigurationDisplayContext.
			setEnableSuggestions(
				searchBarPortletInstanceConfiguration.enableSuggestions());
		searchBarPortletInstanceConfigurationDisplayContext.
			setSuggestionsContributorConfigurations(
				searchBarPortletInstanceConfiguration.
					suggestionsContributorConfigurations());
		searchBarPortletInstanceConfigurationDisplayContext.
			setSuggestionsDisplayThreshold(
				searchBarPortletInstanceConfiguration.
					suggestionsDisplayThreshold());

		searchBarPortletInstanceConfigurationDisplayContext.
			setSuggestionsConfigurationVisible(
				searchSuggestionsCompanyConfiguration.
					enableSuggestionsEndpoint());

		httpServletRequest.setAttribute(
			SearchBarPortletInstanceConfigurationDisplayContext.class.getName(),
			searchBarPortletInstanceConfigurationDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/search/bar/portlet_instance_configuration.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.search.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}