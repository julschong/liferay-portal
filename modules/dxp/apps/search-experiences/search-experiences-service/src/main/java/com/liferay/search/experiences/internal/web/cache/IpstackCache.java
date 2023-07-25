/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.web.cache;

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
import com.liferay.search.experiences.blueprint.exception.InvalidWebCacheItemException;
import com.liferay.search.experiences.blueprint.exception.PrivateIPAddressException;
import com.liferay.search.experiences.internal.configuration.IpstackConfiguration;

import java.beans.ExceptionListener;

import java.net.Inet4Address;
import java.net.InetAddress;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(enabled = false, service = IpstackCache.class)
public class IpstackCache {

	public JSONObject get(
		ExceptionListener exceptionListener, String ipAddress,
		IpstackConfiguration ipstackConfiguration) {

		try {
			if (!ipstackConfiguration.enabled() ||
				_isPrivateIPAddress(ipAddress)) {

				return JSONFactoryUtil.createJSONObject();
			}

			String key = StringBundler.concat(
				IpstackCache.class.getName(), StringPool.POUND,
				ipstackConfiguration.apiKey(), StringPool.POUND,
				ipstackConfiguration.apiURL(), StringPool.POUND, ipAddress);

			JSONObject jsonObject = _portalCache.get(key);

			if (jsonObject != null) {
				return jsonObject;
			}

			jsonObject = _convert(ipAddress, ipstackConfiguration);

			_portalCache.put(
				key, jsonObject,
				(int)(_getRefreshTime(ipstackConfiguration) / Time.SECOND));

			return jsonObject;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			exceptionListener.exceptionThrown(exception);

			return JSONFactoryUtil.createJSONObject();
		}
	}

	@Activate
	protected void activate() {
		_portalCache =
			(PortalCache<String, JSONObject>)_multiVMPool.getPortalCache(
				IpstackCache.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(IpstackCache.class.getName());
	}

	private JSONObject _convert(
		String ipAddress, IpstackConfiguration ipstackConfiguration) {

		try {
			String apiURL = ipstackConfiguration.apiURL();

			if (!apiURL.endsWith("/")) {
				apiURL += "/";
			}

			String url = StringBundler.concat(
				apiURL, ipAddress, "?access_key=",
				ipstackConfiguration.apiKey());

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

	private long _getRefreshTime(IpstackConfiguration ipstackConfiguration) {
		if (ipstackConfiguration.enabled()) {
			return ipstackConfiguration.cacheTimeout();
		}

		return 0;
	}

	private boolean _isPrivateIPAddress(String ipAddress) throws Exception {
		Inet4Address inet4Address = (Inet4Address)InetAddress.getByName(
			ipAddress);

		if (inet4Address.isAnyLocalAddress() ||
			inet4Address.isLinkLocalAddress() ||
			inet4Address.isLoopbackAddress() ||
			inet4Address.isMulticastAddress() ||
			inet4Address.isSiteLocalAddress()) {

			throw new PrivateIPAddressException(
				"Unable to resolve private IP address " + ipAddress);
		}

		return false;
	}

	private void _validateResponse(JSONObject jsonObject) {
		boolean success = jsonObject.getBoolean("success", true);

		if (success) {
			return;
		}

		throw new InvalidWebCacheItemException(
			StringBundler.concat(
				"IPStack: ",
				JSONUtil.getValueAsString(
					jsonObject, "JSONObject/error", "Object/info"),
				" (",
				JSONUtil.getValueAsString(
					jsonObject, "JSONObject/error", "Object/code"),
				")"));
	}

	private static final Log _log = LogFactoryUtil.getLog(IpstackCache.class);

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<String, JSONObject> _portalCache;

}