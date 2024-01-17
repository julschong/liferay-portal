/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.kernel.service;

import com.liferay.mail.kernel.model.Account;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.portal.kernel.module.service.Snapshot;

import javax.mail.Session;

/**
 * @author Brian Wing Shun Chan
 */
public class MailServiceUtil {

	public static void clearSession() {
		_getService().clearSession();
	}

	public static Session getSession() {
		return _getService().getSession();
	}

	public static Session getSession(Account account) {
		return _getService().getSession(account);
	}

	public static Session getSession(long companyId) {
		return _getService().getSession(companyId);
	}

	public static void sendEmail(MailMessage mailMessage) {
		_getService().sendEmail(mailMessage);
	}

	private static MailService _getService() {
		return _mailServiceSnapshot.get();
	}

	private static final Snapshot<MailService> _mailServiceSnapshot =
		new Snapshot<>(MailServiceUtil.class, MailService.class, null);

}