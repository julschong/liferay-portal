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

package com.liferay.mail;

import com.liferay.mail.exception.MailEngineException;
import com.liferay.mail.kernel.model.Account;
import com.liferay.mail.kernel.model.FileAttachment;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.model.SMTPAccount;

import java.util.List;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;

/**
 * @author Julius Lee
 * @see    com.liferay.util.mail.MailEngine
 */
public interface MailEngine {

	public Session getSession();

	public Session getSession(Account account);

	public Session getSession(boolean cache);

	public void send(byte[] bytes) throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress to, String subject,
			String body)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress to, String subject,
			String body, boolean htmlFormat)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo,
			List<FileAttachment> fileAttachments)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo,
			List<FileAttachment> fileAttachments, SMTPAccount smtpAccount)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo,
			List<FileAttachment> fileAttachments, SMTPAccount smtpAccount,
			InternetHeaders internetHeaders)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, String subject, String body)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, String subject, String body,
			boolean htmlFormat, InternetAddress[] replyTo, String messageId,
			String inReplyTo)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			String subject, String body)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			String subject, String body, boolean htmlFormat)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, String subject,
			String body)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, String subject,
			String body, boolean htmlFormat)
		throws MailEngineException;

	public void send(MailMessage mailMessage) throws MailEngineException;

	public void send(String from, String to, String subject, String body)
		throws MailEngineException;

}