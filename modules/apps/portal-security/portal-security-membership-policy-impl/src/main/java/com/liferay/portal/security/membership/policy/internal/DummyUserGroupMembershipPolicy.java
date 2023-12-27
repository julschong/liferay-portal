/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.membership.policy.internal;

import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.membershippolicy.BaseUserGroupMembershipPolicy;
import com.liferay.portal.kernel.security.membershippolicy.UserGroupMembershipPolicy;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@Component(service = UserGroupMembershipPolicy.class)
public class DummyUserGroupMembershipPolicy
	extends BaseUserGroupMembershipPolicy {

	@Override
	public void checkMembership(
		long[] userIds, long[] addUserGroupIds, long[] removeUserGroupIds) {
	}

	@Override
	public boolean isMembershipAllowed(long userId, long userGroupId) {
		return true;
	}

	@Override
	public boolean isMembershipRequired(long userId, long userGroupId) {
		return false;
	}

	@Override
	public void propagateMembership(
		long[] userIds, long[] addUserGroupIds, long[] removeUserGroupIds) {
	}

	@Override
	public void verifyPolicy(UserGroup userGroup) {
	}

	@Override
	public void verifyPolicy(
		UserGroup userGroup, UserGroup oldUserGroup,
		Map<String, Serializable> oldExpandoAttributes) {
	}

}