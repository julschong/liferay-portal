/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.cache;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.blueprint.exception.InvalidWebCacheItemException;
import com.liferay.search.experiences.internal.configuration.OpenWeatherMapConfiguration;

import java.beans.ExceptionListener;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(enabled = false, service = OpenWeatherMapCache.class)
public class OpenWeatherMapCache {

	public JSONObject getJSONObject(
		ExceptionListener exceptionListener, String latitude, String longitude,
		OpenWeatherMapConfiguration openWeatherMapConfiguration) {

		if (!openWeatherMapConfiguration.enabled()) {
			return JSONFactoryUtil.createJSONObject();
		}

		try {
			String key = StringBundler.concat(
				StringPool.POUND, openWeatherMapConfiguration.apiKey(),
				StringPool.POUND, openWeatherMapConfiguration.apiURL(),
				StringPool.POUND, latitude, StringPool.POUND, longitude);

			JSONObject jsonObject = _portalCache.get(key);

			if (jsonObject != null) {
				return jsonObject;
			}

			jsonObject = _createJSONObject(
				latitude, longitude, openWeatherMapConfiguration);

			_portalCache.put(
				key, jsonObject, _getRefreshTime(openWeatherMapConfiguration));

			return jsonObject;
		}
		catch (Exception exception) {
			exceptionListener.exceptionThrown(exception);

			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return JSONFactoryUtil.createJSONObject();
		}
	}

	@Activate
	protected void activate() {
		_portalCache =
			(PortalCache<String, JSONObject>)_multiVMPool.getPortalCache(
				OpenWeatherMapCache.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(OpenWeatherMapCache.class.getName());
	}

	private JSONObject _createJSONObject(
		String latitude, String longitude,
		OpenWeatherMapConfiguration openWeatherMapConfiguration) {

		try {
			String url = StringBundler.concat(
				openWeatherMapConfiguration.apiURL(), "?APPID=",
				openWeatherMapConfiguration.apiKey(), "&format=json&lat=",
				latitude, "&lon=", longitude, "&units=",
				openWeatherMapConfiguration.units());

			if (_log.isDebugEnabled()) {
				_log.debug("Reading " + url);
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				HttpUtil.URLtoString(url));

			_validateResponse(jsonObject);

			return jsonObject;
		}
		catch (Exception exception) {
			throw new InvalidWebCacheItemException(exception);
		}
	}

	private int _getRefreshTime(
		OpenWeatherMapConfiguration openWeatherMapConfiguration) {

		if (openWeatherMapConfiguration.enabled()) {
			return (int)
				(openWeatherMapConfiguration.cacheTimeout() / Time.SECOND);
		}

		return 0;
	}

	private void _validateResponse(JSONObject jsonObject) {
		String cod = jsonObject.getString("cod");

		if (Validator.isNull(cod) || cod.startsWith("2")) {
			return;
		}

		throw new InvalidWebCacheItemException(
			StringBundler.concat(
				"OpenWeatherMap: ",
				JSONUtil.getValueAsString(jsonObject, "Object/message"), " (",
				cod, ")"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenWeatherMapCache.class);

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<String, JSONObject> _portalCache;

}