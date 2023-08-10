/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * @author Eduardo García
 */
public class PortletDecorator
	implements Comparable<PortletDecorator>, Serializable {

	public static final Accessor<PortletDecorator, String> NAME_ACCESSOR =
		new Accessor<PortletDecorator, String>() {

			@Override
			public String get(PortletDecorator portletDecorator) {
				return portletDecorator.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<PortletDecorator> getTypeClass() {
				return PortletDecorator.class;
			}

		};

	public PortletDecorator() {
		this(null, null, null);
	}

	public PortletDecorator(String portletDecoratorId) {
		this(portletDecoratorId, null, null);
	}

	public PortletDecorator(
		String portletDecoratorId, String name, String cssClass) {

		_portletDecoratorId = portletDecoratorId;
		_name = name;
		_cssClass = cssClass;
	}

	@Override
	public int compareTo(PortletDecorator portletDecorator) {
		return getName().compareTo(portletDecorator.getName());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PortletDecorator)) {
			return false;
		}

		PortletDecorator portletDecorator = (PortletDecorator)object;

		if (getPortletDecoratorId().equals(
				portletDecorator.getPortletDecoratorId())) {

			return true;
		}

		return false;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getName() {
		if (Validator.isNull(_name)) {
			return _portletDecoratorId;
		}

		return _name;
	}

	public String getPortletDecoratorId() {
		return _portletDecoratorId;
	}

	public String getPortletDecoratorThumbnailPath() {
		if (Validator.isNotNull(_cssClass) &&
			Validator.isNotNull(_portletDecoratorThumbnailPath)) {

			int pos = _cssClass.indexOf(CharPool.SPACE);

			if ((pos > 0) &&
				_portletDecoratorThumbnailPath.endsWith(
					_cssClass.substring(0, pos))) {

				String subclassPath = StringUtil.replace(
					_cssClass, CharPool.SPACE, CharPool.SLASH);

				return _portletDecoratorThumbnailPath +
					subclassPath.substring(pos);
			}
		}

		return _portletDecoratorThumbnailPath;
	}

	public int hashCode() {
		return _portletDecoratorId.hashCode();
	}

	public boolean isDefaultPortletDecorator() {
		return _defaultPortletDecorator;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDefaultPortletDecorator(boolean defaultPortletDecorator) {
		_defaultPortletDecorator = defaultPortletDecorator;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPortletDecoratorThumbnailPath(
		String portletDecoratorThumbnailPath) {

		_portletDecoratorThumbnailPath = portletDecoratorThumbnailPath;
	}

	private String _cssClass;
	private boolean _defaultPortletDecorator;
	private String _name;
	private final String _portletDecoratorId;
	private String _portletDecoratorThumbnailPath =
		"${images-path}/portlet_decorators/${portlet-decorator-css-class}";

}