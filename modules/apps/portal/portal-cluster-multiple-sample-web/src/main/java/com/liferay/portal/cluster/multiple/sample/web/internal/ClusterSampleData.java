/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.sample.web.internal;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalSessionContext;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.Serializable;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

/**
 * @author Jorge Díaz
 */
public class ClusterSampleData implements Serializable {

	public ClusterSampleData() {
		_computerName = PortalUtil.getComputerName();
		_data = StringUtil.randomString(20);
		_liferayHome = SystemProperties.get("liferay.home");
		_timestamp = System.currentTimeMillis();
	}

	public List<String> getAllSessionIds() {
		return TransformUtil.transform(
			PortalSessionContext.values(), HttpSession::getId);
	}

	public String getComputerName() {
		return _computerName;
	}

	public String getData() {
		return _data;
	}

	public String getLiferayHome() {
		return _liferayHome;
	}

	public String getLoggedInSessions() {
		Collection<HttpSession> httpSessions = PortalSessionContext.values();

		Map<String, Map<String, String>> map = new TreeMap<>();

		for (HttpSession httpSession : httpSessions) {
			Map<String, String> attributeMap = new TreeMap<>();

			Enumeration<String> enumeration = httpSession.getAttributeNames();

			while (enumeration.hasMoreElements()) {
				String attributeName = enumeration.nextElement();

				attributeMap.put(
					attributeName,
					JSONFactoryUtil.serialize(
						httpSession.getAttribute(attributeName)));
			}

			if (attributeMap.containsKey("USER_ID")) {
				map.put(httpSession.getId(), attributeMap);
			}
		}

		StringBundler sb = new StringBundler();

		for (Map.Entry<String, Map<String, String>> sessionEntry :
				map.entrySet()) {

			sb.append("<strong>ID: ");
			sb.append(sessionEntry.getKey());
			sb.append("</strong>\n");

			sb.append("<ul>");

			for (Map.Entry<String, String> idEntry :
					sessionEntry.getValue(
					).entrySet()) {

				sb.append("<li>");
				sb.append(idEntry.getKey());
				sb.append(": ");
				sb.append(idEntry.getValue());
				sb.append("</li>");
			}

			sb.append("</ul>");
		}

		return sb.toString();
	}

	public long getTimestamp() {
		return _timestamp;
	}

	private static final long serialVersionUID = 805643793521506119L;

	private final String _computerName;
	private final String _data;
	private final String _liferayHome;
	private long _timestamp = -1;

}