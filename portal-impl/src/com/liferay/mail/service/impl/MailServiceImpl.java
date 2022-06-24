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

package com.liferay.mail.service.impl;

import com.liferay.mail.kernel.model.Filter;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.mail.kernel.util.Hook;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MailServiceImpl implements IdentifiableOSGiService, MailService {

	@Override
	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (_log.isDebugEnabled()) {
					_log.debug("addForward");
				}

				MethodHandler methodHandler = new MethodHandler(
					_addForwardMethodKey, companyId, userId, filters,
					emailAddresses, leaveCopy);

				MessageBusUtil.sendMessage(
					DestinationNames.MAIL, methodHandler);

				return null;
			});
	}

	@Override
	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (_log.isDebugEnabled()) {
					_log.debug("addUser");
				}

				MethodHandler methodHandler = new MethodHandler(
					_addUserMethodKey, companyId, userId, password, firstName,
					middleName, lastName, emailAddress);

				MessageBusUtil.sendMessage(
					DestinationNames.MAIL, methodHandler);

				return null;
			});
	}

	@Override
	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (_log.isDebugEnabled()) {
					_log.debug("addVacationMessage");
				}

				MethodHandler methodHandler = new MethodHandler(
					_addVacationMessageMethodKey, companyId, userId,
					emailAddress, vacationMessage);

				MessageBusUtil.sendMessage(
					DestinationNames.MAIL, methodHandler);

				return null;
			});
	}

	@Override
	public void deleteEmailAddress(long companyId, long userId) {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (_log.isDebugEnabled()) {
					_log.debug("deleteEmailAddress");
				}

				MethodHandler methodHandler = new MethodHandler(
					_deleteEmailAddressMethodKey, companyId, userId);

				MessageBusUtil.sendMessage(
					DestinationNames.MAIL, methodHandler);

				return null;
			});
	}

	@Override
	public void deleteUser(long companyId, long userId) {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (_log.isDebugEnabled()) {
					_log.debug("deleteUser");
				}

				MethodHandler methodHandler = new MethodHandler(
					_deleteUserMethodKey, companyId, userId);

				MessageBusUtil.sendMessage(
					DestinationNames.MAIL, methodHandler);

				return null;
			});
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return MailService.class.getName();
	}

	@Override
	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (_log.isDebugEnabled()) {
					_log.debug("updateBlocked");
				}

				MethodHandler methodHandler = new MethodHandler(
					_updateBlockedMethodKey, companyId, userId, blocked);

				MessageBusUtil.sendMessage(
					DestinationNames.MAIL, methodHandler);

				return null;
			});
	}

	@Override
	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (_log.isDebugEnabled()) {
					_log.debug("updateEmailAddress");
				}

				MethodHandler methodHandler = new MethodHandler(
					_updateEmailAddressMethodKey, companyId, userId,
					emailAddress);

				MessageBusUtil.sendMessage(
					DestinationNames.MAIL, methodHandler);

				return null;
			});
	}

	@Override
	public void updatePassword(long companyId, long userId, String password) {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (_log.isDebugEnabled()) {
					_log.debug("updatePassword");
				}

				MethodHandler methodHandler = new MethodHandler(
					_updatePasswordMethodKey, companyId, userId, password);

				MessageBusUtil.sendMessage(
					DestinationNames.MAIL, methodHandler);

				return null;
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MailServiceImpl.class);

	private static final MethodKey _addForwardMethodKey = new MethodKey(
		Hook.class, "addForward", long.class, long.class, List.class,
		List.class, boolean.class);
	private static final MethodKey _addUserMethodKey = new MethodKey(
		Hook.class, "addUser", long.class, long.class, String.class,
		String.class, String.class, String.class, String.class);
	private static final MethodKey _addVacationMessageMethodKey = new MethodKey(
		Hook.class, "addVacationMessage", long.class, long.class, String.class,
		String.class);
	private static final MethodKey _deleteEmailAddressMethodKey = new MethodKey(
		Hook.class, "deleteEmailAddress", long.class, long.class);
	private static final MethodKey _deleteUserMethodKey = new MethodKey(
		Hook.class, "deleteUser", long.class, long.class);
	private static final MethodKey _updateBlockedMethodKey = new MethodKey(
		Hook.class, "updateBlocked", long.class, long.class, List.class);
	private static final MethodKey _updateEmailAddressMethodKey = new MethodKey(
		Hook.class, "updateEmailAddress", long.class, long.class, String.class);
	private static final MethodKey _updatePasswordMethodKey = new MethodKey(
		Hook.class, "updatePassword", long.class, long.class, String.class);

}