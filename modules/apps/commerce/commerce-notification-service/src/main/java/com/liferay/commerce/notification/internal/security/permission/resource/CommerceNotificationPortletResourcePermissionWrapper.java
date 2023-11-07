/**
 * SPDX-FileCopyrightText: (c) 2002 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.internal.security.permission.resource;

import com.liferay.portal.kernel.security.permission.resource.BasePortletResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 * @author Julius Lee
 */
@Component(service = PortletResourcePermission.class)
public class CommerceNotificationPortletResourcePermissionWrapper
	extends BasePortletResourcePermissionWrapper {

	@Override
	protected PortletResourcePermission doGetPortletResourcePermission() {
	}

}